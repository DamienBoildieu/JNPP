package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.clients.Gender;
import jnpp.dao.entities.clients.PrivateEntity;
import jnpp.dao.entities.clients.ProfessionalEntity;

public interface ClientDAO extends GenericDAO<ClientEntity> {
    
    PrivateEntity findPrivateByIdentity(Gender gender, String firstname, String lastname);
    ProfessionalEntity findProfessionalByNameIdentity(String name, Gender ownerGender, 
            String ownerFirstname, String ownerLastname);
    ClientEntity findByLoginPassword(String login, String password);
    List<String> findAllLogin();
    
}
