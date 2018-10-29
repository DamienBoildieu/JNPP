package jnpp.dao.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jnpp.dao.entities.clients.Identifier;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class IdentifierDAO implements IIdentifierDAO {
        
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
    public Identifier save(Identifier identifier) {
        identifier = em.merge(identifier);
        em.persist(identifier);
        em.flush();
        return identifier;
    }
    
    @Transactional
    @Override
    public Identifier update(Identifier identifier) {
        em.merge(identifier);
        return identifier;
    }
    
    @Transactional
    @Override
    public void delete(Identifier identifier) {
        em.merge(identifier);
        em.remove(identifier);
    }
    
    @Transactional(readOnly = true)
    @Override
    public Identifier find(String login) {
        return em.find(Identifier.class, login);
    }

    @Override
    public Identifier find(String login, String password) {
        Query q = em.createNamedQuery("find_identifier");
        q.setParameter("login", login);
        q.setParameter("password", password);
        List<Identifier> l = q.getResultList();
        return l.isEmpty() ? null : l.get(0);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<String> findAllLogin() {
        Query q = em.createNamedQuery("find_all_login");
        return q.getResultList();
    }
    
}
