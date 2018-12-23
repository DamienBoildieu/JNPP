package jnpp.service.exceptions.entities;

public class FakeDebitAuthorizationException extends FakeEntityException {

    public FakeDebitAuthorizationException() {
    }

    public FakeDebitAuthorizationException(String message) {
        super(message);
    }

    public FakeDebitAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FakeDebitAuthorizationException(Throwable cause) {
        super(cause);
    }

    public FakeDebitAuthorizationException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
