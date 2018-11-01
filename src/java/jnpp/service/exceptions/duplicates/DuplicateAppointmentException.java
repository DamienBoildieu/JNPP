package jnpp.service.exceptions.duplicates;

public class DuplicateAppointmentException extends DuplicateException {

    public DuplicateAppointmentException() {
    }

    public DuplicateAppointmentException(String message) {
        super(message);
    }

    public DuplicateAppointmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateAppointmentException(Throwable cause) {
        super(cause);
    }

    public DuplicateAppointmentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
