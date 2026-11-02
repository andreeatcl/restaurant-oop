package exceptions;

public class CampGolException extends Exception {
    public CampGolException(String message) {
        super(message);
    }

    public CampGolException(String message, Throwable cause) {
        super(message, cause);
    }
}
