package jnpp.common;

import java.util.List;

/**
 * Classe contenant les informations dont a besoin la vue si un utilisateur est connecté
 */
public class ConnectedInfo extends ViewInfo {
    /**
     * Le prénom de l'utilisateur connecté
     */
    private String firstName;
    /**
     * Le nom de famille de l'utilisateur connecté
     */
    private String lastName;
    /**
     * Indique si l'utilisateur a de nouvelles notifications
     */
    private boolean hasNotif;
    /**
     * Constructeur
     * @param fName le prénom
     * @param lName le nom de famille
     */
    public ConnectedInfo(String fName, String lName) {
	super(true);
	firstName = fName;
	lastName = lName;
        hasNotif = false;
    }
    /**
     * Constructeur avec une alerte
     * @param fName le prénom
     * @param lName le nom de famille
     * @param alert une alerte
     */
    public ConnectedInfo(String fName, String lName, AlertMessage alert) {
	super(true, alert);
	firstName = fName;
	lastName = lName;
        hasNotif = false;
    }
    /**
     * Constructeur avec une liste d'alertes
     * @param fName le prénom
     * @param lName le nom de famille
     * @param alerts les alertes
     */
    public ConnectedInfo(String fName, String lName, List<AlertMessage> alerts) {
	super(true, alerts);
	firstName = fName;
	lastName = lName;
        hasNotif = false;
    }
    /**
     * Constructeur avec indicateur de notification
     * @param fName le prénom
     * @param lName le nom de famille
     * @param alerts les alertes
     * @param notif indique si il y a de nouvelles notifications
     */
    public ConnectedInfo(String fName, String lName, List<AlertMessage> alerts, boolean notif) {
        super(true, alerts);
        firstName = fName;
        lastName = lName;
        hasNotif = notif;
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
    /**
     * Accesseur sur l'indicateur de notification
     * @return true si il y a de nouvelles notifications, fales sinon
     */
    public boolean isHasNotif() {
        return hasNotif;
    }
    /**
     * Mutateur sur le prénom
     * @param firstName le nouveau prénom
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * Mutateur sur le nom de famille
     * @param lastName le nouveau nom de famille
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * Mutateur sur les notifcations
     * @param hasNotif l'indicateur
     */
    public void setHasNotif(boolean hasNotif) {
        this.hasNotif = hasNotif;
    }
    
}
