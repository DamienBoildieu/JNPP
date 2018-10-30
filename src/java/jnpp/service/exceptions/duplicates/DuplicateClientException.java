package jnpp.service.exceptions.duplicates;

public class DuplicateClientException extends DuplicateException {

    public DuplicateClientException() {}

    public DuplicateClientException(String message) {
        super(message);
    }

    public DuplicateClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateClientException(Throwable cause) {
        super(cause);
    }

    public DuplicateClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
