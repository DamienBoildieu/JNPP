package jnpp.service.services;

import java.util.List;
import jnpp.dao.entities.paymentmeans.PaymentMeanEntity.Status;
import jnpp.service.dto.paymentmeans.BankCardDTO;
import jnpp.service.dto.paymentmeans.CheckbookDTO;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.movements.AccountTypeException;
import jnpp.service.exceptions.owners.AccountOwnerException;

public interface PaymentMeanService {

    /**
     * Commande une carte bancaire.
     *
     * @param login Login du client.
     * @param rib Rib du compte cible par la carte bancaire.
     * @return DTo de la carte bancaire.
     * @throws FakeClientException Exception levee le login ne fait pas
     * reference a un client existant.
     * @throws AccountTypeException Exception levee si le compte ne peut avoir
     * de carte bancaire.
     * @throws AccountOwnerException Exception levee le client fermant le compte
     * n'est pas proprietaire du compte.
     */
    BankCardDTO commandBankCard(String login, String rib)
            throws FakeClientException, AccountTypeException, AccountOwnerException;

    /**
     * Commande une carte bancaire.
     *
     * @param login Login du client.
     * @param rib Rib du compte cible par la carte bancaire.
     * @return DTO de la carte bancaire.
     * @throws FakeClientException Exception levee le login ne fait pas
     * reference a un client existant.
     * @throws AccountTypeException Exception levee si le compte ne peut avoir
     * de carte bancaire.
     * @throws AccountOwnerException Exception levee le client fermant le compte
     * n'est pas proprietaire du compte.
     */
    CheckbookDTO commandCheckbook(String login, String rib)
            throws FakeClientException, AccountTypeException, AccountOwnerException;

    /**
     * Retourne les cartes bancaires d'un client.
     *
     * @param login Login du client.
     * @return List de DTO de cartes bancaires.
     * @throws FakeClientException Exception levee le login ne fait pas
     * reference a un client existant.
     */
    List<BankCardDTO> getBankCards(String login)
            throws FakeClientException;

    /**
     * Retourne les cartes bancaires d'un client d'un certain statut.
     *
     * @param login Login du client.
     * @param status Statut des cartes bancaires.
     * @return List de DTO de cartes bancaires.
     * @throws FakeClientException Exception levee le login ne fait pas
     * reference a un client existant.
     */
    List<BankCardDTO> getBankCards(String login, Status status)
            throws FakeClientException;

    /**
     * Retourne les cartes bancaires associees a un compte bancaire.
     *
     * @param login Login du client.
     * @param rib Rib du compte.
     * @return List de DTO de cartes bancaires.
     * @throws FakeClientException Exception levee le login ne fait pas
     * reference a un client existant.
     * @throws AccountOwnerException Exception levee le client fermant le compte
     * n'est pas proprietaire du compte.
     */
    List<BankCardDTO> getBankCards(String login, String rib)
            throws FakeClientException, AccountOwnerException;

    /**
     * Retourne les chequiers d'un client.
     *
     * @param login Login du client.
     * @return List de DTO de chequiers.
     * @throws FakeClientException Exception levee le login ne fait pas
     * reference a un client existant.
     */
    List<CheckbookDTO> getCheckBooks(String login)
            throws FakeClientException;

    /**
     * Retourne les chequiers d'un client d'un certain statut.
     *
     * @param login Login du client.
     * @param status Statut des chequiers.
     * @return List de DTO de chequiers.
     * @throws FakeClientException Exception levee le login ne fait pas
     * reference a un client existant.
     */
    List<CheckbookDTO> getCheckBooks(String login, Status status)
            throws FakeClientException;

    /**
     * Retourne les chequiers associees a un compte bancaire.
     *
     * @param login Login du client.
     * @param rib Rib du compte.
     * @return List de DTO de chequiers.
     * @throws FakeClientException Exception levee le login ne fait pas
     * reference a un client existant.
     * @throws FakeAccountException Exception levee si le rib ne fait pas
     * reference a un compte existant.
     * @throws AccountOwnerException Exception levee le client fermant le compte
     * n'est pas proprietaire du compte.
     */
    List<CheckbookDTO> getCheckBooks(String login, String rib)
            throws FakeClientException, FakeAccountException, AccountOwnerException;

}
