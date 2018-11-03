package jnpp.service.services;

import jnpp.dao.entities.movements.MovementEntity;
import jnpp.dao.repositories.GenericDAO;
import jnpp.service.dto.accounts.CurrencyDTO;
import jnpp.service.dto.movements.DebitDTO;
import jnpp.service.dto.movements.PurchaseDTO;
import jnpp.service.dto.movements.SaleDTO;
import jnpp.service.dto.movements.TransfertDTO;
import jnpp.service.exceptions.accounts.CurrencyException;
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

public interface MovementService {

    TransfertDTO transfertMoney(String login, String ribFrom, String ribTo, 
            Double amount, CurrencyDTO currency)
            throws FakeClientException, FakeAccountException, AccountOwnerException, 
            AccountTypeException, CurrencyException;
    
    DebitDTO debitMoney(String login, String ribFrom, String ribTo, 
            Double amount, CurrencyDTO currency)
            throws FakeClientException, FakeAccountException, AccountOwnerException, 
            AccountTypeException, DebitAuthorizationException, CurrencyException; 
    
    PurchaseDTO purchaseShareTitles(String login, String name, 
            Integer amount)
            throws FakeClientException, NoCurrentAccountException,
            NoShareAccountException, FakeShareException, AmountException;
    
    SaleDTO saleShareTitles(String login, String name, 
           Integer amount)
           throws FakeClientException, NoCurrentAccountException, 
           NoShareAccountException, FakeShareTitleException, AmountException;
    
}
