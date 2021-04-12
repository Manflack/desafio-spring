package ar.com.manflack.manflackshops.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientDTO
{
    private String documentType;
    private String documentNumber;
    private String name;
    private String lastName;
    private String province;
}
