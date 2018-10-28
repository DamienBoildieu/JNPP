package jnpp.service;

import java.util.List;

import jnpp.dao.entities.accounts.Account;
import jnpp.dao.entities.accounts.DebitAuthorization;
import jnpp.dao.entities.clients.Client;
import jnpp.service.exceptions.accounts.DuplicatedAuthorizationException;
import jnpp.service.exceptions.accounts.OwnerException;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeDebitAuthorizationException;

/** Service de gestion des autorisations de prelevements.
 * @author Pierre Bourquat
 * @author Damien Boildieu */
public interface IDebitAuthorizationService extends IService {
        
    /** Authorise un compte represente par son rib a effectuer des prelevements 
     * sur un compte bancaire d'un client.
     * @param client Client generant l'autorisation.
     * @param account Compte autorise a etre debite. 
     * @param ribTo Rib du compte autorise a debiter.
     * @return L'entite de l'autorisation.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws FakeAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte existant.
     * @throws OwnerException Exception levee si le client n'est pas le 
     * proprietaire du compte.
     * @throws DuplicatedAuthorizationException Exception levee si une 
     * autorisation identique existe deja. */
    DebitAuthorization authorizeDebit(Client client, Account account, 
            String ribTo)
            throws FakeClientException, FakeAccountException, OwnerException,
            DuplicatedAuthorizationException;
    
    /** Supprime une autorisation de prelevement. 
     * @param client Client supprimant l'autorisation de prelevement sur son
     * compte bancaore.
     * @param authorization Entite de l'autorisation.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws FakeDebitAuthorizationException Exception levee si l'entite 
     * authorization ne fait pas reference a une autorisation existante.
     * @throws OwnerException Exception levee si le client n'est pas le 
     * proprietaire du compte. */
    void deleteAuthorization(Client client, DebitAuthorization authorization)
            throws FakeClientException, FakeDebitAuthorizationException,
            OwnerException;            
    
    /** Retourne une liste des autorisations de prelevements sur les comptes 
     * d'un client. 
     * @param client Client proprietaire des autorisations.
     * @return List d'autorisation.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.*/
    List<DebitAuthorization> getDebitAuthorizations(Client client)
            throws FakeClientException;
    
    /** Retourne une liste des autorisations de prelevements sur un compte d'un 
     * client. 
     * @param client Client proprietaire des autorisations.
     * @param account Compte bancaire.
     * @return List d'autorisation.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws FakeAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte existant.
     * @throws OwnerException Exception levee si le client n'est pas le 
     * proprietaire du compte. */
    List<DebitAuthorization> getDebitAuthorizations(Client client, 
            Account account)
            throws FakeClientException, FakeAccountException, OwnerException;   
    
    /** Retourne une liste des ribs des comptes sur lesquels un client est 
     * autorise a effectuer des prelevements. 
     * @param client Client autorise.
     * @return Liste  de ribs.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<String> getDebitAuthorizedRibs(Client client)
            throws FakeClientException;
    
    /** Indique si un client est autorise a effectuer un prelevement sur un 
     * compte.
     * @param client Client concerne.
     * @param rib Rib tu compte concerne.
     * @return True si le client peut effectuer des prelevements sur le compte,
     * false sinon.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    boolean isAuthorizedToDebit(Client client, String rib)
            throws FakeClientException;
    
}
