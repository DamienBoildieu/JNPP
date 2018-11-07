package jnpp.controller.exceptions;

/**
 * Exception lancé quand la session vaut null
 */
public class NullSessionException extends NullPointerException {
    
    private static final long serialVersionUID = 1252001743368841915L;
    /**
     * Constructeur
     */
    public NullSessionException() {
        super("A HttpSession needs to be set");
    }
}
