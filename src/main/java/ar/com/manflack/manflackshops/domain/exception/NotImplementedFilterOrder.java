package ar.com.manflack.manflackshops.domain.exception;

public class NotImplementedFilterOrder extends Exception
{
    private static final String errorCode = "INVALID_FILTER_NOT_EXISTS";

    public NotImplementedFilterOrder() {
        super("Invalid filter, use another one");
    }
}
