package se.nackademin.bankomatdb;

public class NoSuchCustomerException extends Exception {
    public NoSuchCustomerException() {
        super();
    }

    public NoSuchCustomerException(String message) {
        super(message);
    }

    public NoSuchCustomerException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchCustomerException(Throwable cause) {
        super(cause);
    }

    protected NoSuchCustomerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
