package se.nackademin.bankomatdb;

public class NoSuchAccountException extends Exception {
    public NoSuchAccountException() {
        super();
    }

    public NoSuchAccountException(String message) {
        super(message);
    }

    public NoSuchAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchAccountException(Throwable cause) {
        super(cause);
    }

    protected NoSuchAccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
