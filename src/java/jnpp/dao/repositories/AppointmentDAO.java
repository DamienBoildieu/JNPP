package jnpp.dao.repositories;

import java.util.Date;
import java.util.List;
import jnpp.dao.entities.advisor.AppointmentEntity;

/**
 * DAO des rendez-vous
 */
public interface AppointmentDAO extends GenericDAO<AppointmentEntity> {
    /**
     * Retourne tous les rendez-vous d'un client
     * @param login l'identifiant du client
     * @return la liste des rendez-vous d'un client
     */
    List<AppointmentEntity> findAllByLogin(String login);
    /**
     * Retourne tous les rendez-vous d'un client proches de la date passée en paramètre
     * @param login l'identifiant du client
     * @param date la date du rendez-vous
     * @return la liste des rendez-vous du client proches de la date
     */
    List<AppointmentEntity> findRecentByLogin(String login, Date date);
    /**
     * Retourne le nombre de rendez-vous d'un conseiller entre deux dates
     * @param id l'identifiant du conseiller
     * @param min la date min
     * @param max la date max
     * @return le nombre de rendez-vous du conseiller
     */
    Long countByAdvisorIdInMinMax(Long id, Date min, Date max);

}
