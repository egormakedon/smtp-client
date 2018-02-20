package by.makedon.smtpclient.exception;

public class SmtpSocketException extends Exception {
    public SmtpSocketException() {
    }

    public SmtpSocketException(String message) {
        super(message);
    }

    public SmtpSocketException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmtpSocketException(Throwable cause) {
        super(cause);
    }

    public SmtpSocketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
