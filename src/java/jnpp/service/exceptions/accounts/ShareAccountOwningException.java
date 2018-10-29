
package jnpp.service.exceptions.accounts;

public class ShareAccountOwningException extends CurrentAccountOwningException {

    public ShareAccountOwningException() {}

    public ShareAccountOwningException(String message) {
        super(message);
    }

    public ShareAccountOwningException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShareAccountOwningException(Throwable cause) {
        super(cause);
    }

    public ShareAccountOwningException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
