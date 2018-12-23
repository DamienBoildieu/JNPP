package jnpp.service.exceptions.owners;

public class AppointmentOwnerException extends OwnerException {

    public AppointmentOwnerException() {
    }

    public AppointmentOwnerException(String message) {
        super(message);
    }

    public AppointmentOwnerException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppointmentOwnerException(Throwable cause) {
        super(cause);
    }

    public AppointmentOwnerException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
