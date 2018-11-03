package jnpp.service.services;

import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.accounts.CurrencyEntity;
import jnpp.dao.entities.accounts.MoneyAccountEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.movements.DebitEntity;
import jnpp.dao.entities.movements.MovementEntity;
import jnpp.dao.entities.movements.TransfertEntity;
import jnpp.dao.entities.notifications.MovementNotificationEntity;
import jnpp.dao.entities.notifications.OverdraftNotificationEntity;
import jnpp.dao.repositories.AccountDAO;
import jnpp.dao.repositories.ClientDAO;
import jnpp.dao.repositories.MovementDAO;
import jnpp.dao.repositories.NotificationDAO;
import jnpp.service.dto.accounts.CurrencyDTO;
import jnpp.service.dto.movements.DebitDTO;
import jnpp.service.dto.movements.PurchaseDTO;
import jnpp.service.dto.movements.SaleDTO;
import jnpp.service.dto.movements.TransfertDTO;
import jnpp.service.dto.notifications.OverdraftNotificationDTO;
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
import org.springframework.stereotype.Service;

@Service("MovementService")
public class MovementServiceImpl implements MovementService {

    @Resource
    ClientDAO clientDAO;
    @Resource
    AccountDAO accountDAO;
    @Resource
    MovementDAO movementDAO;
    @Resource
    NotificationDAO notificationDAO;
    
    @Override
    public TransfertDTO transfertMoney(String login, String ribFrom, String ribTo, Double amount, CurrencyDTO currency) throws FakeClientException, FakeAccountException, AccountOwnerException, AccountTypeException, CurrencyException {
        if (login == null || ribFrom == null || ribTo == null || amount == null 
                || amount <= 0|| currency == null) throw new IllegalArgumentException();
        
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        
        AccountEntity accountFrom = accountDAO.find(ribFrom);
        if (accountFrom == null || !accountFrom.isOwnBy(client)) throw new AccountOwnerException();
        if (!accountFrom.canEmit(MovementEntity.Type.TRANSFERT)) throw new AccountTypeException();
                
        if (!(accountFrom instanceof MoneyAccountEntity)) 
            throw new IllegalStateException("Transfert d'un compte qui ne derive pas de MoneyAccount.");
        MoneyAccountEntity moneyAccountFrom = (MoneyAccountEntity) accountFrom;
        
        AccountEntity accountTo = accountDAO.find(ribTo);
        if (accountTo != null && accountTo.canReceive(MovementEntity.Type.TRANSFERT)) throw new AccountTypeException();
        
        CurrencyEntity currencyFrom = CurrencyEntity.toEntity(currency);
        if (moneyAccountFrom.getCurrency() != currencyFrom) throw new CurrencyException();
          
        Date now = Date.from(Instant.now());
        
        moneyAccountFrom.setMoney(moneyAccountFrom.getMoney() - amount);
        accountDAO.save(moneyAccountFrom);
        
        if (accountTo != null) {
            
            if (!(accountTo instanceof MoneyAccountEntity)) 
                throw new IllegalStateException("Transfert vers un compte qui ne derive pas de MoneyAccount.");
        
            MoneyAccountEntity moneyAccountTo = (MoneyAccountEntity) accountTo;
            
            CurrencyEntity currencyTo = moneyAccountTo.getCurrency();
            moneyAccountTo.setMoney(moneyAccountTo.getMoney() + currencyTo.convert(amount, currencyFrom));
            accountDAO.save(moneyAccountTo);
        }
        
        TransfertEntity transfert = new TransfertEntity(now, ribFrom, ribTo, amount, currencyFrom);
        movementDAO.save(transfert);        
        
        boolean isOverdraft = moneyAccountFrom.getMoney() < 0;
        
        List<ClientEntity> clientFroms = accountFrom.getClients();
        Iterator<ClientEntity> it = clientFroms.iterator();        
        while (it.hasNext()) {
        
            ClientEntity clientFrom = it.next();
            
            if (clientFrom.getNotify()) {
                
                MovementNotificationEntity movementNotification = 
                        new MovementNotificationEntity(clientFrom, now, false, transfert);
                notificationDAO.save(movementNotification);
                
                if (isOverdraft) {
                    
                    OverdraftNotificationEntity overdraftNotification =
                            new OverdraftNotificationEntity(clientFrom, now, false, accountFrom);
                    notificationDAO.save(overdraftNotification);
                }
            }
        }
        
        if (accountTo != null) {
                        
            List<ClientEntity> clientTos = accountTo.getClients();
            it = clientTos.iterator();        
            while (it.hasNext()) {
                
                ClientEntity clientTo = it.next();
                
                if (clientTo.getNotify()) {
                    
                    MovementNotificationEntity movementNotification = 
                            new MovementNotificationEntity(clientTo, now, false, transfert);
                    notificationDAO.save(movementNotification);
                }
            }
        }
        
        return transfert.toDTO();        
    }

