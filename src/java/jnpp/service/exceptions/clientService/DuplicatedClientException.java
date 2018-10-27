package jnpp.service.exceptions.clientService;

public class DuplicatedClientException extends ClientServiceException {

    public DuplicatedClientException() {}

    public DuplicatedClientException(String message) {
        super(message);
    }

    public DuplicatedClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedClientException(Throwable cause) {
        super(cause);
    }

    public DuplicatedClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
