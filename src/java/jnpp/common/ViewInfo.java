package jnpp.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe contenant les informations nécessaires à une vue de l'application
 */
public class ViewInfo {
    /**
     * Indique si l'utilisateur est connecté
     */
    private final boolean connected;
    /**
     * La liste des alertes à afficher
     */
    private final List<AlertMessage> alerts;
    /**
     * Constructeur
     * @param isConnected indique si l'utilisateur est connecté
     */
    public ViewInfo(boolean isConnected) {
        this.connected = isConnected;
        this.alerts = new ArrayList<AlertMessage>();
    }
    /**
     * Constructeur avec une alerte
     * @param isConnected indique si l'utilisateur est connecté
     * @param alert l'alerte
     */
    public ViewInfo(boolean isConnected, AlertMessage alert) {
	this.connected = isConnected;
        this.alerts = new ArrayList<AlertMessage>();
        this.alerts.add(alert);
    }
    /**
     * Constructeur avec une liste d'alertes
     * @param isConnected indique si l'utilisateur est connecté
     * @param alerts la liste d'alertes
     */
    public ViewInfo(boolean isConnected, List<AlertMessage> alerts) {
	this.connected = isConnected;
        if (alerts != null) {
            this.alerts = alerts;
        } else {
           this.alerts = new ArrayList<AlertMessage>();
        }
    }
    /**
     * Accesseur sur le booléen de connexion
     * @return true si l'utilisateur est connecté, false sinon
     */
    public boolean isConnected() {
	return this.connected;
    }
    /**
     * Accesseur sur la liste d'alertes
     * @return la liste d'alertes
     */
    public List<AlertMessage> getAlerts() {
        return alerts;
    }
}
