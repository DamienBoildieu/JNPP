package jnpp.service.exceptions.entities;

public class UnknownAppointmentException extends Exception {
    
    public UnknownAppointmentException() {}

    public UnknownAppointmentException(String message) {
        super(message);
    }

    public UnknownAppointmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownAppointmentException(Throwable cause) {
        super(cause);
    }

    public UnknownAppointmentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
