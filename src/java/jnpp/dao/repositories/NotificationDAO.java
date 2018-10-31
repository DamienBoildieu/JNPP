package jnpp.dao.repositories;

import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import jnpp.dao.entities.notifications.Notification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class NotificationDAO extends GenericDAO<Notification> implements INotificationDAO {
    
    @Transactional(readOnly = true)
    @Override
    public boolean isFake(Notification notification) {
        Query q = getEm().createNamedQuery("is_notification_fake", Long.class);
        q.setParameter("id", notification.getId());
        Long count = (Long) q.getSingleResult();
        return count != 0;
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Notification> findAll(Long clientId) {
        Query q = getEm().createNamedQuery("find_all_client_notification", 
                Notification.class);
        q.setParameter("login_id", clientId);
        return q.getResultList();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Notification> findAllUnseen(Long clientId) {
        Query q = getEm().createNamedQuery("find_all_client_unseen_ notification", 
                Notification.class);
        q.setParameter("login_id", clientId);
        return q.getResultList();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Notification> findAllUnseen(Long clientId, int n) {
        Query q = getEm().createNamedQuery("find_all_client_unseen_ notification", 
                Notification.class);
        q.setParameter("login_id", clientId);
        q.setMaxResults(n);
        return q.getResultList();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Notification> findAllUnseen(Long clientId, Date date) {
        Query q = getEm().createNamedQuery("find_all_client_recent_unseen_ notification", 
                Notification.class);
        q.setParameter("login_id", clientId);
        q.setParameter("date", date);
        return q.getResultList();
    }
        
    @Transactional(readOnly = true)
    @Override
    public void updateAllToSeen(Long clientId) {
        Query q = getEm().createNamedQuery("find_all_client_recent_unseen_ notification");
        q.setParameter("client_id", clientId);
        q.executeUpdate();
    }
    
}
