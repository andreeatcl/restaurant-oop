package exceptions;

/**
 * Exceptie aruncata cand un produs sau o comanda cautata dupa id nu exista
 */
public class ProdusInexistentException extends Exception {

    /**
     *
     * @param message mesajul care descrie elementul negasit
     */
    public ProdusInexistentException(String message) {
        super(message);
    }

    /**
     * @param message mesajul care descrie datele invalide
     * @param cause   exceptia originala care a dus la aceasta eroare
     */
    public ProdusInexistentException(String message, Throwable cause) {
        super(message, cause);
    }
}
