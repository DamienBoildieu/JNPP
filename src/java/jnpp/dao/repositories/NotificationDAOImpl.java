package jnpp.dao.repositories;

import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import jnpp.dao.entities.notifications.NotificationEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class NotificationDAOImpl extends GenericDAOImpl<NotificationEntity> implements NotificationDAO {
    
    @Transactional(readOnly = true)
    @Override
    public List<NotificationEntity> findAllByLogin(String login) {
        Query q = getEm().createNamedQuery("find_all_notification_by_login", 
                NotificationEntity.class);
        q.setParameter("login", login);
        return q.getResultList();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<NotificationEntity> findAllUnseen(Long clientId) {
        Query q = getEm().createNamedQuery("find_all_client_unseen_notification", 
                NotificationEntity.class);
        q.setParameter("client_id", clientId);
        return q.getResultList();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<NotificationEntity> findAllUnseen(Long clientId, int n) {
        Query q = getEm().createNamedQuery("find_all_client_unseen_notification", 
                NotificationEntity.class);
        q.setParameter("client_id", clientId);
        q.setMaxResults(n);
        return q.getResultList();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<NotificationEntity> findAllUnseen(Long clientId, Date date) {
        Query q = getEm().createNamedQuery("find_all_client_recent_unseen_notification", 
                NotificationEntity.class);
        q.setParameter("client_id", clientId);
        q.setParameter("date", date);
        return q.getResultList();
    }
        
    @Transactional(readOnly = true)
    @Override
    public void updateAllToSeen(Long clientId) {
        Query q = getEm().createNamedQuery("update_all_client_notification_seen");
        q.setParameter("client_id", clientId);
        q.executeUpdate();
    }
    
}
