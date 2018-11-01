package jnpp.service.services;

import java.util.Date;
import java.util.List;

import jnpp.dao.entities.MessageEntity;
import jnpp.dao.entities.clients.AdvisorEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.service.exceptions.entities.FakeAdvisorException;
import jnpp.service.exceptions.entities.FakeClientException;

/** Service de gestion de la messagerie permettant l'envoi et la reception de 
 * messages.
 * @author Pierre Bourquat
 * @author Damien Boildieu */
public interface IMessageService extends IService {
    
    /** Envoie un message d'un client a un conseiller.
     * @param client ClientEntity envoyant le message.
     * @param advisor Conseiller recevant le message.
     * @param message Contenu du message.
     * @return L'entite representant le message.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. 
     * @throws FakeAdvisorException Exception levee si l'entite advisor ne 
     * fait pas reference a un conseillier existant. */
    MessageEntity sendMessage(ClientEntity client, AdvisorEntity advisor, String message)
            throws FakeClientException, FakeAdvisorException;
    
    /** Retourne une liste des messages recus et envoyes d'un client.
     * @param client ClientEntity concerne.
     * @return Liste des messages du client.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<MessageEntity> receiveMessages(ClientEntity client)
            throws FakeClientException;
    
    /** Retourne une liste des n dernier messages recus et envoyes d'un client.
     * @param client ClientEntity concerne.
     * @param n Nombre de message.
     * @return Liste des n dernier message.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<MessageEntity> receiveLastMessages(ClientEntity client, int n)
            throws FakeClientException;
    
    /** Retourne une liste des messages recus et envoyes d'un client 
     * posterieurs a une date.  
     * @param client ClientEntity concerne.
     * @param date Date.
     * @return Liste des messages recus et envoyes apres la date specifiee.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<MessageEntity> receiveLastMessages(ClientEntity client, Date date)
            throws FakeClientException;
    
}
