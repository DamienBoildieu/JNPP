package jnpp.service;

import java.util.Date;
import java.util.List;

import jnpp.dao.entities.clients.Client;
import jnpp.dao.entities.notifications.Notification;
import jnpp.service.exceptions.entities.FakeClientException;

/** Service de gestion des notifications.
 * @author Pierre Bourquat
 * @author Damien Boildieu */
public interface INotificationService {

    /** Retourne toutes les notifications d'un client. 
     * @param client Client concerne.
     * @return List des notifications du client.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public List<Notification> receiveNotifications(Client client)
            throws FakeClientException;
    
    /** Retourne les notifications non recus d'un client.
     * @param client Client concerne.
     * @return List de notifications non notifiees.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public List<Notification> receiveNewNotifications(Client client)
            throws FakeClientException;
    
    /** Retourne les n dernieres notifications non recues d'un client.
     * @param client Client concerne.
     * @param n Nombre de notifications.
     * @return List de notifications non notifiees.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public List<Notification> receiveNewNotifications(Client client, int n)
            throws FakeClientException;
    
    /** Retourne dernieres notifications non recues d'un client poseterieures a 
     * une date.
     * @param client Client concerne.
     * @param date Date.
     * @return List de notifications non notifiees.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public List<Notification> receiveNewNotifications(Client client, Date date)
            throws FakeClientException;
    
    /** Marque toutes les notifications d'un client comme recues.
     * @param client Client concerne.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public void discardAllNotications(Client client)
            throws FakeClientException;
    
}