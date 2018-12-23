package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.IdentityEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.clients.PrivateEntity;
import jnpp.dao.entities.clients.ProfessionalEntity;

/**
 * DAO des clients
 */
public interface ClientDAO extends GenericDAO<ClientEntity> {

    /**
     * Rercher un particulier
     *
     * @param gender le sexe du client
     * @param firstname le prénom du client
     * @param lastname le nom du client
     * @return le particulier si il existe, null sinon
     */
    PrivateEntity findPrivateByIdentity(IdentityEntity.Gender gender, String firstname, String lastname);

    /**
     * Recherche un professionnel
     *
     * @param name le nom de l'entreprise
     * @param ownerGender le sexe du propriétaire
     * @param ownerFirstname le prénom du propriétaire
     * @param ownerLastname le nom du propriétaire
     * @return le professionnel si il existe, null sinon
     */
    ProfessionalEntity findProfessionalByNameIdentity(String name, IdentityEntity.Gender ownerGender,
            String ownerFirstname, String ownerLastname);

    /**
     * Recherche un client par son identifiant et son mot de passe
     *
     * @param login l'identifiant
     * @param password le mot de passe
     * @return le client si il existe, null sinon
     */
    ClientEntity findByLoginPassword(String login, String password);

    /**
     * Retourne tous les identifiants des clients
     *
     * @return la liste des identifiants des clients
     */
    List<String> findAllLogin();

    /**
     * Retourne tous les clients
     *
     * @return la liste des clients
     */
    List<ClientEntity> findAll();

    /**
     * Retourne tous les clients qui n'ont pas de conseiller
     *
     * @return la liste des clients qui n'ont pas de conseiller
     */
    List<ClientEntity> findAllWithoutAdvisor();
}
