package jnpp.service.exceptions.entities;

import jnpp.service.exceptions.ServiceException;

public abstract class FakeEntityException extends ServiceException {

    public FakeEntityException() {
    }

    public FakeEntityException(String message) {
        super(message);
    }

    public FakeEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public FakeEntityException(Throwable cause) {
        super(cause);
    }

    public FakeEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
