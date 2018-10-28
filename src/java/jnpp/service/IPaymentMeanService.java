package jnpp.service;

import java.util.List;

import jnpp.dao.entities.BankCard;
import jnpp.dao.entities.Checkbook;
import jnpp.dao.entities.PaymentMean.Status;
import jnpp.dao.entities.accounts.Account;
import jnpp.dao.entities.clients.Client;
import jnpp.service.exceptions.WrongAccountException;
import jnpp.service.exceptions.entities.UnknownAccountException;
import jnpp.service.exceptions.entities.UnknownClientException;

/** Service de gestion des moyens de paiements.
 * @author Pierre Bourquat
 * @author Damien Boildieu */
public interface IPaymentMeanService {
    
    /** Commande une carte bancaire pour un compte bancaire.
     * @param client Client proprietaire du compte bancaire.
     * @param account Compte bancaire cible de la carte bancaire.
     * @return Entite de la carte bancaire.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. 
     * @throws UnknownAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte bancaire existant.
     * @throws WrongAccountException Exception levee si le compte bancaire ne 
     * peut pas etre associe a une carte bancaire. */
    public BankCard commandBankCard(Client client, Account account)
            throws UnknownClientException, UnknownAccountException,
            WrongAccountException;
    
    /** Commande un chequier pour un compte bancaire.
     * @param client Client proprietaire du compte bancaire.
     * @param account Compte bancaire cible du chequier.
     * @return Entite du chequier.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. 
     * @throws UnknownAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte bancaire existant.
     * @throws WrongAccountException Exception levee si le compte bancaire ne 
     * peut pas etre associe a un chequier. */
    public Checkbook commandCheckbook(Client client, Account account)
            throws UnknownClientException, UnknownAccountException,
            WrongAccountException;
    
    /** Retourne une liste des cartes bancaires d'un statut specifique d'un 
     * client. Si le statut est null, toutes les cartes bancaires sont 
     * retournees.
     * @param client Proprietaire des cartes bancaires.
     * @param status Statut des cartes bancaires.
     * @return Liste des cartes bancaires dont le statut correspond au statut
     * specifie.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public List<BankCard> getBankCards(Client client, Status status)
            throws UnknownClientException;
    
    /** Retourne les cartes bancaires associees a un compte bancaire.
     * @param account Compte bancaire cible par les cartes bancaires.
     * @return Liste des cartes bancaires.
     * @throws UnknownAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte bancaire existant. */
    public List<BankCard> getBankCards(Account account)
            throws UnknownAccountException;
    
    /** Retourne une liste des chequiers d'un statut specifique d'un client. Si 
     * le statut est null, tous les chequiers sont retournes.
     * @param client Proprietaire des chequiers.
     * @param status Statut des chequiers.
     * @return Liste des chequiers dont le statut correspond au statut specifie.
     * @throws UnknownClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    public List<Checkbook> getCheckBooks(Client client, Status status)
            throws UnknownClientException;
    
    /** Retourne les chequiers associees a un compte bancaire.
     * @param account Compte bancaire cible par les chequiers.
     * @return Liste des chequiers.
     * @throws UnknownAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte bancaire existant. */
    public List<Checkbook> getCheckBooks(Account account)
            throws UnknownAccountException;
    
}
