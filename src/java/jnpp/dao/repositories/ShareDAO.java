package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.accounts.ShareEntity;

public interface ShareDAO extends GenericDAO<ShareEntity> {

    List<ShareEntity> findAll();

    ShareEntity findByName(String name);

}
