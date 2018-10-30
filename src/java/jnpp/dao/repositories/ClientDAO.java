package jnpp.dao.repositories;

import javax.persistence.Query;

import jnpp.dao.entities.clients.Client;
import jnpp.dao.entities.clients.Gender;
import jnpp.dao.entities.clients.Identity;
import jnpp.dao.entities.clients.Private;
import jnpp.dao.entities.clients.Professional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ClientDAO extends GenericDAO<Client> implements IClientDAO {
    
    @Transactional(readOnly = true)
    @Override
    public boolean privateExist(Gender gender, String firstname, 
            String lastname) {
        Query q = getEm().createNamedQuery("find_by_identity", Long.class);
        q.setParameter("gender", gender);
        q.setParameter("firstname", firstname);
        q.setParameter("lastname", lastname);
        Long count = (Long) q.getSingleResult();
        return count > 0;
    }
    
    @Transactional(readOnly = true)
    @Override
    public boolean professionalExist(String name) {
        Query q = getEm().createNamedQuery("find_by_name", Long.class);
        q.setParameter("name", name);
        Long count = (Long) q.getSingleResult();
        return count > 0;
    }
    
    @Transactional(readOnly = true)
    private boolean isPrivateFake(Private client) {
        Query q = getEm().createNamedQuery("is_private_fake", Long.class);
        q.setParameter("id", client.getId());
        Identity identity = client.getIdentity();
        q.setParameter("gender", identity.getGender());
        q.setParameter("firstname", identity.getFirstname());
        q.setParameter("lastname", identity.getLastname());
        Long count = (Long) q.getSingleResult();
        return count != 0;
    }
    
    @Transactional(readOnly = true)
    private boolean isProfessionalFake(Professional client) {
        Query q = getEm().createNamedQuery("is_professional_fake", Long.class);
        q.setParameter("id", client.getId());
        Identity owner = client.getOwner();
        q.setParameter("name", client.getName());
        q.setParameter("gender", owner.getGender());
        q.setParameter("firstname", owner.getFirstname());
        q.setParameter("lastname", owner.getLastname());
        Long count = (Long) q.getSingleResult();
        return count != 0;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isFake(Client client) {
        switch (client.getType()) {
        case PRIVATE:
            return isPrivateFake((Private) client);
        case PROFESIONAL:
            return isProfessionalFake((Professional) client);
        }
        return true;
    }
    
}
