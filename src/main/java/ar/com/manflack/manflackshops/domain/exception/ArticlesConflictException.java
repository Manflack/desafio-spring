package ar.com.manflack.manflackshops.domain.exception;

import lombok.Data;

@Data
public class ArticlesConflictException extends Exception
{
    private static final String errorCode = "MANY_TYPE_ARTICLES";

    public ArticlesConflictException()
    {
        super("One or more articles of the list are equals to another one");
    }
}
