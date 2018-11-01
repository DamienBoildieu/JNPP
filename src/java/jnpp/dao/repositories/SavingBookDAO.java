package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.accounts.SavingBookEntity;

public interface SavingBookDAO extends GenericDAO<SavingBookEntity> {
    
    List<SavingBookEntity> findAll();
    SavingBookEntity findByName(String name);
    
}
