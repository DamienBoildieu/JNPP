package jnpp.controller.views.alerts;
/**
 * Classe de message d'alerte qui peuvent être affichées sur le site web
 */
public class AlertMessage {
    /**
     * Le type d'alerte
     */
    private AlertEnum alertType;
    /**
     * Le message à afficher
     */
    private String message;
    /**
     * Constructeur
     * @param alertType le type d'alerte
     * @param message le message d'alerte
     */
    public AlertMessage(AlertEnum alertType, String message) {
        this.alertType = alertType;
        this.message = message;
    }
    /**
     * Mutateur du type d'alerte
     * @param alertType le nouveau type d'alerte
     */
    public void setAlertType(AlertEnum alertType) {
        this.alertType = alertType;
    }
    /**
     * Mutateur du message
     * @param message le nouveau message
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * Accesseur sur le type d'alerte
     * @return le type d'alerte
     */
    public AlertEnum getAlertType() {
        return alertType;
    }
    /**
     * Accesseur sur le message de l'alerte
     * @return le message
     */
    public String getMessage() {
        return message;
    }
}
