package jnpp.service.services;

import java.util.Date;
import java.util.List;

import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.notifications.NotificationEntity;
import jnpp.service.dto.notifications.NotificationDTO;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeNotificationException;
import jnpp.service.exceptions.owners.NotificationOwnerException;

public interface NotificationService extends IService {

    List<NotificationDTO> receiveNotifications(String login) throws FakeClientException;
    
    /** Retourne les notifications non lues d'un client.
     * @param client ClientEntity concerne.
     * @return List de notifications non notifiees.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<NotificationEntity> receiveUnseenNotifications(ClientEntity client)
            throws FakeClientException;
    
    /** Retourne les n dernieres notifications non lues d'un client.
     * @param client ClientEntity concerne.
     * @param n Nombre de notifications.
     * @return List de notifications non notifiees.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<NotificationEntity> receiveUnseenNotifications(ClientEntity client, int n)
            throws FakeClientException;
    
    /** Retourne dernieres notifications non lues d'un client poseterieures a 
     * une date.
     * @param client ClientEntity concerne.
     * @param date Date.
     * @return List de notifications non notifiees.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<NotificationEntity> receiveUnseenNotifications(ClientEntity client, Date date)
            throws FakeClientException;
    
    /** Marque toutes les notifications d'un client comme lues.
     * @param client ClientEntity concerne.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    void seeAllNotications(ClientEntity client)
            throws FakeClientException;
    
    /** Marque une notification d'un client comme lue.
     * @param client ClientEntity prorpietaire de la notification.
     * @param notification NotificationEntity a marquer comme lue.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws FakeNotificationException Exception levee si l'entite 
     * notification ne fait pas reference a une notification existante.
     * @throws NotificationOwnerException Exception levee si le client n'est 
     * pas proprietaire de la notification. */
    void seeNotification(ClientEntity client, NotificationEntity notification)
            throws FakeClientException, FakeNotificationException,
            NotificationOwnerException;
    
    /** Marque des notifications d'un client comme lues.
     * @param client ClientEntity prorpietaire de des notifications.
     * @param notifications Notifications a marquer comme lues.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws FakeNotificationException Exception levee si l'entite 
     * notification ne fait pas reference a une notification existante.
     * @throws NotificationOwnerException Exception levee si le client n'est 
     * pas proprietaire d'une des notifications. */
    void seeNotifications(ClientEntity client, List<NotificationEntity> notifications)
            throws FakeClientException, FakeNotificationException,
            NotificationOwnerException;
    
}
