package ar.com.manflack.manflackshops.app.handler;

import ar.com.manflack.manflackshops.app.dto.StatusDTO;
import ar.com.manflack.manflackshops.app.rest.response.ClientRegistrationResponse;
import ar.com.manflack.manflackshops.app.rest.response.ProductResponse;
import ar.com.manflack.manflackshops.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestHandlerException
{
    @ExceptionHandler(ArticlesConflictException.class)
    protected ResponseEntity<?> conflictException(ArticlesConflictException ex)
    {
        StatusDTO status = new StatusDTO(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(new ProductResponse(null, status), HttpStatus.valueOf(status.getCode()));
    }

    @ExceptionHandler(ManyFiltersException.class)
    protected ResponseEntity<?> manyFiltersException(ManyFiltersException ex)
    {
        StatusDTO status = new StatusDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(new ProductResponse(null, status), HttpStatus.valueOf(status.getCode()));
    }

    @ExceptionHandler(NotImplementedFilterOrder.class)
    protected ResponseEntity<?> manyFiltersException(NotImplementedFilterOrder ex)
    {
        StatusDTO status = new StatusDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(new ProductResponse(null, status), HttpStatus.valueOf(status.getCode()));
    }

    @ExceptionHandler(UncompletedFieldClientException.class)
    protected ResponseEntity<?> uncompletedFields(UncompletedFieldClientException ex)
    {
        StatusDTO status = new StatusDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(new ClientRegistrationResponse(ex.getClient(), status),
                HttpStatus.valueOf(status.getCode()));
    }

    @ExceptionHandler(ClientConflictException.class)
    protected ResponseEntity<?> uncompletedFields(ClientConflictException ex)
    {
        StatusDTO status = new StatusDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(new ClientRegistrationResponse(ex.getClient(), status),
                HttpStatus.valueOf(status.getCode()));
    }
}
