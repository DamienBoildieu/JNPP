package jnpp.dao.repositories;

import java.util.List;

import jnpp.dao.entities.paymentmeans.BankCardEntity;
import jnpp.dao.entities.paymentmeans.CheckbookEntity;
import jnpp.dao.entities.paymentmeans.PaymentMeanEntity;

public interface PaymentMeanDAO extends GenericDAO<PaymentMeanEntity> {

    /**
     * Retourne les cartes bancaires d'un client.
     *
     * @param login Login du client.
     * @return Liste d'entites de cartes bancaires.
     */
    List<BankCardEntity> findBankCardByLogin(String login);

    /**
     * Retourne les cartes bancaires d'un client d'un certain status.
     *
     * @param login  Login du client.
     * @param status Statut des cartes bancaires.
     * @return Liste d'entites de cartes bancaires.
     */
    List<BankCardEntity> findBankCardByLoginStatus(String login,
            PaymentMeanEntity.Status status);

    /**
     * Retourne les cartes bancaires d'un compte.
     *
     * @param login Login du client.
     * @param rib   Rib du compte.
     * @return Liste d'entites de cartes bancaires.
     */
    List<BankCardEntity> findBankCardByLoginRib(String login, String rib);

    /**
     * Retourne les chequiers d'un client.
     *
     * @param login Login du client.
     * @return Liste d'entites de chequiers.
     */
    List<CheckbookEntity> findCheckBookByLogin(String login);

    /**
     * Retourne les chequiers d'un client d'un certain status.
     *
     * @param login  Login du client.
     * @param status Statut des chequiers.
     * @return Liste d'entites de chequiers.
     */
    List<CheckbookEntity> findCheckBookByLoginStatus(String login,
            PaymentMeanEntity.Status status);

    /**
     * Retourne les chequiers d'un compte.
     *
     * @param login Login du client.
     * @param rib   Rib du compte.
     * @return Liste d'entites de chequiers.
     */
    List<CheckbookEntity> findCheckBookByLoginRib(String login, String rib);

    /**
     * Retourne tous les moyens de paiements.
     *
     * @return Liste d'entites
     */
    List<PaymentMeanEntity> findAll();

    /**
     * Retourne tous les identifiants des moyens de paiements.
     *
     * @return Liste d'identifiants.
     */
    List<String> findAllId();

}
