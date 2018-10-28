package jnpp.service.exceptions.accounts;

public class DuplicatedCurrentAccountException extends AccountException {

    public DuplicatedCurrentAccountException() {}

    public DuplicatedCurrentAccountException(String message) {
        super(message);
    }

    public DuplicatedCurrentAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedCurrentAccountException(Throwable cause) {
        super(cause);
    }

    public DuplicatedCurrentAccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
