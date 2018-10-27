package jnpp.service.exceptions;

public class WrongAdvisorException extends ServiceException {

    public WrongAdvisorException() {}

    public WrongAdvisorException(String message) {
        super(message);
    }

    public WrongAdvisorException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongAdvisorException(Throwable cause) {
        super(cause);
    }

    public WrongAdvisorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
