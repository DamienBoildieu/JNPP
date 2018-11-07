package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.accounts.CloseRequestEntity;

/**
 * DAO des demandes de fermeture de compte
 */
public interface CloseRequestDAO extends GenericDAO<CloseRequestEntity> {
    /**
     * Retourne toutes les demande de fermeture d'un compte
     * @param rib le compte
     * @return la liste des demandes de fermeture du compte
     */
    List<CloseRequestEntity> findAllByRib(String rib);

}
