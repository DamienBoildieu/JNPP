package jnpp.service.exceptions.clients;

public class InformationException extends ClientException {

    public InformationException() {
    }

    public InformationException(String message) {
        super(message);
    }

    public InformationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InformationException(Throwable cause) {
        super(cause);
    }

    public InformationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
