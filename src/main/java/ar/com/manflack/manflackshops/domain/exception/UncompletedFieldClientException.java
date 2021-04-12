package ar.com.manflack.manflackshops.domain.exception;

import ar.com.manflack.manflackshops.app.dto.ClientDTO;
import lombok.Data;

@Data
public class UncompletedFieldClientException extends Exception
{
    private static final String errorCode = "UNCOMPLETED_FIELD_CLIENT";
    private ClientDTO client;

    public UncompletedFieldClientException(String field, ClientDTO client)
    {
        super("The field '" + field + "' is empty, please update them");
    }
}
