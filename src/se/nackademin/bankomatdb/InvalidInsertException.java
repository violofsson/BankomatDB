package se.nackademin.bankomatdb;

public class InvalidInsertException extends Exception {
    public InvalidInsertException() {
        super();
    }

    public InvalidInsertException(String message) {
        super(message);
    }

    public InvalidInsertException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInsertException(Throwable cause) {
        super(cause);
    }

    protected InvalidInsertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
