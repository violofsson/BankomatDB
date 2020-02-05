package se.nackademin.bankomatdb;

public class InvalidDataEntryException extends Exception {
    public InvalidDataEntryException() {
        super();
    }

    public InvalidDataEntryException(String message) {
        super(message);
    }

    public InvalidDataEntryException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataEntryException(Throwable cause) {
        super(cause);
    }

    protected InvalidDataEntryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
