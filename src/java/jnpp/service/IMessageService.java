package jnpp.service;

import java.util.Date;
import java.util.List;

import jnpp.dao.entities.Message;
import jnpp.dao.entities.clients.Advisor;
import jnpp.dao.entities.clients.Client;
import jnpp.service.exceptions.entities.UnknownAdvisorException;
import jnpp.service.exceptions.entities.UnknownClientException;

public interface IMessageService {
    
    /** Envoie un message d'un client a un conseiller.
     * @param client Client envoyant le message.
     * @param advisor Conseiller recevant le message.
     * @param message Contenu du message.
     * @return L'entite representant le message.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. 
     * @throws UnknownAdvisorException Exception levee si l'entite advisor ne 
     * fait pas reference a un conseillier existant. */
    public Message sendMessage(Client client, Advisor advisor, String message)
            throws UnknownClientException, UnknownAdvisorException;
    
    /** Envoie un message d'un conseiller a un client.
     * @param advisor Conseiller envoyant le message.
     * @param client Client recevant le message.
     * @param message Contenu du message.
     * @return L'entite representant le message.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. 
     * @throws UnknownAdvisorException Exception levee si l'entite advisor ne 
     * fait pas reference a un conseillier existant. */
    public Message sendMessage(Advisor advisor, Client client, String message)
            throws UnknownClientException, UnknownAdvisorException;
    
    /** Retourne une liste des messages recus et envoyes d'un client.
     * @param client Client concerne.
     * @return Liste des messages du client.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public List<Message> getMessages(Client client)
            throws UnknownClientException;
    
    /** Retourne une liste des n dernier messages recus et envoyes d'un client.
     * @param client Client concerne.
     * @param n Nombre de message.
     * @return Liste des n dernier message.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public List<Message> getLastMessages(Client client, int n)
            throws UnknownClientException;
    
    /** Retourne une liste des messages recus et envoyes d'un client 
     * posterieurs a une date.  
     * @param client Client concerne.
     * @param date Date.
     * @return Liste des messages recus et envoyes apres la date specifiee.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public List<Message> getLastMessages(Client client, Date date)
            throws UnknownClientException;
    
    /** Retourne une liste des messages recus et envoyes d'un Conseiller.
     * @param advisor Conseiller concerne.
     * @return Liste des messages du client.
     * @throws UnknownAdvisorException Exception levee si l'entite advisor ne 
     * fait pas reference a un conseiller existant. */
    public List<Message> getMessages(Advisor advisor)
            throws UnknownAdvisorException;
        
    /** Retourne une liste des n dernier messages recus et envoyes d'un client.
     * @param advisor Conseiller concerne.
     * @param n Nombre de message.
     * @return Liste des n dernier message.
     * @throws UnknownAdvisorException Exception levee si l'entite advisor ne 
     * fait pas reference a un conseiller existant. */
    public List<Message> getLastMessages(Advisor advisor, int n)
            throws UnknownAdvisorException;
        
    /** Retourne une liste des messages recus et envoyes d'un client 
     * posterieurs a une date.  
     * @param advisor Conseiller concerne.
     * @param date Date.
     * @return Liste des messages recus et envoyes apres la date specifiee.
     * @throws UnknownAdvisorException Exception levee si l'entite advisor ne 
     * fait pas reference a un conseiller existant. */
    public List<Message> getLastMessages(Advisor advisor, Date date)
            throws UnknownAdvisorException;
    
}
