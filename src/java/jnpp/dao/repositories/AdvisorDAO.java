package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.advisor.AdvisorEntity;

public interface AdvisorDAO extends GenericDAO<AdvisorEntity> {
    
    List<AdvisorEntity> findAll();
    
}
