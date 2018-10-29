package jnpp.dao.repositories;

import java.util.List;

import jnpp.dao.entities.clients.Client;
import jnpp.dao.entities.clients.Identifier;

public interface IIdentifierDAO {
    
    Identifier save(Identifier identifier);
    Identifier update(Identifier identifier);
    void delete(Identifier identifier);
    Identifier find(String login);
    
    Client find(String login, String password);    
    List<String> findAllLogin();
    String findLogin(Long clientId);
    Identifier find(Long clientId);
    
    Identifier findPrivate(String login, String firstname, String lastname, 
            String email);
    Identifier findProfessional(String login, String name, 
            String ownerFirstname, String ownerLastname, String email);
}
