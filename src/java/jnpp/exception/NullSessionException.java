/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.exception;

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
