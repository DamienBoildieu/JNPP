package jnpp.dao.repositories;

import java.util.List;
import javax.persistence.Query;
import jnpp.dao.entities.AdvisorEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AdvisorDAOImpl extends GenericDAOImpl<AdvisorEntity> implements AdvisorDAO {

    @Transactional(readOnly = true)
    @Override
    public List<AdvisorEntity> findAll() {
        Query query = getEm().createNamedQuery("find_all_advisor");
        return query.getResultList();
    }
   
}
