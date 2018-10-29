package jnpp.dao.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jnpp.dao.entities.clients.Client;
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
    public Client find(String login, String password) {
        Query q = em.createNamedQuery("find_client_by_login_password", 
                Client.class);
        q.setParameter("login", login);
        q.setParameter("password", password);
        List<Client> l = q.getResultList();
        return l.isEmpty() ? null : l.get(0);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<String> findAllLogin() {
        Query q = em.createNamedQuery("find_all_login");
        return q.getResultList();
    }
    
    @Transactional(readOnly = true)
    @Override
    public String findLogin(Long clientId) {
        Query q = em.createNamedQuery("find_login_by_client_fk", String.class);
        q.setParameter("client_id", clientId);
        List<String> l = q.getResultList();
        return l.isEmpty() ? null : l.get(0);
    }
    
    @Transactional(readOnly = true)
    @Override
    public Identifier find(Long clientId) {
        Query q = em.createNamedQuery("find_by_client_fk", Identifier.class);
        q.setParameter("client_id", clientId);
        List<Identifier> l = q.getResultList();
        return l.isEmpty() ? null : l.get(0);
    }

    @Transactional(readOnly = true)
    @Override
    public Identifier findPrivate(String login, String firstname, 
            String lastname, String email) {
        Query q = em.createNamedQuery("find_by_login_identity_email", 
                Identifier.class);
        q.setParameter("login", login);
        q.setParameter("firstname", firstname);
        q.setParameter("lastname", lastname);
        q.setParameter("email", email);
        List<Identifier> l = q.getResultList();
        return l.isEmpty() ? null : l.get(0);
    }

    @Transactional(readOnly = true)
    @Override
    public Identifier findProfessional(String login, String name, 
            String ownerFirstname, String ownerLastname, String email) {
        Query q = em.createNamedQuery("find_by_login_name_owner_email", 
                Identifier.class);
        q.setParameter("login", login);
        q.setParameter("name", name);
        q.setParameter("firstname", ownerFirstname);
        q.setParameter("lastname", ownerLastname);
        q.setParameter("email", email);
        List<Identifier> l = q.getResultList();
        return l.isEmpty() ? null : l.get(0);
    }    
    
}
