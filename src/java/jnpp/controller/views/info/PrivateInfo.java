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
public class PrivateInfo extends ConnectedInfo {
    /**
     * Le prénom de l'utilisateur connecté
     */
    private final String firstName;
    /**
     * Le nom de famille de l'utilisateur connecté
     */
    private final String lastName;
    /**
     * Constructeur
     * @param fName le prénom
     * @param lName le nom de famille
     */
    public PrivateInfo(String fName, String lName) {
	super();
	firstName = fName;
	lastName = lName;
    }
    
    public PrivateInfo(String fName, String lName, boolean hasNotif) {
	super(hasNotif);
	firstName = fName;
	lastName = lName;
    }
    /**
     * Constructeur avec une alerte
     * @param fName le prénom
     * @param lName le nom de famille
     * @param alert une alerte
     */
    public PrivateInfo(String fName, String lName, AlertMessage alert) {
	super(alert);
	firstName = fName;
	lastName = lName;
    }
    /**
     * Constructeur avec une liste d'alertes
     * @param fName le prénom
     * @param lName le nom de famille
     * @param alerts les alertes
     */
    public PrivateInfo(String fName, String lName, List<AlertMessage> alerts) {
	super(alerts);
	firstName = fName;
	lastName = lName;
    }
    /**
     * Constructeur avec indicateur de notification
     * @param fName le prénom
     * @param lName le nom de famille
     * @param alerts les alertes
     * @param notif indique si il y a de nouvelles notifications
     */
    public PrivateInfo(String fName, String lName, List<AlertMessage> alerts, boolean notif) {
        super(alerts, notif);
        firstName = fName;
        lastName = lName;
    }
    /**
     * Accesseur sur le prénom
     * @return le prénom
     */
    public String getFirstName() {
	return firstName;
    }
    /**
     * Accesseur sur le nom de famille
     * @return le nom de famille
     */
    public String getLastName() {
	return lastName;
    }
}
