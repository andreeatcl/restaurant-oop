package exceptions;

public class DateInvalideException extends Exception {
    public DateInvalideException(String message) {
        super(message);
    }

    public DateInvalideException(String message, Throwable cause) {
        super(message, cause);
    }
}
