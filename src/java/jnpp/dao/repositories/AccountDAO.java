package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.accounts.CurrentAccountEntity;
import jnpp.dao.entities.accounts.ShareAccountEntity;

/**
 * DAO des comptes bancaire
 */
public interface AccountDAO extends GenericDAO<AccountEntity> {
    /**
     * Recherche si un utilisateur possède un compte
     * @param login l'identifiant de l'utilisateur
     * @return true si il possède un compte, false sinon
     */
    boolean hasAccount(String login);
    /**
     * Recherche si un utilisateur possède un compte courant
     * @param login l'identifiant de l'utilisateur
     * @return true si il possède un compte courant, false sinon
     */
    boolean hasCurrentAccount(String login);
    /**
     * Recherche si un utilisateur possède un compte livret du type indiqué
     * @param login l'identifiant de l'utilisateur
     * @param savingBookId le livret
     * @return true si il possède un compte livret du type indiqué, false sinon
     */
    boolean hasSavingAccount(String login, Long savingBookId);
    /**
     * Recherche si un utilisateur possède un compte d'actions
     * @param login l'identifiant de l'utilisateur
     * @return true si il possède un compte d'actions, false sinon
     */
    boolean hasShareAccount(String login);
    /**
     * Retourne tous les rib des comptes existants
     * @return une liste des rib des comptes existants
     */
    List<String> findAllRib();
    /**
     * Retourne les comptes d'un utilisateur
     * @param login l'identifiant de l'utilisateur
     * @return la liste des comptes de l'utilisateur
     */
    List<AccountEntity> findAllByLogin(String login);
    /**
     * Retourne le compte courant d'un utilisateur
     * @param login l'identifiant de l'utilisateur
     * @return le compte courant si il existe, null sinon
     */
    CurrentAccountEntity findCurrentByLogin(String login);
    /**
     * Retourne le compte d'actions d'un utilisateur
     * @param login l'identifiant de l'utilisateur
     * @return le compte d'actions si il existe, null sinon
     */
    ShareAccountEntity findShareByLogin(String login);
    /**
     * Retourne tous les comptes existants
     * @return la liste des comptes
     */
    List<AccountEntity> findAll();

}
