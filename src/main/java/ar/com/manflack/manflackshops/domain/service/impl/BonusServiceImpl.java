package ar.com.manflack.manflackshops.domain.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import ar.com.manflack.manflackshops.app.dto.ArticleDTO;
import ar.com.manflack.manflackshops.app.dto.ClientDTO;
import ar.com.manflack.manflackshops.app.dto.ProductDTO;
import ar.com.manflack.manflackshops.app.dto.TicketDTO;
import ar.com.manflack.manflackshops.app.rest.request.ProductRequest;
import ar.com.manflack.manflackshops.domain.exception.ArticleWithoutStockException;
import ar.com.manflack.manflackshops.domain.exception.ArticlesConflictException;
import ar.com.manflack.manflackshops.domain.exception.ClientConflictException;
import ar.com.manflack.manflackshops.domain.exception.UncompletedFieldClientException;
import ar.com.manflack.manflackshops.domain.repository.ClientRepository;
import ar.com.manflack.manflackshops.domain.repository.ProductRepository;
import ar.com.manflack.manflackshops.domain.service.BonusService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BonusServiceImpl implements BonusService
{
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public TicketDTO purchaseRequest(ProductRequest request)
            throws ArticlesConflictException, ArticleWithoutStockException
    {
        List<ArticleDTO> articlesToPurchase = new ArrayList<>();
        AtomicReference<Integer> total = new AtomicReference<>(0);

        StringBuilder errorTrace = new StringBuilder();

        for (ArticleDTO article : request.getArticles())
        {
            ProductDTO product = productRepository.getByProductIdAndNameAndBrandAndQuantity(article.getProductId(),
                    article.getName(),
                    article.getBrand());

            if (product != null)
            {
                Integer quantity = article.getQuantity();
                if (article.getQuantity() > product.getQuantity())
                {
                    quantity = product.getQuantity();
                    errorTrace.append("Se han solicitado ")
                            .append(article.getQuantity())
                            .append(" unidades del producto con ID ")
                            .append(article.getProductId())
                            .append(" pero se encuentran en existencia ")
                            .append(product.getQuantity())
                            .append(", por favor, validar nuevamente la petición\n");
                }

                articlesToPurchase.add(article);
                Integer finalQuantity = quantity;
                total.updateAndGet(v -> v + product.getPrice() * finalQuantity);
            }
        }

        String errorTraceStr = errorTrace.toString();
        if (errorTraceStr.length() != 0)
        {
            throw new ArticleWithoutStockException(errorTraceStr);
        }

        TicketDTO ticket = new TicketDTO();
        ticket.setArticles(articlesToPurchase);
        ticket.setTotal(total.get());

        return ticket;
    }

    @Override
    public ClientDTO signup(ClientDTO client) throws ClientConflictException, UncompletedFieldClientException
    {
        validateClientFields(client);

        if (clientRepository.get(client.getDocumentType(), client.getDocumentNumber()) != null)
            throw new ClientConflictException(client);

        client = clientRepository.saveAndFlush(client);
        return client;
    }

    @Override
    public List<ClientDTO> getClients(String province)
    {
        List<ClientDTO> clients = clientRepository.getAll();

        if (StringUtils.isNotBlank(province))
            clients = clients.stream()
                    .filter(client -> client.getProvince().equals(province))
                    .collect(Collectors.toList());

        return clients;
    }

    private void validateClientFields(ClientDTO client) throws UncompletedFieldClientException
    {
        if (StringUtils.isBlank(client.getDocumentType()))
            throw new UncompletedFieldClientException("documentType", client);

        if (StringUtils.isBlank(client.getDocumentNumber()))
            throw new UncompletedFieldClientException("documentNumber", client);

        if (StringUtils.isBlank(client.getName()))
            throw new UncompletedFieldClientException("name", client);

        if (StringUtils.isBlank(client.getLastName()))
            throw new UncompletedFieldClientException("lastName", client);
    }
}
