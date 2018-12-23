package jnpp.service.exceptions.duplicates;

public class DuplicateAdvisorException extends DuplicateException {

    public DuplicateAdvisorException() {
    }

    public DuplicateAdvisorException(String message) {
        super(message);
    }

    public DuplicateAdvisorException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateAdvisorException(Throwable cause) {
        super(cause);
    }

    public DuplicateAdvisorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
