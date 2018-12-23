package jnpp.service.exceptions.movements;

public class OverdraftException extends MovementException {

    public OverdraftException() {
    }

    public OverdraftException(String message) {
        super(message);
    }

    public OverdraftException(String message, Throwable cause) {
        super(message, cause);
    }

    public OverdraftException(Throwable cause) {
        super(cause);
    }

    public OverdraftException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
