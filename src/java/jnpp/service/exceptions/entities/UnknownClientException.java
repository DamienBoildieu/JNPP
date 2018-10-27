package jnpp.service.exceptions.entities;

public class UnknownClientException extends UnknownEntityException {

    public UnknownClientException() {}

    public UnknownClientException(String message) {
        super(message);
    }

    public UnknownClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownClientException(Throwable cause) {
        super(cause);
    }

    public UnknownClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
