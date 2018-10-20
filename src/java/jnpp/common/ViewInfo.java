/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.common;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author damien
 */
public class ViewInfo {
    private final boolean connected;
    private final List<AlertMessage> alerts;
    
    public ViewInfo(boolean isConnected) {
        this.connected = isConnected;
        this.alerts = new ArrayList<AlertMessage>();
    }
    
    public ViewInfo(boolean isConnected, AlertMessage alert) {
	this.connected = isConnected;
        this.alerts = new ArrayList<AlertMessage>();
        this.alerts.add(alert);
    }

    public ViewInfo(boolean isConnected, List<AlertMessage> alerts) {
	this.connected = isConnected;
        if (alerts != null) {
            this.alerts = alerts;
        } else {
           this.alerts = new ArrayList<AlertMessage>();
        }
    }
    
    public boolean isConnected() {
	return this.connected;
    }

    public List<AlertMessage> getAlerts() {
        return alerts;
    }
}
