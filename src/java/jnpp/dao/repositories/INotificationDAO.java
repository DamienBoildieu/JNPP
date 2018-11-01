package jnpp.dao.repositories;

import java.util.Date;
import java.util.List;
import jnpp.dao.entities.notifications.NotificationEntity;

public interface INotificationDAO extends GenericDAO<NotificationEntity> {
    
    boolean isFake(NotificationEntity notification);
    
    List<NotificationEntity> findAll(Long clientId);
    
    List<NotificationEntity> findAllUnseen(Long clientId);
    List<NotificationEntity> findAllUnseen(Long clientId, int n);
    List<NotificationEntity> findAllUnseen(Long clientId, Date date);
    
    void updateAllToSeen(Long clientId);
    
}
