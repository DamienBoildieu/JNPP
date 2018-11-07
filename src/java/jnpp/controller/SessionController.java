package jnpp.controller;

import javax.servlet.http.HttpSession;
import jnpp.controller.exceptions.NullSessionException;
import jnpp.controller.exceptions.UnconnectedException;
import jnpp.controller.views.Translator;
import jnpp.service.dto.clients.ClientDTO;

/**
 * Classe permettant de gérer les HttpSession
 */
public class SessionController {
    /**
     * Constructeur private
     */
    private SessionController() {}
    /**
     * Supprime les attributs ajoutés par l'application
     * @param session la session que l'on veut supprimer
     */
    public static void clearSession(HttpSession session) throws UnconnectedException {
        if (SessionController.isConnected(session)) {
            session.removeAttribute("client");
            session.removeAttribute("hasNotif");
        } else {
            throw new UnconnectedException();
        }
    }
    
    public static void deleteSession(HttpSession session) throws NullSessionException {
        if (session==null)
            throw new NullSessionException();
        session.invalidate();
    }
    
    public static void setClient(HttpSession session, ClientDTO client) throws NullSessionException {
        if (session == null)
            throw new NullSessionException();
        session.setAttribute("client", client);
    }
    
    public static void setLanguage(HttpSession session, Translator.Language lang) throws NullSessionException {
        if (session == null)
            throw new NullSessionException();
        session.setAttribute("language", lang);
    }
    
    public static void setHasNotif(HttpSession session, boolean hasNotif) throws NullSessionException {
        if (session == null)
            throw new NullSessionException();
        session.setAttribute("hasNotif", hasNotif);
    }
    
    public static ClientDTO getClient(HttpSession session) throws NullSessionException {
        if (session == null)
            throw new NullSessionException();
        return (ClientDTO)session.getAttribute("client");
    }
    
    public static Translator.Language getLanguage(HttpSession session) throws NullSessionException {
        if (session == null)
            throw new NullSessionException();
        return (Translator.Language)session.getAttribute("language");
    }
    
    public static Boolean getHasNotif(HttpSession session) throws NullSessionException {
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
        return (session != null) && (session.getAttribute("client") != null) && (session.getAttribute("hasNotif") != null);
    }
}