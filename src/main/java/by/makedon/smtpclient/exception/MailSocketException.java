package by.makedon.smtpclient.exception;

public class MailSocketException extends Exception {
    public MailSocketException() {
    }

    public MailSocketException(String message) {
        super(message);
    }

    public MailSocketException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailSocketException(Throwable cause) {
        super(cause);
    }

    public MailSocketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
