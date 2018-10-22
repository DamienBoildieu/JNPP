/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.common;

import jnpp.exception.NullSessionException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author damien
 */
public class CSession {  
    private CSession() {}
    
    public static void clearSession(HttpSession session) {
        session.removeAttribute("firstName");
        session.removeAttribute("lastName");
    }
    
    public static void setFirstName(HttpSession session, String firstName) {
        if (session == null)
            throw new NullSessionException();
        session.setAttribute("firstName", firstName);
    }
    
    public static void setLastName(HttpSession session, String lastName) {
        if (session == null)
            throw new NullSessionException();
        session.setAttribute("lastName", lastName);
    }
    
    public static String getFirstName(HttpSession session) {
        if (session == null)
            throw new NullSessionException();
        return (String)session.getAttribute("firstName");
    }
    
    public static String getLastName(HttpSession session) {
        if (session == null)
            throw new NullSessionException();
        return (String)session.getAttribute("lastName");
    }
    
    public static boolean hasSession(HttpSession session) {
        return (session != null) && (session.getAttribute("firstName") != null);
    }
}
