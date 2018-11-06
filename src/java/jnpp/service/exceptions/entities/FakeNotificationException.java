package jnpp.service.exceptions.entities;

public class FakeNotificationException extends FakeEntityException {

    public FakeNotificationException() {
    }

    public FakeNotificationException(String message) {
        super(message);
    }

    public FakeNotificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FakeNotificationException(Throwable cause) {
        super(cause);
    }

    public FakeNotificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
