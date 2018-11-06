package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.accounts.CloseRequestEntity;

public interface CloseRequestDAO extends GenericDAO<CloseRequestEntity> {

    List<CloseRequestEntity> findAllByRib(String rib);

}
