package jnpp.common;

import java.util.List;

/**
 * Classe contenant les informations dont a besoin la vue si un utilisateur est connecté
 */
public class ConnectedInfo extends ViewInfo {
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
    public ConnectedInfo(String fName, String lName) {
	super(true);
	firstName = fName;
	lastName = lName;
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
