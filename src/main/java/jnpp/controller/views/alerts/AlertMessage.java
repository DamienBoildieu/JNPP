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
     *
     * @param alertType le type d'alerte
     * @param message   le message d'alerte
     */
    public AlertMessage(AlertEnum alertType, String message) {
        this.alertType = alertType;
        this.message = message;
    }

    public void setAlertType(AlertEnum alertType) {
        this.alertType = alertType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AlertEnum getAlertType() {
        return alertType;
    }

    public String getMessage() {
        return message;
    }
}
