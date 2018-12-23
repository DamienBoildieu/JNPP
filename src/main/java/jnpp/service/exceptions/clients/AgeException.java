package jnpp.service.exceptions.clients;

public class AgeException extends ClientException {

    public AgeException() {
    }

    public AgeException(String message) {
        super(message);
    }

    public AgeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AgeException(Throwable cause) {
        super(cause);
    }

    public AgeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
