package jnpp.dao.repositories;

import jnpp.dao.entities.clients.Client;

public interface IClientDAO {
 
    public void save(Client client);
    public void update(Client client);
    public void delete(Client client);
    public Client find(long id);
    
}
