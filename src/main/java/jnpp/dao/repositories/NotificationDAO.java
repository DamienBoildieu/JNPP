package jnpp.dao.repositories;

import java.util.Date;
import java.util.List;
import jnpp.dao.entities.notifications.NotificationEntity;

public interface NotificationDAO extends GenericDAO<NotificationEntity> {

    /**
     * Retourne toute les notifications d'un client.
     *
     * @param login Login du client.
     * @return Liste d'entite de notifications.
     */
    List<NotificationEntity> findAllByLogin(String login);

    /**
     * Retourne toute les notifications non vues d'un client.
     *
     * @param login Login du client.
     * @return Liste d'entite de notifications.
     */
    List<NotificationEntity> findUnseenByLogin(String login);

    /**
     * Retourne des notifications non vues d'un client.
     *
     * @param login Login du client.
     * @param n Nombre de notifications.
     * @return Liste d'entite de notifications.
     */
    List<NotificationEntity> findNUnseenByLogin(String login, int n);

    /**
     * Retourne des notifications non vues d'un client posterieures a une date.
     *
     * @param login Login du client.
     * @param date Date
     * @return Liste d'entite de notifications.
     */
    List<NotificationEntity> findUnseenRecentByLogin(String login, Date date);

    /**
     * Marque toutes les notifications non vues d'un client comme vues.
     *
     * @param login Login du client.
     */
    void setAllSeenByLogin(String login);

}
