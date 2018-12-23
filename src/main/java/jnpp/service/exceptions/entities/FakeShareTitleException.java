package jnpp.service.exceptions.entities;

public class FakeShareTitleException extends FakeEntityException {

    public FakeShareTitleException() {
    }

    public FakeShareTitleException(String message) {
        super(message);
    }

    public FakeShareTitleException(String message, Throwable cause) {
        super(message, cause);
    }

    public FakeShareTitleException(Throwable cause) {
        super(cause);
    }

    public FakeShareTitleException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
