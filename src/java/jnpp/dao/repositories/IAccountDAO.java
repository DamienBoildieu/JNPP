package jnpp.dao.repositories;

import jnpp.dao.entities.clients.Client;

public interface IAccountDAO {
    
    boolean hasAccount(Client client);
    
}
