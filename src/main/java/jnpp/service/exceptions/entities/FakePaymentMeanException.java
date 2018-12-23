package jnpp.service.exceptions.entities;

public class FakePaymentMeanException extends FakeEntityException {

    public FakePaymentMeanException() {
    }

    public FakePaymentMeanException(String message) {
        super(message);
    }

    public FakePaymentMeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public FakePaymentMeanException(Throwable cause) {
        super(cause);
    }

    public FakePaymentMeanException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
