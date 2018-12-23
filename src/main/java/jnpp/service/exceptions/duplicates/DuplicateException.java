package jnpp.service.exceptions.duplicates;

import jnpp.service.exceptions.ServiceException;

public abstract class DuplicateException extends ServiceException {

    public DuplicateException() {
    }

    public DuplicateException(String message) {
        super(message);
    }

    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateException(Throwable cause) {
        super(cause);
    }

    public DuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
