/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.common;

import exception.NullSessionException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author damien
 */
public class CSession {  
    private HttpSession session = null;
    private CSession() {}
    private static class CSessionHolder {
        private final static CSession INSTANCE = new CSession();
    }
 
    public static CSession getInstance() {
        return CSessionHolder.INSTANCE;
    }
    
    public void setSession(HttpSession session) {
        this.session = session;
    }
    
    public void clearSession() {
        this.session.removeAttribute("firstName");
        this.session.removeAttribute("lastName");
    }
    
    public void setFirstName(String firstName) {
        if (this.session == null)
            throw new NullSessionException();
        this.session.setAttribute("firstName", firstName);
    }
    
    public void setLastName(String lastName) {
        if (this.session == null)
            throw new NullSessionException();
        this.session.setAttribute("lastName", lastName);
    }
    
    public String getFirstName() {
        if (this.session == null)
            throw new NullSessionException();
        return (String)this.session.getAttribute("firstName");
    }
    
    public String getLastName() {
        if (this.session == null)
            throw new NullSessionException();
        return (String)this.session.getAttribute("lastName");
    }
    
    public boolean hasSession() {
        return (this.session != null) && (this.session.getAttribute("firstName") != null);
    }
}
