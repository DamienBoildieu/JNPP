package jnpp.dao.repositories;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jnpp.dao.entities.notifications.NotificationEntity;

@Repository
public class NotificationDAOImpl extends GenericDAOImpl<NotificationEntity>
        implements NotificationDAO {

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
    public List<NotificationEntity> findUnseenByLogin(String login) {
        Query q = getEm().createNamedQuery("find_unseen_notification_by_login",
                NotificationEntity.class);
        q.setParameter("login", login);
        return q.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<NotificationEntity> findNUnseenByLogin(String login, int n) {
        Query q = getEm().createNamedQuery("find_unseen_notification_by_login",
                NotificationEntity.class);
        q.setParameter("login", login);
        q.setMaxResults(n);
        return q.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<NotificationEntity> findUnseenRecentByLogin(String login,
            Date date) {
        Query q = getEm().createNamedQuery(
                "find_unseen_recent_notification_by_login",
                NotificationEntity.class);
        q.setParameter("login", login);
        q.setParameter("date", date);
        return q.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public void setAllSeenByLogin(String login) {
        Query q = getEm()
                .createNamedQuery("set_all_notification_seen_by_login");
        q.setParameter("login", login);
        q.executeUpdate();
    }

}
