package jnpp.controller.exceptions;

/**
 *
 * @author damien
 */
public class NullSessionException extends NullPointerException {
    
    private static final long serialVersionUID = 1252001743368841915L;
    
    public NullSessionException() {
        super("A HttpSession needs to be set");
    }
}
