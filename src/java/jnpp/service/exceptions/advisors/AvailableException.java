package jnpp.service.exceptions.advisors;

public class AvailableException extends AdvisorException {

    public AvailableException() {
    }

    public AvailableException(String message) {
        super(message);
    }

    public AvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public AvailableException(Throwable cause) {
        super(cause);
    }

    public AvailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
