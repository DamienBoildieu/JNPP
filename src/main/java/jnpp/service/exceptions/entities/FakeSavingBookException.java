package jnpp.service.exceptions.entities;

public class FakeSavingBookException extends FakeEntityException {

    public FakeSavingBookException() {
    }

    public FakeSavingBookException(String message) {
        super(message);
    }

    public FakeSavingBookException(String message, Throwable cause) {
        super(message, cause);
    }

    public FakeSavingBookException(Throwable cause) {
        super(cause);
    }

    public FakeSavingBookException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
