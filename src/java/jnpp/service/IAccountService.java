package jnpp.service;

import java.util.List;

import jnpp.dao.entities.accounts.Account;
import jnpp.dao.entities.accounts.CurrentAccount;
import jnpp.dao.entities.accounts.JointAccount;
import jnpp.dao.entities.clients.Client;
import jnpp.dao.entities.clients.Identity;
import jnpp.dao.entities.clients.Private;
import jnpp.service.exceptions.accounts.DuplicatedCurrentAccountException;
import jnpp.service.exceptions.accounts.UnknownIdentityException;
import jnpp.service.exceptions.entities.FakeClientException;

public interface IAccountService {

    public List<Account> getAccounts(Client client)
            throws FakeClientException;
    
    public CurrentAccount openCurrentAccount(Client client)
            throws FakeClientException, DuplicatedCurrentAccountException;
    
    public JointAccount openJointAccount(Private client, 
            List<Identity> identities)
            throws FakeClientException, UnknownIdentityException;
    
}
