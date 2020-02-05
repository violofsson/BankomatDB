package se.nackademin.bankomatdb;

public class NoSuchLoanException extends Exception {
    public NoSuchLoanException() {
        super();
    }

    public NoSuchLoanException(String message) {
        super(message);
    }

    public NoSuchLoanException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchLoanException(Throwable cause) {
        super(cause);
    }

    protected NoSuchLoanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
