package jnpp.dao.repositories;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import jnpp.dao.entities.accounts.ShareTitleEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ShareTitleDAOImpl extends GenericDAOImpl<ShareTitleEntity> implements ShareTitleDAO {

    @Transactional(readOnly = true)
    @Override
    public ShareTitleEntity findByRibName(String rib, String name) {
        Query query = getEm().createNamedQuery("find_sharetitle_by_rib_name");
        query.setParameter("rib", rib);
        query.setParameter("name", name);
        try {
            return (ShareTitleEntity) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShareTitleEntity> findAllByRib(String rib) {
        Query query = getEm().createNamedQuery("fin_all_sharetitle_by_rib");
        query.setParameter("rib", rib);
        return (List<ShareTitleEntity>) query.getResultList();
    }

}
