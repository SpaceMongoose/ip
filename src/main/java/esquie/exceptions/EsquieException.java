package esquie.exceptions;
/**
 * EsquieException represents exceptions specific to Esquie.
 */
public class EsquieException extends Exception {

    /**
     * Initializes new error specific to Esquie with the following error message.
     *
     * @param message Text describing the error.
     */
    public EsquieException(String message) {
        super(message);
    }
}
