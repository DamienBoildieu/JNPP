package jnpp.controller.views.info;

import java.util.List;
import jnpp.controller.views.alerts.AlertMessage;

/**
 * Classe contenant les informations dont a besoin la vue si un utilisateur est connecté
 */
public class ConnectedInfo extends ViewInfo {
    /**
     * Indique si l'utilisateur a de nouvelles notifications
     */
    private final boolean hasNotif;
  
    protected ConnectedInfo() {
        super(true);
        hasNotif = false;
    }
    
    protected ConnectedInfo(boolean notif) {
        super(true);
        hasNotif = notif;
    }
    /**
     * Constructeur avec une alerte
     * @param alert une alerte
     */
    protected ConnectedInfo(AlertMessage alert) {
	super(true, alert);
        hasNotif = false;
    }
    /**
     * Constructeur avec une liste d'alertes
     * @param alerts les alertes
     */
    protected ConnectedInfo(List<AlertMessage> alerts) {
	super(true, alerts);
        hasNotif = false;
    }
    /**
     * Constructeur avec indicateur de notification
     * @param alerts les alertes
     * @param notif indique si il y a de nouvelles notifications
     */
    protected ConnectedInfo(List<AlertMessage> alerts, boolean notif) {
        super(true, alerts);
        hasNotif = notif;
    }
    /**
     * Accesseur sur l'indicateur de notification
     * @return true si il y a de nouvelles notifications, fales sinon
     */
    public boolean isHasNotif() {
        return hasNotif;
    }    
}
