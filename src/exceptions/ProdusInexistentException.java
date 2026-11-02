package exceptions;

public class ProdusInexistentException extends Exception {
    public ProdusInexistentException(String message) {
        super(message);
    }

    public ProdusInexistentException(String message, Throwable cause) {
        super(message, cause);
    }
}
