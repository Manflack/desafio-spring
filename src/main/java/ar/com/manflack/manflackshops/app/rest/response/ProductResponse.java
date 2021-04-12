package ar.com.manflack.manflackshops.app.rest.response;

import ar.com.manflack.manflackshops.app.dto.StatusDTO;
import ar.com.manflack.manflackshops.app.dto.TicketDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductResponse
{
    private TicketDTO ticket;
    private StatusDTO statusCode;
}
