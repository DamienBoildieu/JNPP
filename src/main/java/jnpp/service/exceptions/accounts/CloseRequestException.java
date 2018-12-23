package jnpp.service.exceptions.accounts;

public class CloseRequestException extends AccountException {

    public CloseRequestException() {
    }

    public CloseRequestException(String message) {
        super(message);
    }

    public CloseRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloseRequestException(Throwable cause) {
        super(cause);
    }

    public CloseRequestException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
