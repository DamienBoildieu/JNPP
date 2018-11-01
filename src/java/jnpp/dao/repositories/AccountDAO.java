package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.accounts.AccountEntity;

public interface AccountDAO extends GenericDAO<AccountEntity> {
       
    boolean hasCurrentAccount(String login);
    boolean hasSavingAccount(String login, Long savingBookId);
    boolean hasShareAccount(String login);
    
    List<String> findAllRib();
    
    List<AccountEntity> findAllByLogin(String login);
    
}
