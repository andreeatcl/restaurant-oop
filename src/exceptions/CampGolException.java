package exceptions;

/**
 * exceptie aruncata atunci cand un camp obligatoriu este nul sau gol
 */
public class CampGolException extends Exception {
    /**
     * @param message mesajul care descrie campul gol
     */
    public CampGolException(String message) {
        super(message);
    }

    /**
     * @param message mesajul care descrie campul gol
     * @param cause   exceptia originala care a dus la aceasta eroare
     */
    public CampGolException(String message, Throwable cause) {
        super(message, cause);
    }
}
