package jnpp.dao.repositories;

import java.util.List;

import jnpp.dao.entities.accounts.SavingBookEntity;

public interface SavingBookDAO extends GenericDAO<SavingBookEntity> {

    /**
     * Retourne tous les livrets.
     *
     * @return Liste d'entites de livrets.
     */
    List<SavingBookEntity> findAll();

    /**
     * Retourne un livret par son nom.
     *
     * @param name Nom de livret
     * @return Entite du livret si trouve, null sinon.
     */
    SavingBookEntity findByName(String name);

}
