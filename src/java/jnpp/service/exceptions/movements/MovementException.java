package jnpp.service.exceptions.movements;

import jnpp.service.exceptions.ServiceException;

public class MovementException extends ServiceException {

    public MovementException() {}

    public MovementException(String message) {
        super(message);
    }

    public MovementException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovementException(Throwable cause) {
        super(cause);
    }

    public MovementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
