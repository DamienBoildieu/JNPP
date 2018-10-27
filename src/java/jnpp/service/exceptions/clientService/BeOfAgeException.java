package jnpp.service.exceptions.clientService;

public class BeOfAgeException extends ClientServiceException {

    public BeOfAgeException() {}

    public BeOfAgeException(String message) {
        super(message);
    }

    public BeOfAgeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeOfAgeException(Throwable cause) {
        super(cause);
    }

    public BeOfAgeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
