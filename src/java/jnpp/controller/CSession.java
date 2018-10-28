package jnpp.controller;

import jnpp.controller.exceptions.NullSessionException;
import javax.servlet.http.HttpSession;
import jnpp.controller.views.Translator;

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
    
    public static void setLanguage(HttpSession session, Translator.Language lang) {
        if (session == null)
            throw new NullSessionException();
        session.setAttribute("language", lang);
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
    
    public static Translator.Language getLanguage(HttpSession session) {
        if (session == null)
            throw new NullSessionException();
        return (Translator.Language)session.getAttribute("language");
    }
    /**
     * Teste si l'utilisateur a une session d'ouverte
     * @param session le session a analysé
     * @return true si l'utilisateur a une session sur le site, false sinon
     */
    public static boolean isConnected(HttpSession session) {
        return (session != null) && (session.getAttribute("firstName") != null);
    }
}
