package jnpp.dao.repositories;

import jnpp.dao.entities.clients.Client;
import jnpp.dao.entities.clients.Gender;

public interface IClientDAO extends IGenericDAO<Client> {
     
    boolean privateExist(Gender gender, String firstname, String lastname);
    boolean professionalExist(String name);
    boolean isFake(Client client);
    
}
