package exceptions;

public class ComandaGoalaException extends Exception {
    public ComandaGoalaException(String message) {
        super(message);
    }

    public ComandaGoalaException(String message, Throwable cause) {
        super(message, cause);
    }
}
