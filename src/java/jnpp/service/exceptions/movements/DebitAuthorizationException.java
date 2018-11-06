package jnpp.service.exceptions.movements;

import jnpp.service.exceptions.accounts.AccountException;

public class DebitAuthorizationException extends AccountException {

    public DebitAuthorizationException() {
    }

    public DebitAuthorizationException(String message) {
        super(message);
    }

    public DebitAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DebitAuthorizationException(Throwable cause) {
        super(cause);
    }

    public DebitAuthorizationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
