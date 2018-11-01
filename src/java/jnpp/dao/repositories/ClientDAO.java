package jnpp.dao.repositories;

import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.clients.Identity;
import jnpp.dao.entities.clients.PrivateEntity;

public interface ClientDAO extends GenericDAO<ClientEntity> {
    
    PrivateEntity findPrivate(Identity identity);
    
}
