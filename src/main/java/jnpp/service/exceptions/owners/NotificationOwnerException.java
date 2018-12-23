package jnpp.service.exceptions.owners;

public class NotificationOwnerException extends OwnerException {

    public NotificationOwnerException() {
    }

    public NotificationOwnerException(String message) {
        super(message);
    }

    public NotificationOwnerException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotificationOwnerException(Throwable cause) {
        super(cause);
    }

    public NotificationOwnerException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
