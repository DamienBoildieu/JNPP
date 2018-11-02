package jnpp.service.services;

import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.accounts.ShareEntity;
import jnpp.dao.entities.accounts.ShareTitleEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.movements.DebitEntity;
import jnpp.dao.entities.movements.PurchaseEntity;
import jnpp.dao.entities.movements.SaleEntity;
import jnpp.dao.entities.movements.TransfertEntity;
import jnpp.service.exceptions.accounts.NoCurrentAccountException;
import jnpp.service.exceptions.accounts.NoShareAccountException;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeShareException;
import jnpp.service.exceptions.entities.FakeShareTitleException;
import jnpp.service.exceptions.movements.AccountTypeException;
import jnpp.service.exceptions.movements.AmountException;
import jnpp.service.exceptions.movements.DebitAuthorizationException;
import jnpp.service.exceptions.owners.AccountOwnerException;

/** Service de gestion des transactions.
 * @author Pierre Bourquat
 * @author Damien Boildieu */
public interface IMovementService extends IService {
    
    /** TransfertEntity de l'argent.
     * @param client ClientEntity effectuant le transfert.
     * @param account Compte origne du transfert.
     * @param rib Compte destination du transgert.
     * @param amount Quantite d'argent a transferer.
     * @return L'entite de transation du transfert.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws FakeAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte existant.
     * @throws AccountOwnerException Exception levee si le client n'est pas le 
     * proprietaire du compte.
     * @throws AccountTypeException Exception levee si le type de compte 
     * n'autorise pas cette transaction.
     * @throws AmountException Eception levee si la quantite d'argent a 
     * transferer n'est pas valide. */
    TransfertEntity transfertMoney(ClientEntity client, AccountEntity account, String rib, 
            double amount)
            throws FakeClientException, FakeAccountException, AccountOwnerException, 
            AccountTypeException, AmountException;
    
    /** Debite de l'argent. 
     * @param client ClientEntity effectuant le debit.
     * @param account Compte destination du debit.
     * @param rib Compte origine du debit.
     * @param amount Quantite d'argent a transferer.
     * @return L'entite de transation du debit.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws FakeAccountException Exception levee si l'entite account ne 
     * fait pas reference a un compte existant.
     * @throws AccountOwnerException Exception levee si le client n'est pas le 
     * proprietaire du compte.
     * @throws AccountTypeException Exception levee si le type de compte 
     * n'autorise pas cette transaction.
     * @throws DebitAuthorizationException Exception levee si le client n'est 
     * pas autorise a debiter le compte.
     * @throws AmountException Eception levee si la quantite d'argent a debiter
     * n'est pas valide. */
    DebitEntity debitMoney(ClientEntity client, AccountEntity account, String rib, double amount)
            throws FakeClientException, FakeAccountException, AccountOwnerException, 
            AccountTypeException, DebitAuthorizationException, AmountException; 
    
    /** Achete des titres d'actions. On suppose qu'il existe suffisement de 
     * titre a acheter. L'argent de l'achat est preleve sur le compte courant.
     * @param client ClientEntity achetant des actions.
     * @param share Action a acheter.
     * @param amount Quantite d'action a acheter.
     * @return L'entite de transation de l'achat.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws NoCurrentAccountException Exception levee si le client ne 
     * possede pas de compte courant.
     * @throws NoShareAccountException Exception levee si le client ne 
     * possede pas de compte d'actions.
     * @throws FakeShareException Exception levee si l'entite share ne fait pas
     * reference une action existante.
     * @throws AmountException  Exception levee si la quantite de titres 
     * d'actions a vendre est invalide. */
    PurchaseEntity purchaseShareTitles(ClientEntity client, ShareEntity share, 
            double amount)
            throws FakeClientException, NoCurrentAccountException,
            NoShareAccountException, FakeShareException, AmountException;
    
    /** Vend des titres d'actions. On suppose l'existence d'une entite qui
     * receptionne toutes les ventes d'actions. L'argent de la vente est 
     * transferee vers le compte courant du client.
     * @param client ClientEntity vendant des actions.
     * @param title Titre d'action a vendre.
     * @param amount Quantite de titre a d'action a vendre.
     * @return L'entite de transaction de la vente.
     * @throws FakeClientException Exception levee si l'entite client ne 
     * fait pas reference a un client existant.
     * @throws NoCurrentAccountException Exception levee si le client ne 
     * possede pas de compte courant.
     * @throws NoShareAccountException Exception levee si le client ne 
     * possede pas de compte d'actions.
     * @throws FakeShareTitleException Exception levee si l'entite title ne 
     * fait pas reference a un titre d'action existant.
     * @throws AccountOwnerException Exception levee si le client n'est pas le 
     * proprietaire des titres d'actions.
     * @throws AmountException Exception levee si la quantite de titres 
     * d'actions a vendre est invalide. */
    SaleEntity saleShareTitles(ClientEntity client, ShareTitleEntity title, int amount)
           throws FakeClientException, NoCurrentAccountException, 
           NoShareAccountException, FakeShareTitleException, 
           AccountOwnerException, AmountException;
    
}
