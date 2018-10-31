package jnpp.service;

import java.util.List;

import jnpp.dao.entities.BankCard;
import jnpp.dao.entities.Checkbook;
import jnpp.dao.entities.PaymentMean.Status;
import jnpp.dao.entities.accounts.Account;
import jnpp.dao.entities.clients.Client;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.movements.AccountTypeException;
import jnpp.service.exceptions.owners.AccountOwnerException;

/** Service de gestion des moyens de paiements.
 * @author Pierre Bourquat
 * @author Damien Boildieu */
public interface IPaymentMeanService extends IService {
    
    /** Commande une carte bancaire pour un compte bancaire.
     * @param client Client proprietaire du compte bancaire.
     * @param account Compte bancaire cible de la carte bancaire.
     * @return Entite de la carte bancaire.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. 
     * @throws FakeAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte bancaire existant.
     * @throws AccountTypeException Exception levee si le compte bancaire ne 
     * peut pas etre associe a une carte bancaire. */
    BankCard commandBankCard(Client client, Account account)
            throws FakeClientException, FakeAccountException,
            AccountTypeException;
    
    /** Commande un chequier pour un compte bancaire.
     * @param client Client proprietaire du compte bancaire.
     * @param account Compte bancaire cible du chequier.
     * @return Entite du chequier.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. 
     * @throws FakeAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte bancaire existant.
     * @throws AccountTypeException Exception levee si le compte bancaire ne 
     * peut pas etre associe a un chequier. */
    Checkbook commandCheckbook(Client client, Account account)
            throws FakeClientException, FakeAccountException,
            AccountTypeException;
    
    /** Retourne une liste des cartes bancaires d'un statut specifique d'un 
     * client. Si le statut est null, toutes les cartes bancaires sont 
     * retournees.
     * @param client Proprietaire des cartes bancaires.
     * @param status Statut des cartes bancaires.
     * @return Liste des cartes bancaires dont le statut correspond au statut
     * specifie.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<BankCard> getBankCards(Client client, Status status)
            throws FakeClientException;
    
    /** Retourne les cartes bancaires associees a un compte bancaire.
     * @param client Proprietaire du compte.
     * @param account Compte bancaire cible par les cartes bancaires.
     * @return Liste des cartes bancaires.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant
     * @throws FakeAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte bancaire existant.
     * @throws AccountOwnerException Exception levee si le client n'est pas le 
     * proprietaire du compte. */
    List<BankCard> getBankCards(Client client, Account account)
            throws FakeClientException, FakeAccountException,
            AccountOwnerException;
    
    /** Retourne une liste des chequiers d'un statut specifique d'un client. Si 
     * le statut est null, tous les chequiers sont retournes.
     * @param client Proprietaire des chequiers.
     * @param status Statut des chequiers.
     * @return Liste des chequiers dont le statut correspond au statut specifie.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<Checkbook> getCheckBooks(Client client, Status status)
            throws FakeClientException;
    
    /** Retourne les chequiers associees a un compte bancaire.
     * @param client Proprietaire du compte.
     * @param account Compte bancaire cible par les chequiers.
     * @return Liste des chequiers.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant
     * @throws FakeAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte bancaire existant.
     * @throws AccountOwnerException Exception levee si le client n'est pas le 
     * proprietaire du compte. */
    List<Checkbook> getCheckBooks(Client client, Account account)
            throws FakeClientException, FakeAccountException,
            AccountOwnerException;
    
}
