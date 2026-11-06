package exceptions;

/**
 * exceptie aruncata cand o valoare introdusa sau citita din fisier nu respecta regulile de validare
 * exemple: valori nepozitive, data in viitor, json invalid, optiune inexistenta
 */
public class DateInvalideException extends Exception {
    /**
     * @param message mesajul care descrie datele invalide
     */
    public DateInvalideException(String message) {
        super(message);
    }

    /**
     * @param message mesajul care descrie datele invalide
     * @param cause   exceptia originala care a dus la aceasta eroare
     */
    public DateInvalideException(String message, Throwable cause) {
        super(message, cause);
    }
}
