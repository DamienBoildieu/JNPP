package jnpp.service.exceptions.advisors;

import jnpp.service.exceptions.ServiceException;

public class DateException extends AdvisorException {

    public DateException() {}

    public DateException(String message) {
        super(message);
    }

    public DateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateException(Throwable cause) {
        super(cause);
    }

    public DateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}