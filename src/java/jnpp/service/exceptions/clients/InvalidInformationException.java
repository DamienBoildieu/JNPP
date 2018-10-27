package jnpp.service.exceptions.clients;

public class InvalidInformationException extends ClientServiceException {

    public InvalidInformationException() {}

    public InvalidInformationException(String message) {
        super(message);
    }

    public InvalidInformationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInformationException(Throwable cause) {
        super(cause);
    }

    public InvalidInformationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
