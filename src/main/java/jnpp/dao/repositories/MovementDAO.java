package jnpp.dao.repositories;

import java.util.Date;
import java.util.List;
import jnpp.dao.entities.movements.MovementEntity;

/**
 * DAO des transactions
 */
public interface MovementDAO extends GenericDAO<MovementEntity> {

    /**
     * Retourne toutes les transactions d'un compte
     *
     * @param rib le rib du compte
     * @return la liste des transactions du compte
     */
    List<MovementEntity> findAllByRib(String rib);

    /**
     * Retourne les n dernières transactions d'un compte
     *
     * @param rib le rib du compte
     * @param n le nombre de transactions à récupérer
     * @return la liste des n dernières transactions d'un compte
     */
    List<MovementEntity> findNByRib(String rib, int n);

    /**
     * Retourne toutes les transactions d'un compte effectuées après la date
     * indiquée
     *
     * @param rib le rib du compte
     * @param date la date
     * @return les transactions d'un compte effectuées après la date indiquée
     */
    List<MovementEntity> findRecentByRib(String rib, Date date);

}
