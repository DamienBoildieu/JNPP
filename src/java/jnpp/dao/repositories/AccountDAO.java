package jnpp.dao.repositories;

import java.util.List;
import javax.persistence.Query;

import jnpp.dao.entities.accounts.Account;
import jnpp.dao.entities.accounts.SavingBook;
import jnpp.dao.entities.clients.Client;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AccountDAO extends GenericDAO<Account> implements IAccountDAO {
    
    @Transactional(readOnly = true)
    @Override
    public boolean hasAccount(Client client) {
        Query q = getEm().createNamedQuery("has_account",  Long.class);
        q.setParameter("id", client.getId());
        Long count = (Long) q.getSingleResult();
        return count != 0;
    }
    
    @Transactional(readOnly = true)
    @Override
    public boolean hasCurrentAccount(Client client) {
        Query q = getEm().createNamedQuery("has_current_account",  Long.class);
        q.setParameter("id", client.getId());
        Long count = (Long) q.getSingleResult();
        return count != 0;
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<String> findAllRib() {
        Query q = getEm().createNamedQuery("find_all_rib");
        return q.getResultList();
    } 
  
    @Transactional(readOnly = true)
    @Override
    public boolean hasBookAccount(Client client, SavingBook book) {
        Query q = getEm().createNamedQuery("has_saving_account",  Long.class);
        q.setParameter("book_id", client.getId());
        q.setParameter("client_id", client.getId());
        Long count = (Long) q.getSingleResult();
        return count != 0;
    }
    
}
