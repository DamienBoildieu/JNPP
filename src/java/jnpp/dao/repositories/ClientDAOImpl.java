package jnpp.dao.repositories;

import java.util.List;
import javax.persistence.Query;

import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.Gender;
import jnpp.dao.entities.Identity;
import jnpp.dao.entities.clients.PrivateEntity;
import jnpp.dao.entities.clients.ProfessionalEntity;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ClientDAOImpl extends GenericDAOImpl<ClientEntity> implements ClientDAO {
 
    @Transactional(readOnly = true)
    @Override
    public PrivateEntity findPrivateByIdentity(Gender gender, String firstname, String lastname) {
        Query query = getEm().createNamedQuery("find_private_by_identity");
        query.setParameter("gender", gender);
        query.setParameter("firstname", firstname);
        query.setParameter("lastname", lastname);
        return (PrivateEntity) query.getSingleResult();
    }
        
    @Transactional(readOnly = true)
    @Override
    public ProfessionalEntity findProfessionalByNameIdentity(String name, Gender ownerGender, String ownerFirstname, String ownerLastname) {
        Query query = getEm().createNamedQuery("find_professional_by_name_identity");
        query.setParameter("name", name);
        query.setParameter("gender", ownerGender);
        query.setParameter("firstname", ownerFirstname);
        query.setParameter("lastname", ownerLastname);
        return (ProfessionalEntity) query.getSingleResult();    
    }
        
    @Transactional(readOnly = true)
    @Override
    public ClientEntity findByLoginPassword(String login, String password) {
        Query query = getEm().createNamedQuery("find_client_by_login_password");
        query.setParameter("login", login);
        query.setParameter("password", password);
        return (ClientEntity) query.getSingleResult();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<String> findAllLogin() {
        Query query = getEm().createNamedQuery("find_all_login");
        return query.getResultList();
    }
      
}