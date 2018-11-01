package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.accounts.ShareEntity;

public interface ShareDAO extends IGenericDAO<ShareEntity> {
    
    List<ShareEntity> findAll();
    
}
