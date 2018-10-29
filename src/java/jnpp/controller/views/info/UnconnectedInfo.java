package jnpp.controller.views.info;

import java.util.List;
import jnpp.controller.views.alerts.AlertMessage;

/**
 * Classe contenant les informations dont a besoin la vue si un utilisateur n'est pas connecté
 */
public class UnconnectedInfo extends ViewInfo {
    /**
     * Constructeur
     */
    public UnconnectedInfo() {
	super(false);
    }
    /**
     * Constructeur avec une alerte
     * @param alert l'alerte
     */
    public UnconnectedInfo(AlertMessage alert) {
	super(false, alert);
    }
    /**
     * Constructeur avec une liste d'alertes
     * @param alerts les alertes
     */
    public UnconnectedInfo(List<AlertMessage> alerts) {
        super(false, alerts);
    }
}
