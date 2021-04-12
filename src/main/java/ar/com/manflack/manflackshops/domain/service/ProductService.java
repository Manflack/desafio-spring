package ar.com.manflack.manflackshops.domain.service;

import ar.com.manflack.manflackshops.app.dto.ProductDTO;
import ar.com.manflack.manflackshops.app.dto.TicketDTO;
import ar.com.manflack.manflackshops.app.filter.ProductFilter;
import ar.com.manflack.manflackshops.app.rest.request.ProductRequest;
import ar.com.manflack.manflackshops.domain.exception.ArticlesConflictException;
import ar.com.manflack.manflackshops.domain.exception.ManyFiltersException;
import ar.com.manflack.manflackshops.domain.exception.NotImplementedFilterOrder;

import java.util.List;

public interface ProductService
{
    List<ProductDTO> getAllProducts(ProductFilter filter) throws NotImplementedFilterOrder, ManyFiltersException;

    TicketDTO purchaseRequest(ProductRequest request) throws ArticlesConflictException;
}
