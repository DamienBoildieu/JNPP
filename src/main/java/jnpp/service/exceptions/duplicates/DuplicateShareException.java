package jnpp.service.exceptions.duplicates;

public class DuplicateShareException extends DuplicateException {

    public DuplicateShareException() {
    }

    public DuplicateShareException(String message) {
        super(message);
    }

    public DuplicateShareException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateShareException(Throwable cause) {
        super(cause);
    }

    public DuplicateShareException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
