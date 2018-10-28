package jnpp.service;

import java.util.List;

import jnpp.dao.entities.accounts.Account;
import jnpp.dao.entities.clients.Client;
import jnpp.service.exceptions.entities.UnknownClientException;

public interface IAccountService {

    public List<Account> getAccounts(Client client)
            throws UnknownClientException;
    
}
