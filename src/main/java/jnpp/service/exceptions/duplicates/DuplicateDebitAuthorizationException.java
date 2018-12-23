package jnpp.service.exceptions.duplicates;

public class DuplicateDebitAuthorizationException extends DuplicateException {

    public DuplicateDebitAuthorizationException() {
    }

    public DuplicateDebitAuthorizationException(String message) {
        super(message);
    }

    public DuplicateDebitAuthorizationException(String message,
            Throwable cause) {
        super(message, cause);
    }

    public DuplicateDebitAuthorizationException(Throwable cause) {
        super(cause);
    }

    public DuplicateDebitAuthorizationException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
