package jnpp.dao.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jnpp.dao.entities.clients.Client;
import jnpp.dao.entities.clients.Gender;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ClientDAO implements IClientDAO {
    
    @PersistenceContext(unitName="JNPPPU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }
    
    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    @Transactional
    @Override
    public Client save(Client client) {
        client = em.merge(client);
        em.persist(client);
        em.flush();
        return client;
    }
    
    @Transactional
    @Override
    public Client update(Client client) {
        em.merge(client);
        return client;
    }
    
    @Transactional
    @Override
    public void delete(Client client) {
        em.merge(client);
        em.remove(client);
    }
    
    @Transactional(readOnly = true)
    @Override
    public Client find(long id) {
        return em.find(Client.class, id);
    }
    
    @Transactional(readOnly = true)
    @Override
    public boolean privateExist(Gender gender, String firstname, 
            String lastname) {
        Query q = em.createNamedQuery("find_private_by_identity", Long.class);
        q.setParameter("gender", gender);
        q.setParameter("firstname", firstname);
        q.setParameter("lastname", lastname);
        Long count = (Long) q.getSingleResult();
        return count > 0;
    }
    
    @Transactional(readOnly = true)
    @Override
    public boolean professionalExist(String name) {
        Query q = em.createNamedQuery("find_professional_by_name", Long.class);
        q.setParameter("name", name);
        Long count = (Long) q.getSingleResult();
        return count > 0;
    }
    
}
