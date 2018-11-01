package jnpp.dao.repositories;

import javax.persistence.Query;

import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.clients.Gender;
import jnpp.dao.entities.clients.Identity;
import jnpp.dao.entities.clients.PrivateEntity;
import jnpp.dao.entities.clients.ProfessionalEntity;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ClientDAOImpl extends GenericDAO<ClientEntity> implements ClientDAO {
 
    @Transactional(readOnly = true)
    @Override
    public PrivateEntity findPrivate(Identity identity) {
        Query query = getEm().createNamedQuery("find_private_by_identity", Long.class);
        query.setParameter("gender", identity.getGender());
        query.setParameter("firstname", identity.getFirstname());
        query.setParameter("lastname", identity.getLastname());
        return (PrivateEntity) query.getSingleResult();
    }
    
    
    
}
