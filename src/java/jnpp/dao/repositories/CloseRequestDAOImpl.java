package jnpp.dao.repositories;

import java.util.List;
import javax.persistence.Query;
import jnpp.dao.entities.accounts.CloseRequestEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CloseRequestDAOImpl extends GenericDAOImpl<CloseRequestEntity> implements CloseRequestDAO {

    @Transactional(readOnly = true)
    @Override
    public List<CloseRequestEntity> findAllByRib(String rib) {
        Query query = getEm().createNamedQuery("find_closerequest_by_rib");
        query.setParameter("rib", rib);
        return query.getResultList();
    }

}
