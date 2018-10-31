package jnpp.service.exceptions.owners;

public class AdvisorOwnerException extends OwnerException {

    public AdvisorOwnerException() {}

    public AdvisorOwnerException(String message) {
        super(message);
    }

    public AdvisorOwnerException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdvisorOwnerException(Throwable cause) {
        super(cause);
    }

    public AdvisorOwnerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
