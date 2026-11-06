package exceptions;

/**
 * Exceptie atuncata atunci cand o comanda ar ramane sau ar fi creata fara niciun produs
 */
public class ComandaGoalaException extends Exception {
    /**
     * @param message   mesajul care descrie eroarea
     */
    public ComandaGoalaException(String message) {
        super(message);
    }

    /**
     * @param message mesajul care descrie eroarea
     * @param cause   exceptia originala care a dus la aceasta eroare
     */
    public ComandaGoalaException(String message, Throwable cause) {
        super(message, cause);
    }
}
