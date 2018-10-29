package jnpp.dao.repositories;

import java.util.List;

import javax.persistence.EntityManager;
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
    public Client find(String login, String password) {
        Query q = em.createQuery(""
                + "SELECT c "
                + "FROM Client c "
                + "WHERE c.login = ?1 "
                + "  AND c.password = ?2");
        q.setParameter(1, login);
        q.setParameter(2, password);
        List<Client> l = q.getResultList();
        return l.isEmpty() ? null : l.get(0);
    }
    
    @Transactional(readOnly = true)
    @Override
    public boolean privateExist(Gender gender, String firstname, String lastname) {
        Query q = em.createQuery(""
                + "SELECT COUNT(c) "
                + "FROM Client c " 
                + "WHERE c.type = ?1 "
                + "  AND c.gender = ?2 "
                + "  AND c.firstname = ?3 "
                + "  AND c.lastname = ?4");
        q.setParameter(1, Client.Type.Values.PRIVATE);
        q.setParameter(2, gender.name());
        q.setParameter(3, firstname);
        q.setParameter(4, lastname);
        Long count = (Long) q.getSingleResult();
        return count > 0;
    }
    
    @Transactional(readOnly = true)
    @Override
    public boolean professionalExist(String name) {
                Query q = em.createQuery(""
                + "SELECT COUNT(c) "
                + "FROM Client c " 
                + "WHERE c.type = ?1 "
                + "  AND c.gender = ?2");
        q.setParameter(1, Client.Type.Values.PROFESSIONAL);
        q.setParameter(2, name);
        Long count = (Long) q.getSingleResult();
        return count > 0;
    }
    
}
