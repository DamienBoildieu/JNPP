package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.accounts.ShareTitleEntity;

public interface ShareTitleDAO extends GenericDAO<ShareTitleEntity> {

    /**
     * Retourne un titre d'action recherche par son proprietaire et son nom.
     *
     * @param login Login du proprietaire.
     * @param name Nom de l'action.
     * @return Entite dtu titre d'action si trouve, null sinon.
     */
    ShareTitleEntity findByRibName(String login, String name);

    /**
     * Retourne tous les titres d'actions d'un compte.
     *
     * @param rib Rib du compte.
     * @return List d'entites de titres d'actions.
     */
    List<ShareTitleEntity> findAllByRib(String rib);

}
