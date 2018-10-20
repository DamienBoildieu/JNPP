/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.common;

import java.util.List;

/**
 *
 * @author damien
 */
public class UnconnectedInfo extends ViewInfo {

    public UnconnectedInfo() {
	super(false);
    }
    
    public UnconnectedInfo(AlertMessage alert) {
	super(false, alert);
    }
    
    public UnconnectedInfo(List<AlertMessage> alerts) {
        super(false, alerts);
    }
}