    @Override
    public DebitDTO debitMoney(String login, String ribFrom, String ribTo, Double amount, CurrencyDTO currency) throws FakeClientException, FakeAccountException, AccountOwnerException, AccountTypeException, DebitAuthorizationException, CurrencyException {

        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        
        AccountEntity accountFrom = accountDAO.find(ribFrom);
        if (accountFrom == null || !accountFrom.isOwnBy(client)) throw new AccountOwnerException();
        if (!accountFrom.canEmit(MovementEntity.Type.DEBIT)) throw new AccountTypeException();
                
        if (!(accountFrom instanceof MoneyAccountEntity)) 
            throw new IllegalStateException("Debut vers un compte qui ne derive pas de MoneyAccount.");
        MoneyAccountEntity moneyAccountFrom = (MoneyAccountEntity) accountFrom;
        
        AccountEntity accountTo = accountDAO.find(ribTo);
        if (accountTo != null && accountTo.canReceive(MovementEntity.Type.DEBIT)) throw new AccountTypeException();

        CurrencyEntity currencyFrom = CurrencyEntity.toEntity(currency);
        if (moneyAccountFrom.getCurrency() != currencyFrom) throw new CurrencyException();
          
        Date now = Date.from(Instant.now());
        
        moneyAccountFrom.setMoney(moneyAccountFrom.getMoney() + amount);
        accountDAO.save(moneyAccountFrom);
        
        if (accountTo != null) {
            
            if (!(accountTo instanceof MoneyAccountEntity)) 
                throw new IllegalStateException("Debit d'un compte qui ne derive pas de MoneyAccount.");
        
            MoneyAccountEntity moneyAccountTo = (MoneyAccountEntity) accountTo;
            
            CurrencyEntity currencyTo = moneyAccountTo.getCurrency();
            moneyAccountTo.setMoney(moneyAccountTo.getMoney() + currencyTo.convert(amount, currencyFrom));
            accountDAO.save(moneyAccountTo);
        }
        
        DebitEntity debit = new DebitEntity(now, ribFrom, ribTo, amount, currencyFrom);
        movementDAO.save(debit);

        List<ClientEntity> clientFroms = accountFrom.getClients();
        Iterator<ClientEntity> it = clientFroms.iterator();        
        while (it.hasNext()) {
        
            ClientEntity clientFrom = it.next();
            
            if (clientFrom.getNotify()) {
                
                MovementNotificationEntity movementNotification = 
                        new MovementNotificationEntity(clientFrom, now, false, debit);
                notificationDAO.save(movementNotification);
            }
        }
        
        if (accountTo != null) {
            
            boolean isOverdraft = ((MoneyAccountEntity) accountTo).getMoney() < 0;
            
            List<ClientEntity> clientTos = accountTo.getClients();
            it = clientTos.iterator();        
            while (it.hasNext()) {
                
                ClientEntity clientTo = it.next();
                
                if (clientTo.getNotify()) {
                    
                    MovementNotificationEntity movementNotification = 
                            new MovementNotificationEntity(clientTo, now, false, debit);
                    notificationDAO.save(movementNotification);
                    
                    if (isOverdraft) {
                     
                        OverdraftNotificationEntity overdraftNotification =
                            new OverdraftNotificationEntity(clientTo, now, false, accountTo);
                        notificationDAO.save(overdraftNotification);
                    }
                }
            }            
        }
        
        return debit.toDTO();
    }

    @Override
    public PurchaseDTO purchaseShareTitles(String login, String name, Integer amount) throws FakeClientException, NoCurrentAccountException, NoShareAccountException, FakeShareException, AmountException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SaleDTO saleShareTitles(String login, String name, Integer amount) throws FakeClientException, NoCurrentAccountException, NoShareAccountException, FakeShareTitleException, AccountOwnerException, AmountException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
