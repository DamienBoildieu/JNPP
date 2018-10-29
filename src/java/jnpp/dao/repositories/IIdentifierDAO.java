package jnpp.dao.repositories;

import java.util.List;

import jnpp.dao.entities.clients.Identifier;

public interface IIdentifierDAO {
    
    Identifier save(Identifier identifier);
    Identifier update(Identifier identifier);
    void delete(Identifier identifier);
    Identifier find(String login);
    
    Identifier find(String login, String password);    
    List<String> findAllLogin();
    
}
