package jnpp.service.exceptions.clients;

import jnpp.service.exceptions.ServiceException;

public class ClientServiceException extends ServiceException {

    public ClientServiceException() {}

    public ClientServiceException(String message) {
        super(message);
    }

    public ClientServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientServiceException(Throwable cause) {
        super(cause);
    }

    public ClientServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
