package jnpp.common;

import jnpp.exception.NullSessionException;
import javax.servlet.http.HttpSession;

/**
 * Classe permettant de gérer les HttpSession
 */
public class CSession {
    /**
     * Constructeur private
     */
    private CSession() {}
    /**
     * Supprime les attributs ajoutés par l'application
     * @param session la session que l'on veut supprimer
     */
    public static void clearSession(HttpSession session) {
        session.removeAttribute("firstName");
        session.removeAttribute("lastName");
    }
    /**
     * Ajoute l'attribut firstName à la session
     * @param session la session à modifier
     * @param firstName la valeur de firstName
     */
    public static void setFirstName(HttpSession session, String firstName) {
        if (session == null)
            throw new NullSessionException();
        session.setAttribute("firstName", firstName);
    }
    /**
     * Ajoute l'attribut lastName à la session
     * @param session la session à modifier
     * @param firstName la valeur de lastName
     */
    public static void setLastName(HttpSession session, String lastName) {
        if (session == null)
            throw new NullSessionException();
        session.setAttribute("lastName", lastName);
    }
    /**
     * Récupère l'attribut firstName de la session
     * @param session la session dont on veut récupérer l'attribut
     * @return la valeur de firstName ou null si l'attribut n'avait pas été ajouté
     */
    public static String getFirstName(HttpSession session) {
        if (session == null)
            throw new NullSessionException();
        return (String)session.getAttribute("firstName");
    }
    /**
     * Récupère l'attribut lastName de la session
     * @param session la session dont on veut récupérer l'attribut
     * @return la valeur de lastName ou null si l'attribut n'avait pas été ajouté
     */
    public static String getLastName(HttpSession session) {
        if (session == null)
            throw new NullSessionException();
        return (String)session.getAttribute("lastName");
    }
    /**
     * Teste si l'utilisateur a une session d'ouverte
     * @param session le session a analysé
     * @return true si l'utilisateur a une session sur le site, false sinon
     */
    public static boolean hasSession(HttpSession session) {
        return (session != null) && (session.getAttribute("firstName") != null);
    }
}
