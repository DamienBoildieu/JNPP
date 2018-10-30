package jnpp.service.exceptions.owners;

import jnpp.service.exceptions.ServiceException;

public class OwnerException extends ServiceException {

    public OwnerException() {}

    public OwnerException(String message) {
        super(message);
    }

    public OwnerException(String message, Throwable cause) {
        super(message, cause);
    }

    public OwnerException(Throwable cause) {
        super(cause);
    }

    public OwnerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
