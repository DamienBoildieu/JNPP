/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.controller.views.info;

import java.util.List;
import jnpp.controller.views.alerts.AlertMessage;

/**
 *
 * @author Damien
 */
public class CompanyInfo extends ConnectedInfo {
/**
     * Le nom de l'entreprise
     */
    private final String companyName;
    /**
     * Constructeur
     * @param cName le com de la compagnie
     */
    public CompanyInfo(String cName) {
	super();
	companyName = cName;
    }
    
    public CompanyInfo(String cName, boolean hasNotif) {
	super(hasNotif);
	companyName = cName;
    }
    /**
     * Constructeur avec une alerte
     * @param cName le com de la compagnie

     * @param alert une alerte
     */
    public CompanyInfo(String cName, AlertMessage alert) {
	super(alert);
	companyName = cName;
    }
    /**
     * Constructeur avec une liste d'alertes
     * @param cName le com de la compagnie
     * @param alerts les alertes
     */
    public CompanyInfo(String cName, List<AlertMessage> alerts) {
	super(alerts);
	companyName = cName;
    }
    /**
     * Constructeur avec indicateur de notification
     * @param cName le com de la compagnie
     * @param alerts les alertes
     * @param notif indique si il y a de nouvelles notifications
     */
    public CompanyInfo(String cName, List<AlertMessage> alerts, boolean notif) {
        super(alerts, notif);
        companyName = cName;
    }
    /**
     * Accesseur sur le nom de l'entreprise
     * @return le nom
     */
    public String getCompanyName() {
	return companyName;
    } 
}
