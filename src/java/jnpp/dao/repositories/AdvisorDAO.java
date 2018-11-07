package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.advisor.AdvisorEntity;
import jnpp.dao.entities.clients.ClientEntity;

/**
 * DAO des conseillers
 */
public interface AdvisorDAO extends GenericDAO<AdvisorEntity> {

    /**
     * Récupère la liste de tous les conseillers
     *
     * @return la liste de tous les conseillers
     */
    List<AdvisorEntity> findAll();

    /**
     * Recherche un conseiller par son prénom et son nom
     *
     * @param firstname le prénom du conseiller
     * @param lastname le nom du conseiller
     * @return le conseiller si il existe, null sinon
     */
    AdvisorEntity findByIdentity(String firstname, String lastname);

    /**
     * Retourne tous les clients d'un conseiller
     *
     * @param id l'identifiant du conseiller
     * @return la liste des clients du conseiller
     */
    List<ClientEntity> findAllClientByID(Long id);

}
