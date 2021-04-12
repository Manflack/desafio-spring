package ar.com.manflack.manflackshops.app.rest.response;

import ar.com.manflack.manflackshops.app.dto.ClientDTO;
import ar.com.manflack.manflackshops.app.dto.StatusDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClientRegistrationResponse
{
    private ClientDTO client;
    private StatusDTO status;
}
