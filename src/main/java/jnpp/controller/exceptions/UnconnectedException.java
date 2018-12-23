package jnpp.controller.exceptions;

/**
 * Exception lancé quand un HttpSession ne représente pas un utilisateur
 * connecté
 */
public class UnconnectedException extends Exception {

    private static final long serialVersionUID = -3717132425088581889L;

    /**
     * Constructeur
     */
    public UnconnectedException() {
        super("Session is not filled");
    }
}
