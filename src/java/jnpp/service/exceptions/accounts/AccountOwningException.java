package jnpp.service.exceptions.accounts;

public class AccountOwningException extends AccountException {

    public AccountOwningException() {}

    public AccountOwningException(String message) {
        super(message);
    }

    public AccountOwningException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountOwningException(Throwable cause) {
        super(cause);
    }

    public AccountOwningException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
