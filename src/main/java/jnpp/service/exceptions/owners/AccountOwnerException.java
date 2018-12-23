package jnpp.service.exceptions.owners;

public class AccountOwnerException extends OwnerException {

    public AccountOwnerException() {
    }

    public AccountOwnerException(String message) {
        super(message);
    }

    public AccountOwnerException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountOwnerException(Throwable cause) {
        super(cause);
    }

    public AccountOwnerException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
