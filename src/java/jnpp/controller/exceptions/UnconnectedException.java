/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.controller.exceptions;

/**
 *
 * @author Damien
 */
public class UnconnectedException extends Exception {
    
    private static final long serialVersionUID = -3717132425088581889L;
    
    public UnconnectedException() {
        super("Session is not filled");
    }    
}
