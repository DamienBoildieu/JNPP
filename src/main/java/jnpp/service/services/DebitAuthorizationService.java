package jnpp.service.services;

import java.util.List;

import jnpp.service.dto.accounts.DebitAuthorizationDTO;
import jnpp.service.exceptions.duplicates.DuplicateDebitAuthorizationException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeDebitAuthorizationException;
import jnpp.service.exceptions.movements.AccountTypeException;
import jnpp.service.exceptions.owners.AccountOwnerException;

public interface DebitAuthorizationService {

    /**
     * Ajoute une autorisation de debit.
     *
     * @param login   Identifiant du client.
     * @param ribFrom Rib du compte autorisant le debit.
     * @param ribTo   Rib du compte autorise a debiter.
     * @return DTo de l'autorisation.
     * @throws FakeClientException                  Exception levee le login ne
     *                                              fait pas reference a un
     *                                              client existant.
     * @throws AccountOwnerException                Exception levee le client
     *                                              n'est pas proprietaire du
     *                                              compte.
     * @throws DuplicateDebitAuthorizationException Exception levee si une
     *                                              autorisation identique
     *                                              existe deja.
     * @throws AccountTypeException                 Exception levee si le compte
     *                                              origine ne peut pas
     *                                              supporter l'operation de
     *                                              debit.
     */
    DebitAuthorizationDTO createDebitAuthorization(String login, String ribFrom,
            String ribTo) throws FakeClientException, AccountOwnerException,
            DuplicateDebitAuthorizationException, AccountTypeException;

    /**
     * Supprime une autorisation de debit.
     *
     * @param login   Login du client ayant genere l'autorisation.
     * @param ribFrom Rib du compte d'origine du debit.
     * @param ribTo   Rib du compte destination du debit.
     * @throws FakeClientException             Exception levee le login ne fait
     *                                         pas reference a un client
     *                                         existant.
     * @throws FakeDebitAuthorizationException Exception levees si les ribs ne
     *                                         font pas reference a une
     *                                         autorisation existante.
     * @throws AccountOwnerException           Exception levee le client n'est
     *                                         pas proprietaire du compte.
     */
    void deleteDebitAuthorization(String login, String ribFrom, String ribTo)
            throws FakeClientException, FakeDebitAuthorizationException,
            AccountOwnerException;

    /**
     * Retourne les autorisations d'un client.
     *
     * @param login Login du client.
     * @return Liste de DTO d'autorisations.
     * @throws FakeClientException Exception levee le login ne fait pas
     *                             reference a un client existant.
     */
    List<DebitAuthorizationDTO> getDebitAuthorizations(String login)
            throws FakeClientException;

    /**
     * Retourne les autorisations d'un compte d'un client.
     *
     * @param login Login du client.
     * @param rib   Rib du compte.
     * @return Liste de DTO d'autorisations.
     * @throws FakeClientException   Exception levee le login ne fait pas
     *                               reference a un client existant.
     * @throws AccountOwnerException Exception levee le client n'est pas
     *                               proprietaire du compte.
     */
    List<DebitAuthorizationDTO> getDebitAuthorizations(String login, String rib)
            throws FakeClientException, AccountOwnerException;

}
