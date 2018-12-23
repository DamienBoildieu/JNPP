package jnpp.service.exceptions.accounts;

public class UnknownIdentityException extends AccountException {

    public UnknownIdentityException() {
    }

    public UnknownIdentityException(String message) {
        super(message);
    }

    public UnknownIdentityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownIdentityException(Throwable cause) {
        super(cause);
    }

    public UnknownIdentityException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
