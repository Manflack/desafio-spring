package ar.com.manflack.manflackshops.domain.exception;

import ar.com.manflack.manflackshops.app.dto.ClientDTO;
import lombok.Data;

@Data
public class ClientConflictException extends Exception
{
    private static final String errorCode = "CLIENT_ALREADY_EXISTS";
    private ClientDTO client;

    public ClientConflictException(ClientDTO client)
    {
        super("Client already exists with the document type and document number");
        this.client = client;
    }
}
