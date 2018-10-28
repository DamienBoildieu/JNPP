package jnpp.service.exceptions.accounts;

public class CurrentAccountOwningException extends AccountOwningException {

    public CurrentAccountOwningException() {}

    public CurrentAccountOwningException(String message) {
        super(message);
    }

    public CurrentAccountOwningException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrentAccountOwningException(Throwable cause) {
        super(cause);
    }

    public CurrentAccountOwningException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
