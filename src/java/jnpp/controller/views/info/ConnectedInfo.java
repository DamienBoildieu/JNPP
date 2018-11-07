package jnpp.controller.views.info;

import java.util.List;
import jnpp.controller.views.alerts.AlertMessage;

/**
 * Classe contenant les informations dont a besoin la vue si un utilisateur est
 * connecté
 */
public class ConnectedInfo extends ViewInfo {

    /**
     * Indique si l'utilisateur a de nouvelles notifications
     */
    private final boolean hasNotif;
    /**
     * Le nom de l'utilisateur connecté
     */
    private final String userName;

    /**
     * Constructeur
     *
     * @param name le nom de l'utilisateur
     */
    public ConnectedInfo(String name) {
        super(true);
        hasNotif = false;
        userName = name;
    }

    /**
     * Constructeur
     *
     * @param name le nom de l'utilisateur
     * @param notif indicateur de notification
     */
    public ConnectedInfo(String name, boolean notif) {
        super(true);
        hasNotif = notif;
        userName = name;
    }

    /**
     * Constructeur avec une alerte
     *
     * @param alert une alerte
     */
    public ConnectedInfo(String name, AlertMessage alert) {
        super(true, alert);
        hasNotif = false;
        userName = name;
    }

    /**
     * Constructeur avec une liste d'alertes
     *
     * @param alerts les alertes
     */
    public ConnectedInfo(String name, List<AlertMessage> alerts) {
        super(true, alerts);
        hasNotif = false;
        userName = name;
    }

    /**
     * Constructeur avec indicateur de notification
     *
     * @param alerts les alertes
     * @param notif indique si il y a de nouvelles notifications
     */
    public ConnectedInfo(String name, List<AlertMessage> alerts, boolean notif) {
        super(true, alerts);
        hasNotif = notif;
        userName = name;
    }

    /**
     * Constructeur avec indicateur de notification
     *
     * @param alerts les alertes
     * @param notif indique si il y a de nouvelles notifications
     */
    public ConnectedInfo(String name, AlertMessage alert, boolean notif) {
        super(true, alert);
        hasNotif = notif;
        userName = name;
    }

    public boolean isHasNotif() {
        return hasNotif;
    }

    public String getUserName() {
        return userName;
    }
}
