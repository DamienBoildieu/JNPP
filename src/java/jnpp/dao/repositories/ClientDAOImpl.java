package jnpp.dao.repositories;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import jnpp.dao.entities.IdentityEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.clients.PrivateEntity;
import jnpp.dao.entities.clients.ProfessionalEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ClientDAOImpl extends GenericDAOImpl<ClientEntity> implements ClientDAO {
 
    @Transactional(readOnly = true)
    @Override
    public PrivateEntity findPrivateByIdentity(IdentityEntity.Gender gender, String firstname, String lastname) {
        Query query = getEm().createNamedQuery("find_private_by_identity");
        query.setParameter("gender", gender);
        query.setParameter("firstname", firstname);
        query.setParameter("lastname", lastname);
        try {
            return (PrivateEntity) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
        
    @Transactional(readOnly = true)
    @Override
    public ProfessionalEntity findProfessionalByNameIdentity(String name, IdentityEntity.Gender ownerGender, String ownerFirstname, String ownerLastname) {
        Query query = getEm().createNamedQuery("find_professional_by_name_identity");
        query.setParameter("name", name);
        query.setParameter("gender", ownerGender);
        query.setParameter("firstname", ownerFirstname);
        query.setParameter("lastname", ownerLastname);
        try {
            return (ProfessionalEntity) query.getSingleResult(); 
        } catch (NoResultException e) {
            return null;
        }   
    }
        
    @Transactional(readOnly = true)
    @Override
    public ClientEntity findByLoginPassword(String login, String password) {
        Query query = getEm().createNamedQuery("find_client_by_login_password");
        query.setParameter("login", login);
        query.setParameter("password", password);
        try {
            return (ClientEntity) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }   
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<String> findAllLogin() {
        Query query = getEm().createNamedQuery("find_all_login");
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClientEntity> findAll() {
        Query query = getEm().createNamedQuery("find_all_clients");
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClientEntity> findAllWithoutAdvisor() {
        Query query = getEm().createNamedQuery("find_client_without_advisor");
        return query.getResultList();
    }
      
}
