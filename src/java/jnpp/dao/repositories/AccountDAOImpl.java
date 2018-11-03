package jnpp.dao.repositories;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.accounts.CurrentAccountEntity;
import jnpp.dao.entities.accounts.ShareAccountEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AccountDAOImpl extends GenericDAOImpl<AccountEntity> implements AccountDAO {
    
    @Transactional(readOnly = true)
    @Override
    public boolean hasAccount(String login) {
        Query query = getEm().createNamedQuery("has_account",  Long.class);
        query.setParameter("login", login);
        Long count = (Long) query.getSingleResult();
        return count != 0;
    }
    
    @Transactional(readOnly = true)
    @Override
    public boolean hasCurrentAccount(String login) {
        Query query = getEm().createNamedQuery("has_current_account",  Long.class);
        query.setParameter("login", login);
        Long count = (Long) query.getSingleResult();
        return count != 0;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean hasSavingAccount(String login, Long savingBookId) {
        Query query = getEm().createNamedQuery("has_saving_account",  Long.class);
        query.setParameter("login", login);
        query.setParameter("savingbook_id", savingBookId);
        Long count = (Long) query.getSingleResult();
        return count != 0;
    }
       
    @Transactional(readOnly = true)
    @Override
    public boolean hasShareAccount(String login) {
        Query query = getEm().createNamedQuery("has_share_account",  Long.class);
        query.setParameter("login", login);
        Long count = (Long) query.getSingleResult();
        return count != 0;
    }
       
    @Transactional(readOnly = true)
    @Override
    public List<String> findAllRib() {
        Query query = getEm().createNamedQuery("find_all_rib");
        return query.getResultList();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<AccountEntity> findAllByLogin(String login) {
        Query query = getEm().createNamedQuery("find_all_account_by_login");
        query.setParameter("login", login);
        return query.getResultList();
    }
    
    @Transactional(readOnly = true)
    @Override
    public CurrentAccountEntity findCurrentByLogin(String login) {
        Query query = getEm().createNamedQuery("find_current_account_by_login");
        query.setParameter("login", login);
        try {
            return (CurrentAccountEntity) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public ShareAccountEntity findShareByLogin(String login) {
        Query query = getEm().createNamedQuery("find_share_account_by_login");
        query.setParameter("login", login);
        try {
            return (ShareAccountEntity) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
}
