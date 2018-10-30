package jnpp.controller;

import jnpp.controller.exceptions.NullSessionException;
import javax.servlet.http.HttpSession;
import jnpp.controller.exceptions.UnconnectedException;
import jnpp.controller.views.Translator;
import jnpp.dao.entities.clients.Client;

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
    public static void clearSession(HttpSession session) throws UnconnectedException {
        if (CSession.isConnected(session)) {
            session.removeAttribute("userName");
            session.removeAttribute("hasNotif");
        } else {
            throw new UnconnectedException();
        }
    }
    
    public static void setUserName(HttpSession session, String userName) {
        if (session == null)
            throw new NullSessionException();
        session.setAttribute("userName", userName);
    }
    
    public static void setLanguage(HttpSession session, Translator.Language lang) {
        if (session == null)
            throw new NullSessionException();
        session.setAttribute("language", lang);
    }
    
    public static void setHasNotif(HttpSession session, boolean hasNotif) {
        if (session == null)
            throw new NullSessionException();
        session.setAttribute("hasNotif", hasNotif);
    }
    
    public static String getUserName(HttpSession session) {
        if (session == null)
            throw new NullSessionException();
        return (String)session.getAttribute("userName");
    }
    
    public static Translator.Language getLanguage(HttpSession session) {
        if (session == null)
            throw new NullSessionException();
        return (Translator.Language)session.getAttribute("language");
    }
    
    public static Boolean getHasNotif(HttpSession session) {
        if (session == null)
            throw new NullSessionException();
        return (Boolean)session.getAttribute("hasNotif");
    }
    /**
     * Teste si l'utilisateur a une session d'ouverte
     * @param session le session a analysé
     * @return true si l'utilisateur a une session sur le site, false sinon
     */
    public static boolean isConnected(HttpSession session) {
        return (session != null) && (session.getAttribute("userName") != null) && (session.getAttribute("hasNotif") != null);
    }
}
