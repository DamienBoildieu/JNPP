package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.accounts.DebitAuthorizationEntity;

/**
 * DAO des autorisations de débit
 */
public interface DebitAuthorizationDAO extends GenericDAO<DebitAuthorizationEntity> {

    /**
     * Recherche l'autorisation de débit entre deux comptes
     *
     * @param ribFrom l'émetteur de l'autorisation
     * @param ribTo le compte autorisé à débiter
     * @return l'autorisation si elle existe, null sinon
     */
    DebitAuthorizationEntity findByRibFromRibTo(String ribFrom, String ribTo);

    /**
     * Recherche les autorisations émises par un utilisateur
     *
     * @param login l'identifiant du client
     * @return la liste des autorisations émises par l'utilisateur
     */
    List<DebitAuthorizationEntity> findAllByLogin(String login);

    /**
     * Recherche la liste des autorisations émises par un utilisateur pour un
     * compte
     *
     * @param login l'identifiant du client
     * @param rib le compte
     * @return la liste des autorisations émises par un client pour un compte
     */
    List<DebitAuthorizationEntity> findAllByLoginRibFrom(String login, String rib);

    /**
     * Indique si un compte peut débiter un autre
     *
     * @param ribFrom le compte qui veut débiter
     * @param ribTo le compte à débiter
     * @return true si il peut débiter, false sinon
     */
    boolean canDebit(String ribFrom, String ribTo);

}
