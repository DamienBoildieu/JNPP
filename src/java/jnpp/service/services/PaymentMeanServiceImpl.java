package jnpp.service.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.paymentmeans.BankCardEntity;
import jnpp.dao.entities.paymentmeans.CheckbookEntity;
import jnpp.dao.entities.paymentmeans.PaymentMeanEntity;
import jnpp.dao.repositories.AccountDAO;
import jnpp.dao.repositories.ClientDAO;
import jnpp.dao.repositories.PaymentMeanDAO;
import jnpp.service.dto.paymentmeans.BankCardDTO;
import jnpp.service.dto.paymentmeans.CheckbookDTO;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.movements.AccountTypeException;
import jnpp.service.exceptions.owners.AccountOwnerException;
import org.springframework.stereotype.Service;

@Service("PaymentMeanService")
public class PaymentMeanServiceImpl implements PaymentMeanService {

    @Resource
    ClientDAO clientDAO;
    @Resource
    AccountDAO accountDAO;
    @Resource
    PaymentMeanDAO paymentMeanDAO;
    
    @Override
    public BankCardDTO commandBankCard(String login, String rib) throws FakeClientException, FakeAccountException, AccountTypeException, AccountOwnerException {
        if (login == null || rib == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        AccountEntity account = accountDAO.find(rib);
        if (account == null) throw new FakeAccountException();
        if (!account.isOwnBy(client)) throw new AccountOwnerException();
        if (!canBindToBanckCard(account)) throw new AccountTypeException();
        BankCardEntity bankcard = new BankCardEntity(client, account, PaymentMeanEntity.Status.ORDERED);
        paymentMeanDAO.save(bankcard);
        return bankcard.toDTO();
    }

    @Override
    public CheckbookDTO commandCheckbook(String login, String rib) throws FakeClientException, FakeAccountException, AccountTypeException, AccountOwnerException {
        if (login == null || rib == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        AccountEntity account = accountDAO.find(rib);
        if (account == null) throw new FakeAccountException();
        if (!account.isOwnBy(client)) throw new AccountOwnerException();
        if (!canBindToCheckbook(account)) throw new AccountTypeException();
        CheckbookEntity checkbook = new CheckbookEntity(client, account, PaymentMeanEntity.Status.ORDERED);
        paymentMeanDAO.save(checkbook);
        return checkbook.toDTO();
    }

    @Override
    public List<BankCardDTO> getBankCards(String login) throws FakeClientException {
        if (login == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();    
        List<BankCardEntity> bankcards = paymentMeanDAO.findBankCardByLogin(login);
        return BankCardEntity.toDTO(bankcards);
    }

    @Override
    public List<BankCardDTO> getBankCards(String login, PaymentMeanEntity.Status status) throws FakeClientException {
        if (login == null || status == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();    
        List<BankCardEntity> bankcards = paymentMeanDAO.findBankCardByLoginStatus(login, status);
        return BankCardEntity.toDTO(bankcards);
    }

    @Override
    public List<BankCardDTO> getBankCards(String login, String rib) throws FakeClientException, FakeAccountException, AccountOwnerException {
        if (login == null || rib == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        AccountEntity account = accountDAO.find(rib);
        if (account == null) throw new FakeAccountException();
        if (!account.isOwnBy(client)) throw new AccountOwnerException();    
        List<BankCardEntity> bankcards = paymentMeanDAO.findBankCardByLoginRib(login, rib);
        return BankCardEntity.toDTO(bankcards); 
    }

    @Override
    public List<CheckbookDTO> getCheckBooks(String login) throws FakeClientException {
        if (login == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();    
        List<CheckbookEntity> checkbooks = paymentMeanDAO.findCheckBookByLogin(login);
        return CheckbookEntity.toDTO(checkbooks);    
    }

    @Override
    public List<CheckbookDTO> getCheckBooks(String login, PaymentMeanEntity.Status status) throws FakeClientException {
        if (login == null || status == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();    
        List<CheckbookEntity> checkbooks = paymentMeanDAO.findCheckBookByLoginStatus(login, status);
        return CheckbookEntity.toDTO(checkbooks);  
    }

    @Override
    public List<CheckbookDTO> getCheckBooks(String login, String rib) throws FakeClientException, FakeAccountException, AccountOwnerException {
        if (login == null || rib == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        AccountEntity account = accountDAO.find(rib);
        if (account == null) throw new FakeAccountException();
        if (!account.isOwnBy(client)) throw new AccountOwnerException();    
        List<CheckbookEntity> checkbooks = paymentMeanDAO.findCheckBookByLoginRib(login, rib);
        return CheckbookEntity.toDTO(checkbooks); 
    }
    
    private static boolean canBindToBanckCard(AccountEntity account) {
        switch (account.getType()) {
            case CURRENT:
            case JOINT:
                return true;
            case SAVING:
            case SHARE:
                return false;
        }
        return false;
    }
    
    private static boolean canBindToCheckbook(AccountEntity account) {
        switch (account.getType()) {
            case CURRENT:
            case JOINT:
                return true;
            case SAVING:
            case SHARE:
                return false;
        }
        return false;
    }
    
}
