package jnpp.service.exceptions.advisors;

import jnpp.service.exceptions.ServiceException;

public abstract class AdvisorException extends ServiceException {

    public AdvisorException() {
    }

    public AdvisorException(String message) {
        super(message);
    }

    public AdvisorException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdvisorException(Throwable cause) {
        super(cause);
    }

    public AdvisorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
