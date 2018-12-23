package jnpp.service.exceptions.entities;

public class FakeAppointmentException extends FakeEntityException {

    public FakeAppointmentException() {
    }

    public FakeAppointmentException(String message) {
        super(message);
    }

    public FakeAppointmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public FakeAppointmentException(Throwable cause) {
        super(cause);
    }

    public FakeAppointmentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
