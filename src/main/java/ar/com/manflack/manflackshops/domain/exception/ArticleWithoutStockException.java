package ar.com.manflack.manflackshops.domain.exception;

public class ArticleWithoutStockException extends Exception
{
    private static final String errorCode = "NO_STOCK_PRODUCT";

    public ArticleWithoutStockException(String message)
    {
        super(message);
    }
}
