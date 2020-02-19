package se.nackademin.bankomatdb;

public class NoSuchRecordException extends Exception {
    public NoSuchRecordException() {
        super();
    }

    public NoSuchRecordException(String message) {
        super(message);
    }

    public NoSuchRecordException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchRecordException(Throwable cause) {
        super(cause);
    }

    public NoSuchRecordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
