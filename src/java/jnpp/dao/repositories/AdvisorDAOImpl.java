package jnpp.dao.repositories;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import jnpp.dao.entities.advisor.AdvisorEntity;
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

    @Transactional(readOnly = true)
    @Override
    public AdvisorEntity findByIdentity(String firstname, String lastname) {
        Query query = getEm().createNamedQuery("find_advisor_by_identity");
        query.setParameter("firstname", firstname);
        query.setParameter("lastname", lastname);
        try {
            return (AdvisorEntity) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
   
}
