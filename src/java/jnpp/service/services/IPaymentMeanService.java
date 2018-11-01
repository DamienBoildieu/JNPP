package jnpp.service.services;

import java.util.List;

import jnpp.dao.entities.paymentmeans.BankCardEntity;
import jnpp.dao.entities.paymentmeans.CheckbookEntity;
import jnpp.dao.entities.paymentmeans.PaymentMeanEntity.Status;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.movements.AccountTypeException;
import jnpp.service.exceptions.owners.AccountOwnerException;

/** Service de gestion des moyens de paiements.
 * @author Pierre Bourquat
 * @author Damien Boildieu */
public interface IPaymentMeanService extends IService {
    
    /** Commande une carte bancaire pour un compte bancaire.
     * @param client ClientEntity proprietaire du compte bancaire.
     * @param account Compte bancaire cible de la carte bancaire.
     * @return Entite de la carte bancaire.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. 
     * @throws FakeAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte bancaire existant.
     * @throws AccountTypeException Exception levee si le compte bancaire ne 
     * peut pas etre associe a une carte bancaire. */
    BankCardEntity commandBankCard(ClientEntity client, AccountEntity account)
            throws FakeClientException, FakeAccountException,
            AccountTypeException;
    
    /** Commande un chequier pour un compte bancaire.
     * @param client ClientEntity proprietaire du compte bancaire.
     * @param account Compte bancaire cible du chequier.
     * @return Entite du chequier.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. 
     * @throws FakeAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte bancaire existant.
     * @throws AccountTypeException Exception levee si le compte bancaire ne 
     * peut pas etre associe a un chequier. */
    CheckbookEntity commandCheckbook(ClientEntity client, AccountEntity account)
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
    List<BankCardEntity> getBankCards(ClientEntity client, Status status)
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
    List<BankCardEntity> getBankCards(ClientEntity client, AccountEntity account)
            throws FakeClientException, FakeAccountException,
            AccountOwnerException;
    
    /** Retourne une liste des chequiers d'un statut specifique d'un client. Si 
     * le statut est null, tous les chequiers sont retournes.
     * @param client Proprietaire des chequiers.
     * @param status Statut des chequiers.
     * @return Liste des chequiers dont le statut correspond au statut specifie.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<CheckbookEntity> getCheckBooks(ClientEntity client, Status status)
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
    List<CheckbookEntity> getCheckBooks(ClientEntity client, AccountEntity account)
            throws FakeClientException, FakeAccountException,
            AccountOwnerException;
    
}
