package ar.com.manflack.manflackshops.app.rest;

import java.util.List;

import ar.com.manflack.manflackshops.app.dto.ClientDTO;
import ar.com.manflack.manflackshops.app.dto.StatusDTO;
import ar.com.manflack.manflackshops.app.dto.TicketDTO;
import ar.com.manflack.manflackshops.app.rest.request.ProductRequest;
import ar.com.manflack.manflackshops.app.rest.response.ClientRegistrationResponse;
import ar.com.manflack.manflackshops.app.rest.response.ProductResponse;
import ar.com.manflack.manflackshops.domain.exception.ArticleWithoutStockException;
import ar.com.manflack.manflackshops.domain.exception.ArticlesConflictException;
import ar.com.manflack.manflackshops.domain.exception.ClientConflictException;
import ar.com.manflack.manflackshops.domain.exception.UncompletedFieldClientException;
import ar.com.manflack.manflackshops.domain.service.BonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BonusController
{
    @Autowired
    BonusService bonusService;

    @PostMapping(path = "/api/v2/purchase-request")
    public ResponseEntity<?> purchaseRequest(@RequestBody ProductRequest request)
            throws ArticlesConflictException, ArticleWithoutStockException
    {
        TicketDTO ticket = bonusService.purchaseRequest(request);

        StatusDTO status;
        if (ticket.getArticles().size() != request.getArticles().size())
            status = new StatusDTO(HttpStatus.BAD_REQUEST.value(),
                    "Uno o más artículos no se ha encontrado, por favor vuelva a realizar la petición");
        else
            status = new StatusDTO(HttpStatus.OK.value(), "La solicitud de compra se completó con éxito");

        return new ResponseEntity<>(new ProductResponse(ticket, status), HttpStatus.valueOf(status.getCode()));
    }

    @PostMapping(path = "/api/v2/client/signup")
    public ResponseEntity<?> signUpClient(@RequestBody ClientDTO client)
            throws ClientConflictException, UncompletedFieldClientException
    {
        client = bonusService.signup(client);
        StatusDTO status = new StatusDTO(HttpStatus.CREATED.value(), "New client registered successfully");
        return new ResponseEntity<>(new ClientRegistrationResponse(client, status),
                HttpStatus.valueOf(status.getCode()));
    }

    @GetMapping(path = "/api/v2/clients")
    public ResponseEntity<?> getClients(@RequestParam(required = false) String province)
    {
        List<ClientDTO> clients = bonusService.getClients(province);
        StatusDTO status = new StatusDTO(HttpStatus.CREATED.value(), "New client registered successfully");
        return new ResponseEntity<>(clients, HttpStatus.valueOf(status.getCode()));
    }
}
