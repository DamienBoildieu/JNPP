package jnpp.dao.repositories;

import java.io.Serializable;

public interface GenericDAO<T extends Serializable> {

    /**
     * Sauvegarde l'entite t et retourne l'entite sauvegardee.
     * @param t Entite a sauvegarder.
     * @return Entite sauvegarder
     */
    T save(T t);

    /**
     * Met a jour une entite.
     * @param t Entite a mettre a jour.
     * @return Entite mise a jour.
     */
    T update(T t);

    /**
     * Supprime une entite.
     * @param t Entite a supprimer.
     */
    void delete(T t);

    /**
     * Retourne une entite trouvee par sa cle primaire.
     * @param id Cle primaire.
     * @return L'entite si elle a ete trouvee, null sinon.
     */
    T find(Object id);

}
