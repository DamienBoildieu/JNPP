package jnpp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DUser implements IDUser {
    
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
    public void save(EUser user) {
        user = em.merge(user);
        em.persist(user);
    }

    @Transactional
    @Override
    public void update(EUser user) {
        em.merge(user);
    }

    @Transactional
    @Override
    public void delete(EUser user) {
        user = em.merge(user);
        em.remove(user);
    }

    @Transactional(readOnly = true)
    @Override
    public EUser find(long id) {
        return em.find(EUser.class, id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EUser> findAll() {
        Query q = em.createQuery("SELECT h FROM EUser h");
        return q.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public EUser findByLogin(String login) {
        Query q = em.createQuery(
            "SELECT h FROM EUser h WHERE h.login = :ll");
        q.setParameter("ll", login);
        List<EUser> l = q.getResultList();
        return l.isEmpty() ? null : l.get(0);
    }
    
}
