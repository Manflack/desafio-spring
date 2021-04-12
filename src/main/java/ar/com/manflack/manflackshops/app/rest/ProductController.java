package ar.com.manflack.manflackshops.app.rest;

import ar.com.manflack.manflackshops.app.dto.StatusDTO;
import ar.com.manflack.manflackshops.app.dto.TicketDTO;
import ar.com.manflack.manflackshops.app.filter.ProductFilter;
import ar.com.manflack.manflackshops.app.rest.request.ProductRequest;
import ar.com.manflack.manflackshops.app.rest.response.ProductResponse;
import ar.com.manflack.manflackshops.domain.UtilitiesComponent;
import ar.com.manflack.manflackshops.domain.exception.ArticlesConflictException;
import ar.com.manflack.manflackshops.domain.exception.ManyFiltersException;
import ar.com.manflack.manflackshops.domain.exception.NotImplementedFilterOrder;
import ar.com.manflack.manflackshops.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController
{
    @Autowired
    ProductService productService;

    @Autowired
    UtilitiesComponent domainUtils;

    @GetMapping("/api/v1/articles")
    public ResponseEntity<?> getAllProducts(@RequestParam(required = false) Integer productId,
            @RequestParam(required = false) String name, @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand, @RequestParam(required = false) String price,
            @RequestParam(required = false) String freeShipping, @RequestParam(required = false) String prestige,
            @RequestParam(required = false) String order) throws ManyFiltersException, NotImplementedFilterOrder
    {
        ProductFilter filter =
                domainUtils.getProductFilter(productId, name, category, brand, price, freeShipping, prestige, order);

        return new ResponseEntity<>(productService.getAllProducts(filter), HttpStatus.OK);
    }

    @PostMapping(path = "/api/v1/purchase-request")
    public ResponseEntity<?> purchaseRequest(@RequestBody ProductRequest request) throws ArticlesConflictException
    {
        TicketDTO ticket = productService.purchaseRequest(request);

        StatusDTO status;
        if (ticket.getArticles().size() != request.getArticles().size())
            status = new StatusDTO(HttpStatus.BAD_REQUEST.value(),
                    "Uno o más artículos no se ha encontrado, por favor vuelva a realizar la petición");
        else
            status = new StatusDTO(HttpStatus.OK.value(), "La solicitud de compra se completó con éxito");

        return new ResponseEntity<>(new ProductResponse(ticket, status), HttpStatus.valueOf(status.getCode()));
    }

}
