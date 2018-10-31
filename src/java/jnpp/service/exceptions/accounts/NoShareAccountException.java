
package jnpp.service.exceptions.accounts;

public class NoShareAccountException extends AccountException {

    public NoShareAccountException() {}

    public NoShareAccountException(String message) {
        super(message);
    }

    public NoShareAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoShareAccountException(Throwable cause) {
        super(cause);
    }

    public NoShareAccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
