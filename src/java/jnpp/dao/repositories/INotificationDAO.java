package jnpp.dao.repositories;

import java.util.Date;
import java.util.List;
import jnpp.dao.entities.notifications.Notification;

public interface INotificationDAO extends IGenericDAO<Notification> {
    
    boolean isFake(Notification notification);
    
    List<Notification> findAll(Long clientId);
    
    List<Notification> findAllUnseen(Long clientId);
    List<Notification> findAllUnseen(Long clientId, int n);
    List<Notification> findAllUnseen(Long clientId, Date date);
    
    void updateAllToSeen(Long clientId);
    
}
