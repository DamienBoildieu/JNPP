package jnpp.service;

import jnpp.dao.entities.clients.Advisor;
import jnpp.dao.entities.clients.Client;
import jnpp.service.exceptions.entities.FakeClientException;

/** Service de gestion des conseillers.
 * @author Pierre Bourquat
 * @author Damien Boildieu */
public interface IAdvisorService extends IService {
   
    /** Retourne le conseiller d'un client.
     * @param client Client concerne.
     * @return Conseiller du client.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    Advisor getAdvisor(Client client)
            throws FakeClientException;
    
}
