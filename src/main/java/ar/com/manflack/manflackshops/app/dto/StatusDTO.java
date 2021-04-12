package ar.com.manflack.manflackshops.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusDTO
{
    private Integer code;
    private String message;
}
