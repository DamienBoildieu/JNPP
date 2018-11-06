package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.IdentityEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.clients.PrivateEntity;
import jnpp.dao.entities.clients.ProfessionalEntity;

public interface ClientDAO extends GenericDAO<ClientEntity> {
    
    PrivateEntity findPrivateByIdentity(IdentityEntity.Gender gender, String firstname, String lastname);
    ProfessionalEntity findProfessionalByNameIdentity(String name, IdentityEntity.Gender ownerGender, 
            String ownerFirstname, String ownerLastname);
    ClientEntity findByLoginPassword(String login, String password);
    List<String> findAllLogin();
    List<ClientEntity> findAll();
    List<ClientEntity> findAllWithoutAdvisor();
}
