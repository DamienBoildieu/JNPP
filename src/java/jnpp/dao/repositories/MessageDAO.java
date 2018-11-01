package jnpp.dao.repositories;

import java.util.Date;
import java.util.List;
import jnpp.dao.entities.MessageEntity;

public interface MessageDAO extends GenericDAO<MessageEntity> {
    
    List<MessageEntity> findAllByLogin(String login);
    List<MessageEntity> findNByLogin(String login, int n);
    List<MessageEntity> findRecentByLogin(String login, Date date);
    
}
