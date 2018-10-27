package jnpp.service.exceptions.clientService;

public class ClosureException extends ClientServiceException {

    public ClosureException() {}

    public ClosureException(String message) {
        super(message);
    }

    public ClosureException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClosureException(Throwable cause) {
        super(cause);
    }

    public ClosureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
