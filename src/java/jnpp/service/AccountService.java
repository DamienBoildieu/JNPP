package jnpp.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Resource;

import jnpp.dao.entities.accounts.Account;
import jnpp.dao.entities.accounts.Currency;
import jnpp.dao.entities.accounts.CurrentAccount;
import jnpp.dao.entities.accounts.JointAccount;
import jnpp.dao.entities.accounts.SavingAccount;
import jnpp.dao.entities.accounts.SavingBook;
import jnpp.dao.entities.accounts.Share;
import jnpp.dao.entities.accounts.ShareAccount;
import jnpp.dao.entities.clients.Client;
import jnpp.dao.entities.clients.Identity;
import jnpp.dao.entities.clients.Private;
import jnpp.dao.entities.movements.Movement;
import jnpp.dao.repositories.IAccountDAO;
import jnpp.dao.repositories.IClientDAO;
import jnpp.dao.repositories.ISavingBookDAO;
import jnpp.service.exceptions.ClosureException;
import jnpp.service.exceptions.accounts.ClosureRequestException;
import jnpp.service.exceptions.accounts.UnknownIdentityException;
import jnpp.service.exceptions.duplicates.DuplicateAccountException;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeBookException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.owners.AccountOwnerException;

import org.springframework.stereotype.Service;

@Service("AccountService")
public class AccountService implements IAccountService {
    
    public static final int RIB_LENGTH = 8;
    private static final int RIB_MIN = (int) Math.pow(10, RIB_LENGTH - 1);
    private static final int RIB_RANGE = 9 * RIB_MIN;
    
    public static final Double DEFAULT_MONEY = 0.0;
    public static final Currency DEFAULT_CURRENCY = Currency.EURO;
    public static final Double DEFAULT_LIMIT = -50.0;
    
    @Resource
    IClientDAO clientDAO;
    @Resource
    IAccountDAO accountDAO;
    @Resource
    ISavingBookDAO savingBookDAO;
    
    private final Random random = new Random();
    
    @Override
    public List<SavingBook> getSavingBooks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Share> getShares() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Account> getAccounts(Client client) throws FakeClientException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CurrentAccount openCurrentAccount(Client client) throws FakeClientException, DuplicateAccountException {
        if (client == null) throw new IllegalArgumentException();
        checkFake(client);
        if (accountDAO.hasCurrentAccount(client)) throw new DuplicateAccountException();
        String rib = generateNewRib();
        CurrentAccount account = new CurrentAccount(rib, client, DEFAULT_MONEY, DEFAULT_CURRENCY, DEFAULT_LIMIT);
        account = (CurrentAccount) accountDAO.save(account);
        return account;
    }

    @Override
    public void closeCurrentAccount(Client client, CurrentAccount account) throws FakeClientException, FakeAccountException, ClosureException, AccountOwnerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JointAccount openJointAccount(Private client, List<Identity> identities) throws FakeClientException, UnknownIdentityException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void closeJointAccount(Private client, JointAccount account) throws FakeClientException, FakeAccountException, ClosureException, ClosureRequestException, AccountOwnerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SavingAccount openSavingAccount(Private client, SavingBook book) throws FakeClientException, FakeBookException, DuplicateAccountException {
        if (client == null) throw new IllegalArgumentException();
        checkFake(client);
        checkFake(book);
        if (accountDAO.hasBookAccount(client, book)) throw new DuplicateAccountException();
        String rib = generateNewRib();
        SavingAccount account = new SavingAccount(rib, client, DEFAULT_MONEY, DEFAULT_CURRENCY);
        account = (SavingAccount) accountDAO.save(account);
        return account;
    }

    @Override
    public void closeSavingAccount(Private client, SavingAccount account) throws FakeClientException, FakeAccountException, ClosureException, AccountOwnerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ShareAccount openShareAccount(Client client) throws FakeClientException, DuplicateAccountException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void closeShareAccount(Client client, ShareAccount account) throws FakeClientException, FakeAccountException, ClosureException, AccountOwnerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Movement> getMovements(Client client) throws FakeClientException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Movement> getMovements(Client client, int n) throws FakeClientException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Movement> getMovements(Client client, Date date) throws FakeClientException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Movement> getMovements(Account account) throws FakeAccountException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Movement> getMovements(Account account, int n) throws FakeAccountException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Movement> getMovements(Account account, Date date) throws FakeAccountException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void checkFake(Client client) throws FakeClientException {
        if (CHECK_FAKE_ENTITY && clientDAO.isFake(client)) 
            throw new FakeClientException();
    }
    
    private void checkFake(SavingBook book) throws FakeClientException {
        if (CHECK_FAKE_ENTITY && savingBookDAO.isFake(book)) 
            throw new FakeClientException();
    }
    
    private String generateNewRib() {
        Set<String> ribs = new HashSet<String>(accountDAO.findAllRib());
        String rib = generateRandomRib();
        while (ribs.contains(rib)) rib = generateRandomRib();
        return rib;
    }
    
    private String generateRandomRib() {
        return "" + (RIB_MIN + random.nextInt(RIB_RANGE));
    }
    
}
