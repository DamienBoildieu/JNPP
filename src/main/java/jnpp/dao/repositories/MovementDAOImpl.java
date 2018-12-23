package jnpp.dao.repositories;

import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import jnpp.dao.entities.movements.MovementEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MovementDAOImpl extends GenericDAOImpl<MovementEntity> implements MovementDAO {

    @Transactional(readOnly = true)
    @Override
    public List<MovementEntity> findAllByRib(String rib) {
        Query query = getEm().createNamedQuery("find_all_movement_by_rib");
        query.setParameter("rib", rib);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<MovementEntity> findNByRib(String rib, int n) {
        Query query = getEm().createNamedQuery("find_all_movement_by_rib");
        query.setParameter("rib", rib);
        query.setMaxResults(n);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<MovementEntity> findRecentByRib(String rib, Date date) {
        Query query = getEm().createNamedQuery("find_recent_movement_by_rib");
        query.setParameter("rib", rib);
        query.setParameter("date", date);
        return query.getResultList();
    }

}
