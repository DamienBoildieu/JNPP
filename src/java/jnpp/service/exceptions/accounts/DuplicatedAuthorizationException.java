package jnpp.service.exceptions.accounts;

public class DuplicatedAuthorizationException extends AccountException {

    public DuplicatedAuthorizationException() {}

    public DuplicatedAuthorizationException(String message) {
        super(message);
    }

    public DuplicatedAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedAuthorizationException(Throwable cause) {
        super(cause);
    }

    public DuplicatedAuthorizationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
