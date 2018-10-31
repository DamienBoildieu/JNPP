package jnpp.service;

import java.util.Date;
import java.util.List;

import jnpp.dao.entities.clients.Client;
import jnpp.dao.entities.notifications.Notification;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeNotificationException;
import jnpp.service.exceptions.owners.NotificationOwnerException;

/** Service de gestion des notifications.
 * @author Pierre Bourquat
 * @author Damien Boildieu */
public interface INotificationService extends IService {

    /** Retourne toutes les notifications d'un client. 
     * @param client Client concerne.
     * @return List des notifications du client.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<Notification> receiveNotifications(Client client)
            throws FakeClientException;
    
    /** Retourne les notifications non lues d'un client.
     * @param client Client concerne.
     * @return List de notifications non notifiees.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<Notification> receiveUnseenNotifications(Client client)
            throws FakeClientException;
    
    /** Retourne les n dernieres notifications non lues d'un client.
     * @param client Client concerne.
     * @param n Nombre de notifications.
     * @return List de notifications non notifiees.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<Notification> receiveUnseenNotifications(Client client, int n)
            throws FakeClientException;
    
    /** Retourne dernieres notifications non lues d'un client poseterieures a 
     * une date.
     * @param client Client concerne.
     * @param date Date.
     * @return List de notifications non notifiees.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<Notification> receiveUnseenNotifications(Client client, Date date)
            throws FakeClientException;
    
    /** Marque toutes les notifications d'un client comme lues.
     * @param client Client concerne.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    void seeAllNotications(Client client)
            throws FakeClientException;
    
    /** Marque une notification d'un client comme lue.
     * @param client Client prorpietaire de la notification.
     * @param notification Notification a marquer comme lue.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws FakeNotificationException Exception levee si l'entite 
     * notification ne fait pas reference a une notification existante.
     * @throws NotificationOwnerException Exception levee si le client n'est 
     * pas proprietaire de la notification. */
    void seeNotification(Client client, Notification notification)
            throws FakeClientException, FakeNotificationException,
            NotificationOwnerException;
    
    /** Marque des notifications d'un client comme lues.
     * @param client Client prorpietaire de des notifications.
     * @param notifications Notifications a marquer comme lues.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws FakeNotificationException Exception levee si l'entite 
     * notification ne fait pas reference a une notification existante.
     * @throws NotificationOwnerException Exception levee si le client n'est 
     * pas proprietaire d'une des notifications. */
    void seeNotifications(Client client, List<Notification> notifications)
            throws FakeClientException, FakeNotificationException,
            NotificationOwnerException;
    
}
