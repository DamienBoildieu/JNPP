package jnpp.dao.repositories;

import java.util.Date;
import java.util.List;
import jnpp.dao.entities.advisor.MessageEntity;

/**
 * DAO des messages
 */
public interface MessageDAO extends GenericDAO<MessageEntity> {
    /**
     * Retourne tous les messages d'un utilisateur
     * @param login l'identifiant de l'utilisateur
     * @return les messages de l'utilisateur
     */
    List<MessageEntity> findAllByLogin(String login);
    /**
     * Retourne les n derniers messages d'un utilisateur
     * @param login l'identifiant de l'utilisateur
     * @param n le nombre de messages à récupérer
     * @return les n derniers messages de l'utilisateur
     */
    List<MessageEntity> findNByLogin(String login, int n);
    /**
     * Retourne tous les messages envoyés ou reçus après la date indiquée d'un utilisateur
     * @param login l'identifiant de l'utilisateur
     * @param date la date
     * @return les messages envoyés ou reçus après la date indiquée d'un utilisateur
     */
    List<MessageEntity> findRecentByLogin(String login, Date date);

}
