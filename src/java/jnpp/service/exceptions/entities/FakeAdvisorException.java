package jnpp.service.exceptions.entities;

public class FakeAdvisorException extends FakeEntityException {

    public FakeAdvisorException() {}

    public FakeAdvisorException(String message) {
        super(message);
    }

    public FakeAdvisorException(String message, Throwable cause) {
        super(message, cause);
    }

    public FakeAdvisorException(Throwable cause) {
        super(cause);
    }

    public FakeAdvisorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
