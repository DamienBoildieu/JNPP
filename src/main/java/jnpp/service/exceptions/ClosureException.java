package jnpp.service.exceptions;

public class ClosureException extends ServiceException {

    public ClosureException() {
    }

    public ClosureException(String message) {
        super(message);
    }

    public ClosureException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClosureException(Throwable cause) {
        super(cause);
    }

    public ClosureException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
