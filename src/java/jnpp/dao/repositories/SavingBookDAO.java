package jnpp.dao.repositories;

import javax.persistence.Query;
import jnpp.dao.entities.accounts.SavingBook;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SavingBookDAO extends GenericDAO<SavingBook> implements ISavingBookDAO {
    
    @Transactional(readOnly = true)
    @Override
    public boolean isFake(SavingBook book) {
        Query q = getEm().createNamedQuery("is_savingbook_fake", Long.class);
        q.setParameter("id", book.getId());
        q.setParameter("name", book.getName());
        Long count = (Long) q.getSingleResult();
        return count == 0;
    }
        
}
