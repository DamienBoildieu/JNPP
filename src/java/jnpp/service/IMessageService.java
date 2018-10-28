package jnpp.service;

import java.util.Date;
import java.util.List;

import jnpp.dao.entities.Message;
import jnpp.dao.entities.clients.Advisor;
import jnpp.dao.entities.clients.Client;
import jnpp.service.exceptions.entities.FakeAdvisorException;
import jnpp.service.exceptions.entities.FakeClientException;

/** Service de gestion de la messagerie permettant l'envoi et la reception de 
 * messages.
 * @author Pierre Bourquat
 * @author Damien Boildieu */
public interface IMessageService {
    
    /** Envoie un message d'un client a un conseiller.
     * @param client Client envoyant le message.
     * @param advisor Conseiller recevant le message.
     * @param message Contenu du message.
     * @return L'entite representant le message.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. 
     * @throws FakeAdvisorException Exception levee si l'entite advisor ne 
     * fait pas reference a un conseillier existant. */
    public Message sendMessage(Client client, Advisor advisor, String message)
            throws FakeClientException, FakeAdvisorException;
    
    /** Envoie un message d'un conseiller a un client.
     * @param advisor Conseiller envoyant le message.
     * @param client Client recevant le message.
     * @param message Contenu du message.
     * @return L'entite representant le message.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. 
     * @throws FakeAdvisorException Exception levee si l'entite advisor ne 
     * fait pas reference a un conseillier existant. */
    public Message sendMessage(Advisor advisor, Client client, String message)
            throws FakeClientException, FakeAdvisorException;
    
    /** Retourne une liste des messages recus et envoyes d'un client.
     * @param client Client concerne.
     * @return Liste des messages du client.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public List<Message> receiveMessages(Client client)
            throws FakeClientException;
    
    /** Retourne une liste des n dernier messages recus et envoyes d'un client.
     * @param client Client concerne.
     * @param n Nombre de message.
     * @return Liste des n dernier message.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public List<Message> receiveLastMessages(Client client, int n)
            throws FakeClientException;
    
    /** Retourne une liste des messages recus et envoyes d'un client 
     * posterieurs a une date.  
     * @param client Client concerne.
     * @param date Date.
     * @return Liste des messages recus et envoyes apres la date specifiee.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public List<Message> receiveLastMessages(Client client, Date date)
            throws FakeClientException;
    
    /** Retourne une liste des messages recus et envoyes d'un Conseiller.
     * @param advisor Conseiller concerne.
     * @return Liste des messages du client.
     * @throws FakeAdvisorException Exception levee si l'entite advisor ne 
     * fait pas reference a un conseiller existant. */
    public List<Message> receiveMessages(Advisor advisor)
            throws FakeAdvisorException;
        
    /** Retourne une liste des n dernier messages recus et envoyes d'un client.
     * @param advisor Conseiller concerne.
     * @param n Nombre de message.
     * @return Liste des n dernier message.
     * @throws FakeAdvisorException Exception levee si l'entite advisor ne 
     * fait pas reference a un conseiller existant. */
    public List<Message> receiveLastMessages(Advisor advisor, int n)
            throws FakeAdvisorException;
        
    /** Retourne une liste des messages recus et envoyes d'un client 
     * posterieurs a une date.  
     * @param advisor Conseiller concerne.
     * @param date Date.
     * @return Liste des messages recus et envoyes apres la date specifiee.
     * @throws FakeAdvisorException Exception levee si l'entite advisor ne 
     * fait pas reference a un conseiller existant. */
    public List<Message> receiveLastMessages(Advisor advisor, Date date)
            throws FakeAdvisorException;
    
}
