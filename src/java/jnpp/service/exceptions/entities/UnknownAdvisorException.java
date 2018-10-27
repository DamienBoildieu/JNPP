package jnpp.service.exceptions.entities;

public class UnknownAdvisorException extends UnknownEntityException {

    public UnknownAdvisorException() {}

    public UnknownAdvisorException(String message) {
        super(message);
    }

    public UnknownAdvisorException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownAdvisorException(Throwable cause) {
        super(cause);
    }

    public UnknownAdvisorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
