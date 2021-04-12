package ar.com.manflack.manflackshops.domain.service;

import java.util.List;

import ar.com.manflack.manflackshops.app.dto.ClientDTO;
import ar.com.manflack.manflackshops.app.dto.TicketDTO;
import ar.com.manflack.manflackshops.app.rest.request.ProductRequest;
import ar.com.manflack.manflackshops.domain.exception.ArticleWithoutStockException;
import ar.com.manflack.manflackshops.domain.exception.ArticlesConflictException;
import ar.com.manflack.manflackshops.domain.exception.ClientConflictException;
import ar.com.manflack.manflackshops.domain.exception.UncompletedFieldClientException;

public interface BonusService
{
    TicketDTO purchaseRequest(ProductRequest request) throws ArticlesConflictException, ArticleWithoutStockException;

    ClientDTO signup(ClientDTO client) throws ClientConflictException, UncompletedFieldClientException;

    List<ClientDTO> getClients(String province);
}
