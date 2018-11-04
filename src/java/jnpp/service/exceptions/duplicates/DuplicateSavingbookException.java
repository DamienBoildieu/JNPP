package jnpp.service.exceptions.duplicates;

public class DuplicateSavingbookException extends DuplicateException {

    public DuplicateSavingbookException() {}

    public DuplicateSavingbookException(String message) {
        super(message);
    }

    public DuplicateSavingbookException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateSavingbookException(Throwable cause) {
        super(cause);
    }

    public DuplicateSavingbookException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
