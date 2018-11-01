package jnpp.service.exceptions.accounts;

public class ClientTypeException extends AccountException {

    public ClientTypeException() {}

    public ClientTypeException(String message) {
        super(message);
    }

    public ClientTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientTypeException(Throwable cause) {
        super(cause);
    }

    public ClientTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
