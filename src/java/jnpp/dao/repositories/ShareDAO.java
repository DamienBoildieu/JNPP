package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.accounts.ShareEntity;

public interface ShareDAO extends GenericDAO<ShareEntity> {

    /**
     * Retourne toutes les actions.
     *
     * @return Liste d'entite d'actions.
     */
    List<ShareEntity> findAll();

    /**
     * Retourne une action par son nom.
     *
     * @param name Nom d'action.
     * @return Entite de l'action si trouve, null sinon.
     */
    ShareEntity findByName(String name);

}
