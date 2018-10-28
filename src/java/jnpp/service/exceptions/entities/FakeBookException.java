package jnpp.service.exceptions.entities;

public class FakeBookException extends FakeEntityException {

    public FakeBookException() {}

    public FakeBookException(String message) {
        super(message);
    }

    public FakeBookException(String message, Throwable cause) {
        super(message, cause);
    }

    public FakeBookException(Throwable cause) {
        super(cause);
    }

    public FakeBookException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
