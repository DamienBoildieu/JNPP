package jnpp.service.exceptions.accounts;

public class ClosureRequestException extends AccountException {

    public ClosureRequestException() {}

    public ClosureRequestException(String message) {
        super(message);
    }

    public ClosureRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClosureRequestException(Throwable cause) {
        super(cause);
    }

    public ClosureRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
