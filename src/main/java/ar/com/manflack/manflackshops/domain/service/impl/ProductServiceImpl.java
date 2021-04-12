package ar.com.manflack.manflackshops.domain.service.impl;

import ar.com.manflack.manflackshops.app.dto.ArticleDTO;
import ar.com.manflack.manflackshops.app.dto.ProductDTO;
import ar.com.manflack.manflackshops.app.dto.TicketDTO;
import ar.com.manflack.manflackshops.app.filter.ProductFilter;
import ar.com.manflack.manflackshops.app.rest.request.ProductRequest;
import ar.com.manflack.manflackshops.domain.UtilitiesComponent;
import ar.com.manflack.manflackshops.domain.exception.ArticlesConflictException;
import ar.com.manflack.manflackshops.domain.exception.ManyFiltersException;
import ar.com.manflack.manflackshops.domain.exception.NotImplementedFilterOrder;
import ar.com.manflack.manflackshops.domain.repository.ProductRepository;
import ar.com.manflack.manflackshops.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService
{
    @Autowired
    ProductRepository repository;

    @Autowired
    UtilitiesComponent domainUtils;

    @Override
    public List<ProductDTO> getAllProducts(ProductFilter filter) throws NotImplementedFilterOrder, ManyFiltersException
    {
        List<ProductDTO> products = repository.getAll();
        Stream<ProductDTO> filteredArray = products.stream();

        filteredArray = domainUtils.filterProducts(filter, filteredArray);

        return filteredArray.collect(Collectors.toList());
    }

    @Override
    public TicketDTO purchaseRequest(ProductRequest request) throws ArticlesConflictException
    {
        List<ArticleDTO> articlesToPurchase = new ArrayList<>();
        AtomicReference<Integer> total = new AtomicReference<>(0);

        for (ArticleDTO article : request.getArticles())
        {
            ProductDTO product = repository.getByProductIdAndNameAndBrandAndQuantity(article.getProductId(),
                    article.getName(),
                    article.getBrand());

            if (product != null)
            {
                Integer quantity = article.getQuantity();
                if (article.getQuantity() > product.getQuantity())
                    quantity = product.getQuantity();

                articlesToPurchase.add(article);
                Integer finalQuantity = quantity;
                total.updateAndGet(v -> v + product.getPrice() * finalQuantity);
            }
        }
        TicketDTO ticket = new TicketDTO();
        ticket.setArticles(articlesToPurchase);
        ticket.setTotal(total.get());

        return ticket;
    }
}
