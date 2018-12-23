package jnpp.service.exceptions.entities;

public class FakeShareException extends FakeEntityException {

    public FakeShareException() {
    }

    public FakeShareException(String message) {
        super(message);
    }

    public FakeShareException(String message, Throwable cause) {
        super(message, cause);
    }

    public FakeShareException(Throwable cause) {
        super(cause);
    }

    public FakeShareException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
