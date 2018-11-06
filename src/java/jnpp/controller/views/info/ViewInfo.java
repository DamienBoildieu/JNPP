package jnpp.controller.views.info;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import jnpp.controller.SessionController;
import jnpp.controller.views.alerts.AlertMessage;
import jnpp.service.dto.clients.ClientDTO;
import jnpp.service.dto.clients.PrivateDTO;
import jnpp.service.dto.clients.ProfessionalDTO;

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
    protected ViewInfo(boolean isConnected) {
        this.connected = isConnected;
        this.alerts = new ArrayList<AlertMessage>();
    }
    /**
     * Constructeur avec une alerte
     * @param isConnected indique si l'utilisateur est connecté
     * @param alert l'alerte
     */
    protected ViewInfo(boolean isConnected, AlertMessage alert) {
	this.connected = isConnected;
        this.alerts = new ArrayList<AlertMessage>();
        this.alerts.add(alert);
    }
    /**
     * Constructeur avec une liste d'alertes
     * @param isConnected indique si l'utilisateur est connecté
     * @param alerts la liste d'alertes
     */
    protected ViewInfo(boolean isConnected, List<AlertMessage> alerts) {
	this.connected = isConnected;
        if (alerts != null) {
            this.alerts = alerts;
        } else {
           this.alerts = new ArrayList<AlertMessage>();
        }
    }
    
    public static ViewInfo createInfo(HttpSession session) {
        if (SessionController.isConnected(session)) {
            ClientDTO client = SessionController.getClient(session);
            String userName = "";
            switch (client.getType()) {
                case PRIVATE:
                    PrivateDTO priv = (PrivateDTO)client;
                    userName = priv.getIdentity().getFirstname() + " " + priv.getIdentity().getLastname();
                    break;
                case PROFESIONAL:
                    ProfessionalDTO pro = (ProfessionalDTO)client;
                    userName = pro.getName();
                    break;
                default:
                    throw new AssertionError(client.getType().name());      
            }
            return new ConnectedInfo(userName, SessionController.getHasNotif(session));  
        }
        return new UnconnectedInfo();
    }
    
    public static ViewInfo createInfo(HttpSession session, AlertMessage alert) {
        if (SessionController.isConnected(session)) {
            ClientDTO client = SessionController.getClient(session);
            String userName = "";
            switch (client.getType()) {
                case PRIVATE:
                    PrivateDTO priv = (PrivateDTO)client;
                    userName = priv.getIdentity().getFirstname() + " " + priv.getIdentity().getLastname();
                    break;
                case PROFESIONAL:
                    ProfessionalDTO pro = (ProfessionalDTO)client;
                    userName = pro.getName();
                    break;
                default:
                    throw new AssertionError(client.getType().name());      
            }
            return new ConnectedInfo(userName, alert, SessionController.getHasNotif(session));
        }
        return new UnconnectedInfo(alert);
    }
    
    public static ViewInfo createInfo(HttpSession session, List<AlertMessage> alerts) {
        if (SessionController.isConnected(session)) {
            ClientDTO client = SessionController.getClient(session);
            String userName = "";
            switch (client.getType()) {
                case PRIVATE:
                    PrivateDTO priv = (PrivateDTO)client;
                    userName = priv.getIdentity().getFirstname() + " " + priv.getIdentity().getLastname();
                    break;
                case PROFESIONAL:
                    ProfessionalDTO pro = (ProfessionalDTO)client;
                    userName = pro.getName();
                    break;
                default:
                    throw new AssertionError(client.getType().name());      
            }
            return new ConnectedInfo(userName, alerts, SessionController.getHasNotif(session));
        }
        return new UnconnectedInfo(alerts);
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
