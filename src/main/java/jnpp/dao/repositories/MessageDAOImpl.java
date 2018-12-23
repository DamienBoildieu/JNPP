package jnpp.dao.repositories;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jnpp.dao.entities.advisor.MessageEntity;

@Repository
public class MessageDAOImpl extends GenericDAOImpl<MessageEntity>
        implements MessageDAO {

    @Transactional(readOnly = true)
    @Override
    public List<MessageEntity> findAllByLogin(String login) {
        Query query = getEm().createNamedQuery("find_all_message_by_login");
        query.setParameter("login", login);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<MessageEntity> findNByLogin(String login, int n) {
        Query query = getEm().createNamedQuery("find_all_message_by_login");
        query.setParameter("login", login);
        query.setMaxResults(n);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<MessageEntity> findRecentByLogin(String login, Date date) {
        Query query = getEm().createNamedQuery("find_recent_message_by_login");
        query.setParameter("login", login);
        query.setParameter("date", date);
        return query.getResultList();
    }

}
