package jnpp.service.exceptions.movements;

public class AccountTypeException extends MovementException {

    public AccountTypeException() {
    }

    public AccountTypeException(String message) {
        super(message);
    }

    public AccountTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountTypeException(Throwable cause) {
        super(cause);
    }

    public AccountTypeException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
