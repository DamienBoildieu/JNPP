package jnpp.dao.repositories;

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
    
}
