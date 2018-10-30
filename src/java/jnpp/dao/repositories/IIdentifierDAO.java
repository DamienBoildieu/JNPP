package jnpp.dao.repositories;

import java.util.List;

import jnpp.dao.entities.clients.Client;
import jnpp.dao.entities.clients.Identifier;

public interface IIdentifierDAO extends IGenericDAO<Identifier> {
       
    Client find(String login, String password);    
    List<String> findAllLogin();
    String findLogin(Long clientId);
    Identifier findByClientId(Long clientId);
    Identifier findPrivate(String login, String firstname, String lastname, 
            String email);
    Identifier findProfessional(String login, String name, 
            String ownerFirstname, String ownerLastname, String email);
}
