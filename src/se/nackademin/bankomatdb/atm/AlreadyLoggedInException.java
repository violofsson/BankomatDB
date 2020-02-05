package se.nackademin.bankomatdb.atm;

public class AlreadyLoggedInException extends Exception {
    public AlreadyLoggedInException() {
        super();
    }

    public AlreadyLoggedInException(String message) {
        super(message);
    }

    public AlreadyLoggedInException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyLoggedInException(Throwable cause) {
        super(cause);
    }

    protected AlreadyLoggedInException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
