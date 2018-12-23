package jnpp.service.exceptions.advisors;

public class NoAdvisorException extends AdvisorException {

    public NoAdvisorException() {
    }

    public NoAdvisorException(String message) {
        super(message);
    }

    public NoAdvisorException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAdvisorException(Throwable cause) {
        super(cause);
    }

    public NoAdvisorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
