package jnpp.dao.repositories;

import jnpp.dao.entities.accounts.SavingBook;

public interface ISavingBookDAO extends IGenericDAO<SavingBook> {
    
    boolean isFake(SavingBook book);
    
}
