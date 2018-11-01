package jnpp.service.services;

import jnpp.dao.entities.AdvisorEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.service.exceptions.entities.FakeClientException;

/** Service de gestion des conseillers.
 * @author Pierre Bourquat
 * @author Damien Boildieu */
public interface IAdvisorService extends IService {
   
    /** Retourne le conseiller d'un client.
     * @param client ClientEntity concerne.
     * @return Conseiller du client.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    AdvisorEntity getAdvisor(ClientEntity client)
            throws FakeClientException;
    
}
