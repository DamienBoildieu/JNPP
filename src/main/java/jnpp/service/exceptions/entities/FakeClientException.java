package jnpp.service.exceptions.entities;

public class FakeClientException extends FakeEntityException {

    public FakeClientException() {
    }

    public FakeClientException(String message) {
        super(message);
    }

    public FakeClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public FakeClientException(Throwable cause) {
        super(cause);
    }

    public FakeClientException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
