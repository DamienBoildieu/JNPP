package jnpp.service.services;

import java.util.Date;
import java.util.List;

import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.dto.accounts.CurrentAccountDTO;
import jnpp.service.dto.accounts.JointAccountDTO;
import jnpp.service.dto.accounts.SavingAccountDTO;
import jnpp.service.dto.accounts.SavingBookDTO;
import jnpp.service.dto.accounts.ShareAccountDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.dto.movements.MovementDTO;
import jnpp.service.exceptions.ClosureException;
import jnpp.service.exceptions.accounts.ClientTypeException;
import jnpp.service.exceptions.accounts.CloseRequestException;
import jnpp.service.exceptions.accounts.UnknownIdentityException;
import jnpp.service.exceptions.duplicates.DuplicateAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeSavingBookException;
import jnpp.service.exceptions.owners.AccountOwnerException;

public interface AccountService {

    /**
     * Retourne une lists des livrets existants.
     *
     * @return Liste de DTO de livrets.
     */
    List<SavingBookDTO> getSavingBooks();

    /**
     * Retourne une liste des actions existantes.
     *
     * @return Liste de DTO d'actions .
     */
    List<ShareDTO> getShares();

    /**
     * Retourne une liste des comptes d'un client.
     *
     * @param login Login du client.
     * @return Liste de DTO de comptes.
     * @throws FakeClientException Exception levee le login ne fait pas
     *                             reference a un client existant.
     */
    List<AccountDTO> getAccounts(String login) throws FakeClientException;

    /**
     * Retourne le le compte action d'un client.
     *
     * @param login Login du client.
     * @return DTO du compte action.
     * @throws FakeClientException Exception levee le login ne fait pas
     *                             reference a un client existant.
     */
    ShareAccountDTO getShareAccount(String login) throws FakeClientException;

    /**
     * Ouvre un compte courant.
     *
     * @param login Login du client ouvrant le compte.
     * @return DTO du compte courant ouvert.
     * @throws DuplicateAccountException Exception levee si le client a deja un
     *                                   compte courant.
     * @throws FakeClientException       Exception levee le login ne fait pas
     *                                   reference a un client existant.
     */
    CurrentAccountDTO openCurrentAccount(String login)
            throws DuplicateAccountException, FakeClientException;

    /**
     * Ouvre un compte joint. Un compte joint ne peut etre ouvert que part des
     * particuliers. La liste de client doit contenir l'identite du client
     * faisant l'ouverture du compte. Les identites doivent faire reference a
     * des particuliers clients de la banque.
     *
     * @param login      Login du client faisant l'ouverture du compte joint.
     * @param identities Liste des proprietaires du compte.
     * @return DTO du compte joint ouvert.
     * @throws FakeClientException      Exception levee le login ne fait pas
     *                                  reference a un client existant.
     * @throws UnknownIdentityException Exception levee si au moins l'une des
     *                                  identites ne fait pas reference a un
     *                                  client de la banque.
     * @throws ClientTypeException      Exception levee si le type du client
     *                                  n'est pas autorise a ouvrir un compte
     *                                  joint.
     */
    JointAccountDTO openJointAccount(String login, List<IdentityDTO> identities)
            throws FakeClientException, UnknownIdentityException,
            ClientTypeException;

    /**
     * Ouvre un compte livret.
     *
     * @param login Login du client ouvrant le compte.
     * @param name  Nom du livret.
     * @return DTO du compte livret ouvert.
     * @throws FakeClientException       Exception levee le login ne fait pas
     *                                   reference a un client existant.
     * @throws FakeSavingBookException   Exception levee si le nom du livret ne
     *                                   fait pas reference a un livret
     *                                   existant.
     * @throws DuplicateAccountException Exception levee si le client a deja un
     *                                   compte livret de ce livret.
     * @throws ClientTypeException       Exception levee si le type du client
     *                                   n'est pas autorise a ouvrir un compte
     *                                   livret.
     */
    SavingAccountDTO openSavingAccount(String login, String name)
            throws FakeClientException, FakeSavingBookException,
            DuplicateAccountException, ClientTypeException;

    /**
     * Ouvre un compte d'action.
     *
     * @param login Login du client ouvrant le compte.
     * @return DTO du compte d'action ouvert.
     * @throws FakeClientException       Exception levee le login ne fait pas
     *                                   reference a un client existant.
     * @throws DuplicateAccountException Exception levee si le client a deja un
     *                                   compte d'action.
     */
    ShareAccountDTO openShareAccount(String login)
            throws FakeClientException, DuplicateAccountException;

    /**
     * Ferme un compte bancaire. Un compte courant, un compte joint ou un compte
     * livret ne peut etre ferme que si la quantite d'argent sur ce compte est
     * nulle. Un compte d'action ne peut etre ferme que si ce compte n'a aucun
     * titres d'action. Un compte joint ne peut etre ferme que si tous les
     * proprietaires du compte on fait la demande de fermeture. Si le seul
     * client n'ayant pas fait de demande de fermeture est le client acutel, le
     * compte joint est ferme. Si le compte joint n'a pas pu ferme, une demande
     * de fermeture au nom du client est generee.
     *
     * @param login Login du client fermant le compte.
     * @param rib   Rib du compte ferme.
     * @throws FakeClientException   Exception levee le login ne fait pas
     *                               reference a un client existant.
     * @throws AccountOwnerException Exception levee le client fermant le compte
     *                               n'est pas proprietaire du compte.
     * @throws ClosureException      Exception levee si le compte ne peut pas
     *                               etre ferme.
     * @throws CloseRequestException Exception levee si le compte a fermer est
     *                               un compte joint et que tous les autres
     *                               proprietaires de ce compte n'ont pas fait
     *                               de demande de fermeture de compte.
     */
    void closeAccount(String login, String rib) throws FakeClientException,
            AccountOwnerException, ClosureException, CloseRequestException;

    /**
     * Retourne l'historique d'un compte bancaire.
     *
     * @param login Login du client.
     * @param rib   Rib du compte.
     * @return Liste de DTO de transactions.
     * @throws FakeClientException   Exception levee le login ne fait pas
     *                               reference a un client existant.
     * @throws AccountOwnerException Exception levee le client n'est pas
     *                               proprietaire du compte.
     */
    List<MovementDTO> getMovements(String login, String rib)
            throws FakeClientException, AccountOwnerException;

    /**
     * Retourne l'historique d'un compte bancaire.
     *
     * @param login Login du client.
     * @param rib   Rib du compte.
     * @param n     Nombre de transactions.
     * @return Liste de DTO de transactions.
     * @throws FakeClientException   Exception levee le login ne fait pas
     *                               reference a un client existant.
     * @throws AccountOwnerException Exception levee le client n'est pas
     *                               proprietaire du compte.
     */
    List<MovementDTO> getMovements(String login, String rib, int n)
            throws FakeClientException, AccountOwnerException;

    /**
     * Retourne l'historique d'un compte bancaire a partir d'une date.
     *
     * @param login Login du client.
     * @param rib   Rib du compte.
     * @param date  Debut de l'historique.
     * @return Liste de DTO de transactions.
     * @throws FakeClientException   Exception levee le login ne fait pas
     *                               reference a un client existant.
     * @throws AccountOwnerException Exception levee le client n'est pas
     *                               proprietaire du compte.
     */
    List<MovementDTO> getMovements(String login, String rib, Date date)
            throws FakeClientException, AccountOwnerException;

}
