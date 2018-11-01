package jnpp.service.services;

import java.util.List;

import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.accounts.DebitAuthorizationEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.service.exceptions.duplicates.DuplicateDebitAuthorizationException;
import jnpp.service.exceptions.owners.AccountOwnerException;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeDebitAuthorizationException;

/** Service de gestion des autorisations de prelevements.
 * @author Pierre Bourquat
 * @author Damien Boildieu */
public interface IDebitAuthorizationService extends IService {
        
    /** Authorise un compte represente par son rib a effectuer des prelevements 
     * sur un compte bancaire d'un client.
     * @param client ClientEntity generant l'autorisation.
     * @param account Compte autorise a etre debite. 
     * @param ribTo Rib du compte autorise a debiter.
     * @return L'entite de l'autorisation.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws FakeAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte existant.
     * @throws AccountOwnerException Exception levee si le client n'est pas le 
     * proprietaire du compte.
     * @throws DuplicateDebitAuthorizationException Exception levee si une 
     * autorisation identique existe deja. */
    DebitAuthorizationEntity authorizeDebit(ClientEntity client, AccountEntity account, 
            String ribTo)
            throws FakeClientException, FakeAccountException, AccountOwnerException,
            DuplicateDebitAuthorizationException;
    
    /** Supprime une autorisation de prelevement. 
     * @param client ClientEntity supprimant l'autorisation de prelevement sur son
 compte bancaore.
     * @param authorization Entite de l'autorisation.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws FakeDebitAuthorizationException Exception levee si l'entite 
     * authorization ne fait pas reference a une autorisation existante.
     * @throws AccountOwnerException Exception levee si le client n'est pas le 
     * proprietaire du compte. */
    void deleteAuthorization(ClientEntity client, DebitAuthorizationEntity authorization)
            throws FakeClientException, FakeDebitAuthorizationException,
            AccountOwnerException;            
    
    /** Retourne une liste des autorisations de prelevements sur les comptes 
     * d'un client. 
     * @param client ClientEntity proprietaire des autorisations.
     * @return List d'autorisation.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.*/
    List<DebitAuthorizationEntity> getDebitAuthorizations(ClientEntity client)
            throws FakeClientException;
    
    /** Retourne une liste des autorisations de prelevements sur un compte d'un 
     * client. 
     * @param client ClientEntity proprietaire des autorisations.
     * @param account Compte bancaire.
     * @return List d'autorisation.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws FakeAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte existant.
     * @throws AccountOwnerException Exception levee si le client n'est pas le 
     * proprietaire du compte. */
    List<DebitAuthorizationEntity> getDebitAuthorizations(ClientEntity client, 
            AccountEntity account)
            throws FakeClientException, FakeAccountException, AccountOwnerException;   
    
    /** Retourne une liste des ribs des comptes sur lesquels un client est 
     * autorise a effectuer des prelevements. 
     * @param client ClientEntity autorise.
     * @return Liste  de ribs.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    List<String> getAuthorizedAccountsToDebit(ClientEntity client)
            throws FakeClientException;
    
    /** Indique si un client est autorise a effectuer un prelevement sur un 
     * compte.
     * @param client ClientEntity concerne.
     * @param rib Rib tu compte concerne.
     * @return True si le client peut effectuer des prelevements sur le compte,
     * false sinon.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant. */
    boolean isAuthorizedToDebit(ClientEntity client, String rib)
            throws FakeClientException;
    
}
