package jnpp.service.exceptions.entities;

public class FakeAccountException extends FakeEntityException {

    public FakeAccountException() {
    }

    public FakeAccountException(String message) {
        super(message);
    }

    public FakeAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public FakeAccountException(Throwable cause) {
        super(cause);
    }

    public FakeAccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
