package jnpp.service.exceptions.accounts;

public class DuplicatedAccountException extends AccountException {

    public DuplicatedAccountException() {}

    public DuplicatedAccountException(String message) {
        super(message);
    }

    public DuplicatedAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedAccountException(Throwable cause) {
        super(cause);
    }

    public DuplicatedAccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
