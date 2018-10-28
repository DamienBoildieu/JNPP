package jnpp.service.exceptions.accounts;

public class OwnerException extends AccountException {

    public OwnerException() {}

    public OwnerException(String message) {
        super(message);
    }

    public OwnerException(String message, Throwable cause) {
        super(message, cause);
    }

    public OwnerException(Throwable cause) {
        super(cause);
    }

    public OwnerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
