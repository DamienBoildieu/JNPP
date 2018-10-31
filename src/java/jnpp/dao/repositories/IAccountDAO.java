package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.accounts.Account;
import jnpp.dao.entities.accounts.SavingBook;
import jnpp.dao.entities.clients.Client;

public interface IAccountDAO extends IGenericDAO<Account> {
    
    boolean hasAccount(Client client);
    boolean hasCurrentAccount(Client client);
    boolean hasBookAccount(Client client, SavingBook book);
    List<String> findAllRib();
    
    
}
