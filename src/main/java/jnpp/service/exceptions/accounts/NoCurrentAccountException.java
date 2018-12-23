package jnpp.service.exceptions.accounts;

public class NoCurrentAccountException extends AccountException {

    public NoCurrentAccountException() {
    }

    public NoCurrentAccountException(String message) {
        super(message);
    }

    public NoCurrentAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCurrentAccountException(Throwable cause) {
        super(cause);
    }

    public NoCurrentAccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
