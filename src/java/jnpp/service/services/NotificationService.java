package jnpp.service.services;

import java.util.Date;
import java.util.List;
import jnpp.service.dto.notifications.NotificationDTO;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeNotificationException;
import jnpp.service.exceptions.owners.NotificationOwnerException;

public interface NotificationService {

    /**
     * Retourne les notifications d'un client.
     * @param login Login du client.
     * @return Liste de DTO de notifications.
     * @throws FakeClientException Exception levee le login ne fait pas reference 
     * a un client existant.
     */
    List<NotificationDTO> receiveNotifications(String login) throws FakeClientException;

    /**
     * Retourne les notifications non vues d'un client.
     * @param login Login du client.
     * @return Liste de DTO de notifications.
     * @throws FakeClientException Exception levee le login ne fait pas reference 
     * a un client existant.
     */
    List<NotificationDTO> receiveUnseenNotifications(String login)
            throws FakeClientException;

    /**
     * Retourne les notifications non vues d'un client.
     * @param login Login du client.
     * @param n Nombre de notification.
     * @return Liste de DTO de notifications.
     * @throws FakeClientException Exception levee le login ne fait pas reference 
     * a un client existant.
     */
    List<NotificationDTO> receiveUnseenNotifications(String login, int n)
            throws FakeClientException;

    /**
     * Retourne les notifications non vues d'un client posterieires a une date.
     * @param login Login du client.
     * @param date Date
     * @return Liste de DTO de notifications.
     * @throws FakeClientException Exception levee le login ne fait pas reference 
     * a un client existant.
     */
    List<NotificationDTO> receiveUnseenNotifications(String login, Date date)
            throws FakeClientException;

    /**
     * Marque toutes les notifications d'un client lues.
     * @param login Login du client.
     * @throws FakeClientException Exception levee le login ne fait pas reference 
     * a un client existant.
     */
    void seeAllNotications(String login)
            throws FakeClientException;

    /**
     * Marque une notification lue.
     * @param login Login du client.
     * @param id Identifiat de la notification.
     * @throws FakeClientException Exception levee le login ne fait pas reference 
     * a un client existant.
     * @throws FakeNotificationException Exception levee si l'id ne fait pas
     * reference a une notification existante.
     * @throws NotificationOwnerException Exception leve si le client n'est pas
     * proprietaire de la notification.
     */
    void seeNotification(String login, Long id)
            throws FakeClientException, FakeNotificationException,
            NotificationOwnerException;

    /**
     * Marque une liste de notifications d'un client.
     * @param login Login du client.
     * @param ids Liste d'identifiant de notifications.
     * @throws FakeClientException Exception levee le login ne fait pas reference 
     * a un client existant.
     * @throws FakeNotificationException Exception levee si l'id ne fait pas
     * reference a une notification existante.
     * @throws NotificationOwnerException 
     */
    void seeNotifications(String login, List<Long> ids)
            throws FakeClientException, FakeNotificationException,
            NotificationOwnerException;

}
