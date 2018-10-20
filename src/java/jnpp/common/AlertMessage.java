/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.common;

/**
 *
 * @author Damien
 */
public class AlertMessage {
    private AlertEnum alertType;
    private String message;
    
    public AlertMessage(AlertEnum alertType, String message) {
        this.alertType = alertType;
        this.message = message;
    }

    public void setAlertType(AlertEnum alertType) {
        this.alertType = alertType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AlertEnum getAlertType() {
        return alertType;
    }

    public String getMessage() {
        return message;
    }
}
