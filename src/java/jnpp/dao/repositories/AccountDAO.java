package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.accounts.CurrentAccountEntity;
import jnpp.dao.entities.accounts.ShareAccountEntity;

public interface AccountDAO extends GenericDAO<AccountEntity> {
    
    boolean hasAccount(String login);
    boolean hasCurrentAccount(String login);
    boolean hasSavingAccount(String login, Long savingBookId);
    boolean hasShareAccount(String login);
    
    List<String> findAllRib();
    
    List<AccountEntity> findAllByLogin(String login);
    CurrentAccountEntity findCurrentByLogin(String login);
    ShareAccountEntity findShareByLogin(String login);
    
    List<AccountEntity> findAll();
    
}
