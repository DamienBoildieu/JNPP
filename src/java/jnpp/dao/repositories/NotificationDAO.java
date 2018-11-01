package jnpp.dao.repositories;

import java.util.Date;
import java.util.List;
import jnpp.dao.entities.notifications.NotificationEntity;

public interface NotificationDAO extends GenericDAO<NotificationEntity> {
    
    List<NotificationEntity> findAllByLogin(String login);    
    List<NotificationEntity> findUnseenByLogin(String login);
    List<NotificationEntity> findNUnseenByLogin(String login, int n);
    List<NotificationEntity> findUnseenRecentByLogin(String login, Date date);
    
    void setAllSeenByLogin(String login);
    
}
