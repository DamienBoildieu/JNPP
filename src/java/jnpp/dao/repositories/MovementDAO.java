package jnpp.dao.repositories;

import java.util.Date;
import java.util.List;
import jnpp.dao.entities.movements.MovementEntity;

public interface MovementDAO extends GenericDAO<MovementEntity> {
    
    List<MovementEntity> findAllByRib(String rib);
    List<MovementEntity> findNByRib(String rib, int n);
    List<MovementEntity> findRecentByRib(String rib, Date date);
    
}
