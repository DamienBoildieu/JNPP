package jnpp.service;

import java.util.Date;
import java.util.List;

import jnpp.dao.entities.accounts.Account;
import jnpp.dao.entities.accounts.CurrentAccount;
import jnpp.dao.entities.accounts.DebitAuthorization;
import jnpp.dao.entities.accounts.JointAccount;
import jnpp.dao.entities.accounts.SavingAccount;
import jnpp.dao.entities.accounts.SavingBook;
import jnpp.dao.entities.accounts.Share;
import jnpp.dao.entities.accounts.ShareAccount;
import jnpp.dao.entities.clients.Client;
import jnpp.dao.entities.clients.Identity;
import jnpp.dao.entities.clients.Private;
import jnpp.dao.entities.movements.Movement;
import jnpp.service.exceptions.ClosureException;
import jnpp.service.exceptions.accounts.ClosureRequestException;
import jnpp.service.exceptions.accounts.DuplicatedAccountException;
import jnpp.service.exceptions.accounts.DuplicatedAuthorizationException;
import jnpp.service.exceptions.accounts.OwnerException;
import jnpp.service.exceptions.accounts.UnknownIdentityException;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeBookException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeDebitAuthorizationException;

/** Service gerant les comptes bancaires.
 * @author Pierre Bourquat
 * @author Damien Boildieu */
public interface IAccountService extends IService {
    
    /** Retourne une liste des livrets existants. 
     * @return List de livrets. */
    List<SavingBook> getSavingBooks();
    
    /** Retourne une liste des actions existantes.
     * @return Liste d'actions. */
    List<Share> getShares();

    /** Retourne une liste des comptes bancaires d'un client.
     * @param client Client concerne.
     * @return Liste de comptes bancaires.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<Account> getAccounts(Client client)
            throws FakeClientException;
    
    /** Ouvre un compte courant.
     * @param client Client concerne.
     * @return L'entite du compte courant ouvert.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws DuplicatedAccountException Exception levee si le client possede
     * deja un compte courant. */
    CurrentAccount openCurrentAccount(Client client)
            throws FakeClientException, DuplicatedAccountException;
    
    /** Clot un compte courant. Un compte courant non vide ne peut etre clos.
     * @param client Proprietaire du compte.
     * @param account Compte a clore.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws FakeAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte existant.
     * @throws ClosureException Exception levee si le compte ne peut pas etre
     * clos.
     * @throws OwnerException Exception levee si le client n'est pas le 
     * proprietaire du compte. */
    void closeCurrentAccount(Client client, CurrentAccount account)
            throws FakeClientException, FakeAccountException, ClosureException,
            OwnerException;
    
    /** Ouvre un compte joint. Un compte joint ne peut appartenir qu'a des
     * particuliers.
     * @param client Particulier ouvrant le compte.
     * @param identities Personnes avec qui le compte est joint.
     * @return L'entite du compte joint ouvert.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws UnknownIdentityException Exception levee si l'une des identites
     * ne fait pas reference a un particulier client de la banque. */
    JointAccount openJointAccount(Private client, 
            List<Identity> identities)
            throws FakeClientException, UnknownIdentityException;
    
    /** Clot un compte joint. Un compte joint non vide ne peut etre clos.
     * @param client Un des particuliers proprietaires du compte joint.
     * @param account Compte joint a clore.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws FakeAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte existant.
     * @throws ClosureException Exception levee si le compte ne peut pas etre
     * clos.
     * @throws ClosureRequestException Exception levee si tous les autres 
     * proprietaire n'ont pas demande la clos du compte joint.
     * @throws OwnerException Exception levee si le client n'est pas le 
     * proprietaire du compte. */
    void closeJointAccount(Private client, JointAccount account)
            throws FakeClientException, FakeAccountException, ClosureException, 
            ClosureRequestException, OwnerException;
    
    /** Ouvre un compte livret. Seul les particuliers peuvent ouvrir un compte
     * livret.
     * @param client Particulier proprietaire du compte.
     * @param book Type de compte livret.
     * @return L'entite du compte livret ouvert.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws FakeBookException Exception levee si l'entite book ne fait pas
     * reference a un livret existant.
     * @throws DuplicatedAccountException Exception levee si le particulier 
     * possede deja un compte livret de ce type. */
    SavingAccount openSavingAccount(Private client, SavingBook book)
            throws FakeClientException, FakeBookException, 
            DuplicatedAccountException;
    
    /** Clot un compte livret. Un compte livret non vide ne peut etre clos.
     * @param client Particulier proprietaire du compte livret.
     * @param account Compte a clore.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws FakeAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte existant.
     * @throws ClosureException Exception levee si le compte ne peut pas etre
     * clos.
     * @throws OwnerException Exception levee si le client n'est pas le 
     * proprietaire du compte. */
    void closeSavingAccount(Private client, SavingAccount account)
            throws FakeClientException, FakeAccountException, ClosureException, 
            OwnerException;
    
    /** Ouvre un compte d'actions. 
     * @param client Proprietaire du compte.
     * @return L'entite du compte action ouvert.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws DuplicatedAccountException Exception levee si le particulier 
     * possede deja un compte d'actions. */
    ShareAccount openShareAccount(Client client)
            throws FakeClientException, DuplicatedAccountException;
    
    /** Clot un compte d'actions. Un compte d'actions non vide ne peut etre 
     * clos.
     * @param client Particulier proprietaire du compte d'actions.
     * @param account Compte a clore.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws FakeAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte existant.
     * @throws ClosureException Exception levee si le compte ne peut pas etre
     * clos.
     * @throws OwnerException Exception levee si le client n'est pas le 
     * proprietaire du compte. */
    void closeShareAccount(Client client, ShareAccount account)
            throws FakeClientException, FakeAccountException, ClosureException, 
            OwnerException;
    
    /** Retourne toutes les transactions d'un client.
     * @param client Client concerne.
     * @return Liste de transactions.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<Movement> getMovements(Client client)
            throws FakeClientException;
    
    /** Retourne les n dernieres transactions d'un client.
     * @param client Client concerne.
     * @param n Nombre de transaction.
     * @return Liste de transactions.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<Movement> getMovements(Client client, int n)
            throws FakeClientException;
    
    /** Retourne les transactions posterieurs a une data d'un client.
     * @param client Client concerne.
     * @param date Date
     * @return Liste de transactions.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<Movement> getMovements(Client client, Date date)
            throws FakeClientException;
    
    /** Retourne toutes les transactions d'un compte bancaire.
     * @param account Compte bancaire concerne.
     * @return Liste de transactions.
     * @throws FakeAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte bancaire existant. */
    List<Movement> getMovements(Account account)
            throws FakeAccountException;
    
    /** Retourne les n dernieres transactions d'un compte bancaire.
     * @param account Compte bancaire concerne.
     * @param n Nombre de transaction.
     * @return Liste de transactions.
     * @throws FakeAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte bancaire existant. */
    List<Movement> getMovements(Account account, int n)
            throws FakeAccountException;
    
    /** Retourne les transactions posterieurs a une data d'un compte bancaire.
     * @param account Compte bancaire concerne.
     * @param date Date
     * @return Liste de transactions.
     * @throws FakeAccountException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<Movement> getMovements(Account account, Date date)
            throws FakeAccountException;
    
}
