package jnpp.dao.repositories;

import jnpp.dao.entities.clients.Client;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jnpp.dao.interfaces.IClientDAO;

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
    public void save(Client client) {
        client = em.merge(client);
        em.persist(client);
    }

    @Transactional
    @Override
    public void update(Client client) {
        em.merge(client);
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
    
}
