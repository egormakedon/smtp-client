package client.command.exception;

public class RuntimeError extends Exception {
    public RuntimeError(String message, Throwable cause) {
        super(message, cause);
    }
    public RuntimeError(Throwable cause) {
        super(cause);
    }
}
