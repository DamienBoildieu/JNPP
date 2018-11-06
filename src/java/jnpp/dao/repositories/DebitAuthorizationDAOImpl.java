package jnpp.dao.repositories;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import jnpp.dao.entities.accounts.DebitAuthorizationEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DebitAuthorizationDAOImpl extends GenericDAOImpl<DebitAuthorizationEntity> 
        implements DebitAuthorizationDAO{
    
    @Transactional(readOnly = true)
    @Override
    public DebitAuthorizationEntity findByRibFromRibTo(String ribFrom, String ribTo) {
        Query query = getEm().createNamedQuery("find_debit_authorization_by_rib_from_rib_to");
        query.setParameter("rib_from", ribFrom);
        query.setParameter("rib_to", ribTo);
        try {
            return (DebitAuthorizationEntity) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    @Transactional(readOnly = true)
    @Override
    public List<DebitAuthorizationEntity> findAllByLogin(String login) {
        Query query = getEm().createNamedQuery("find_all_debit_authorization_by_login");
        query.setParameter("login", login);
        return query.getResultList();
    }    
    
    @Transactional(readOnly = true)
    @Override
    public List<DebitAuthorizationEntity> findAllByLoginRibFrom(String login, String rib) {
        Query query = getEm().createNamedQuery("find_all_debit_authorization_by_login_rib_from");
        query.setParameter("login", login);
        query.setParameter("rib", rib);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean canDebit(String ribFrom, String ribTo) {
        Query query = getEm().createNamedQuery("can_debit", Long.class);
        query.setParameter("rib_from", ribFrom);
        query.setParameter("rib_to", ribTo);
        Long count = (Long) query.getSingleResult();
        return count != 0;
    }
    
}
