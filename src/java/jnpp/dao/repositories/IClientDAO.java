package jnpp.dao.repositories;

import jnpp.dao.entities.clients.Client;
import jnpp.dao.entities.clients.Gender;
import jnpp.dao.entities.clients.Private;
import jnpp.dao.entities.clients.Professional;

public interface IClientDAO {
 
    Client save(Client client);
    Client update(Client client);
    void delete(Client client);
    Client find(long id);
    
    boolean privateExist(Gender gender, String firstname, String lastname);
    boolean professionalExist(String name);
    public boolean isPrivateFake(Private client);
    public boolean isProfessionalFake(Professional client);
    
}
