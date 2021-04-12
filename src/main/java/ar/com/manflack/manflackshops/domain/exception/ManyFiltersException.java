package ar.com.manflack.manflackshops.domain.exception;

import lombok.Data;

@Data
public class ManyFiltersException extends Exception
{
    private static final String errorCode = "MANY_FILTERS";

    public ManyFiltersException(){
        super("Too many filters, use less params to be filtered");
    }
}
