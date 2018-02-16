package by.makedon.smtpclient.exception;

public class MethodNotSupportedException extends Exception {
    public MethodNotSupportedException() {
    }

    public MethodNotSupportedException(String message) {
        super(message);
    }

    public MethodNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodNotSupportedException(Throwable cause) {
        super(cause);
    }

    public MethodNotSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
