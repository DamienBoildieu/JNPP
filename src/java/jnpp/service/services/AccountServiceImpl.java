package jnpp.service.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Resource;

import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.accounts.CloseRequestEntity;
import jnpp.dao.entities.accounts.Currency;
import jnpp.dao.entities.accounts.CurrentAccountEntity;
import jnpp.dao.entities.accounts.JointAccountEntity;
import jnpp.dao.entities.accounts.SavingAccountEntity;
import jnpp.dao.entities.accounts.SavingBookEntity;
import jnpp.dao.entities.accounts.ShareEntity;
import jnpp.dao.entities.accounts.ShareAccountEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.clients.Identity;
import jnpp.dao.entities.clients.PrivateEntity;
import jnpp.dao.entities.movements.MovementEntity;
import jnpp.dao.repositories.CloseRequestDAO;
import jnpp.service.exceptions.ClosureException;
import jnpp.service.exceptions.accounts.CloseRequestException;
import jnpp.service.exceptions.accounts.UnknownIdentityException;
import jnpp.service.exceptions.duplicates.DuplicateAccountException;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeSavingBookException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.owners.AccountOwnerException;

import org.springframework.stereotype.Service;
import jnpp.dao.repositories.AccountDAO;
import jnpp.dao.repositories.ClientDAO;
import jnpp.dao.repositories.SavingBookDAO;
import jnpp.dao.repositories.ShareDAO;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.dto.accounts.CurrentAccountDTO;
import jnpp.service.dto.accounts.JointAccountDTO;
import jnpp.service.dto.accounts.SavingAccountDTO;
import jnpp.service.dto.accounts.SavingBookDTO;
import jnpp.service.dto.accounts.ShareAccountDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.dto.movements.MovementDTO;

@Service("AccountService")
public class AccountServiceImpl implements AccountService {
    
    public static final int RIB_LENGTH = 8;
    private static final int RIB_MIN = (int) Math.pow(10, RIB_LENGTH - 1);
    private static final int RIB_RANGE = 9 * RIB_MIN;
    
    public static final Double DEFAULT_MONEY = 0.0;
    public static final Currency DEFAULT_CURRENCY = Currency.EURO;
    public static final Double DEFAULT_LIMIT = -50.0;
    
    @Resource
    ClientDAO clientDAO;
    @Resource
    AccountDAO accountDAO;
    @Resource
    SavingBookDAO savingBookDAO;
    @Resource
    CloseRequestDAO closeRequestDAO;
    @Resource
    ShareDAO shareDAO;
    
    private final Random random = new Random(); 
    
    @Override
    public List<SavingBookDTO> getSavingBooks() {
        List<SavingBookEntity> entities = savingBookDAO.findAll();
        List<SavingBookDTO> dtos = new ArrayList<SavingBookDTO>(entities.size());
        Iterator<SavingBookEntity> it = entities.iterator();
        while (it.hasNext()) dtos.add(new SavingBookDTO(it.next()));
        return dtos;
    }
    
    @Override
    public List<ShareDTO> getShares() {
        List<ShareEntity> entities = shareDAO.findAll();
        List<ShareDTO> dtos = new ArrayList<ShareDTO>(entities.size());
        Iterator<ShareEntity> it = entities.iterator();
        while (it.hasNext()) dtos.add(new ShareDTO(it.next()));
        return dtos;
    }
    
    @Override
    public List<AccountDTO> getAccounts(String login) throws FakeClientException {
        if (login == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        List<AccountEntity> entities = accountDAO.findAllByLogin(login);
        List<AccountDTO> dtos = new ArrayList<AccountDTO>(entities.size());
        Iterator<AccountEntity> it = entities.iterator();
        while (it.hasNext()) dtos.add(AccountDTO.newAccountDTO((it.next())));
        return dtos;
    }
    
    @Override
    public CurrentAccountDTO openCurrentAccount(String login) throws DuplicateAccountException, FakeClientException {
        if (login == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        if (accountDAO.hasCurrentAccount(login)) throw new DuplicateAccountException();
        String rib = generateNewRib();
        CurrentAccountEntity account = new CurrentAccountEntity(rib, client, DEFAULT_MONEY, DEFAULT_CURRENCY, DEFAULT_LIMIT);
        accountDAO.save(account);
        return new CurrentAccountDTO(account);
    }
    
    @Override
    public JointAccountDTO openJointAccount(String login, List<Identity> identities) throws FakeClientException, UnknownIdentityException {
        if (login == null || identities == null || identities.size() < 2) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        List<ClientEntity> clients = new ArrayList<ClientEntity>();
        boolean clientFound = false;
        Iterator<Identity> ite = identities.iterator();
        while (ite.hasNext()) {
            Identity identity = ite.next();
            PrivateEntity currentClient = clientDAO.findPrivate(identity);
            if (currentClient == null) throw new UnknownIdentityException();
            else clients.add(currentClient);
            if (!clientFound && currentClient.getLogin().equals(login)) clientFound = true;
        }
        if (!clientFound) throw new IllegalArgumentException("Le login ne fait pas reference a une identite contenue dans la liste.");
        String rib = generateNewRib();
        JointAccountEntity account = new JointAccountEntity(rib, clients, DEFAULT_MONEY, DEFAULT_CURRENCY);
        accountDAO.save(account);
        return new JointAccountDTO(account);
    }
    
    @Override
    public SavingAccountDTO openSavingAccount(String login, String name) throws FakeClientException, FakeSavingBookException, DuplicateAccountException {
        if (login == null || name == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        SavingBookEntity savingBook = savingBookDAO.findByName(name);
        if (savingBook == null) throw new FakeSavingBookException();
        if (accountDAO.hasSavingAccount(login, savingBook.getId())) throw new DuplicateAccountException();
        String rib = generateNewRib();
        SavingAccountEntity account = new SavingAccountEntity(rib, client, DEFAULT_MONEY, DEFAULT_CURRENCY, savingBook);
        accountDAO.save(account);
        return new SavingAccountDTO(account);
    }
    
    @Override
    public ShareAccountDTO openShareAccount(String login) throws FakeClientException, DuplicateAccountException {
        if (login == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        if (accountDAO.hasShareAccount(login)) throw new DuplicateAccountException();
        String rib = generateNewRib();
        ShareAccountEntity account = new ShareAccountEntity(rib, client);
        accountDAO.save(account);
        return new ShareAccountDTO(account);
    }
    
    @Override
    public void closeAccount(String login, String rib) throws FakeClientException, FakeAccountException, AccountOwnerException, ClosureException, CloseRequestException {
        if (login == null || rib == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        AccountEntity account = accountDAO.find(rib);
        if (account == null) throw new FakeAccountException();
        List<ClientEntity> clients = account.getClients();
        if (clients == null) throw new AccountOwnerException();
        boolean clientFound = false;
        Iterator<ClientEntity> itc = clients.iterator();
        while (itc.hasNext() && !clientFound) clientFound = client.equals(itc.next());
        if (!clientFound) throw new AccountOwnerException();
        switch (account.getType()) {
            case CURRENT:
                closeAccount((CurrentAccountEntity) account);
                break;
            case JOINT:
                switch (client.getType()) {
                    case PRIVATE:
                        closeAccount((JointAccountEntity) account, (PrivateEntity) client);
                        break;
                    case PROFESIONAL:
                        throw new IllegalArgumentException("Un professionel a ferme un compte joint.");
                }
                break;
            case SAVING:
                switch (client.getType()) {
                    case PRIVATE:
                        closeAccount((SavingAccountEntity) account);
                        break;
                    case PROFESIONAL:
                        throw new IllegalArgumentException("Un professionel a ferme un compte joint.");
                }
                break;
            case SHARE:
                closeAccount((ShareAccountEntity) account);
                break;
        }
    }
    
    private void closeAccount(CurrentAccountEntity account) throws ClosureException {
        if (account.getClients() != null && account.getClients().size() > 1) throw new IllegalArgumentException("Un compte courant a plusieurs proprietaires.");
        if (account.getMoney() != 0) throw new ClosureException();
        accountDAO.delete(account);
    }
    
    private void closeAccount(JointAccountEntity account, PrivateEntity client) throws ClosureException, CloseRequestException {
        if (account.getClients() != null && account.getClients().size() < 2) throw new IllegalArgumentException("Un compte joint n'a que un seul proprietaire.");

        if (account.getMoney() != 0) throw new ClosureException();
        
        List<ClientEntity> clients = account.getClients();
        List<CloseRequestEntity> requests = closeRequestDAO.findAllByRib(account.getRib());
        
        Map<String, Boolean> map = new HashMap<String, Boolean>();        
        Iterator<ClientEntity> itc = clients.iterator();
        while (itc.hasNext()) map.put(itc.next().getLogin(), false);
        
        Iterator<CloseRequestEntity> itr = requests.iterator();
        while (itr.hasNext()) {
            String login = itr.next().getClient().getLogin();
            if (map.get(login) == null)
                throw new IllegalStateException("Un client non proprietaire d'un compte joint a emis une requete de fermeture sur ce compte joint.");
            else map.put(login, true);
        }
        
        if (map.containsValue(false)) {
            if (!map.get(client.getLogin())) closeRequestDAO.save(new CloseRequestEntity(client, account));
            throw new CloseRequestException();
        } else {
            itr = requests.iterator();
            while (itr.hasNext()) closeRequestDAO.delete(itr.next());
            accountDAO.delete(account);
        }
    }
    
    private void closeAccount(SavingAccountEntity account) throws ClosureException {
        if (account.getClients() != null && account.getClients().size() > 1) throw new IllegalArgumentException("Un compte livret a plusieurs proprietaires.");
        if (account.getMoney() != 0)  throw new ClosureException();
        accountDAO.delete(account);
    }
    
    private void closeAccount(ShareAccountEntity account) throws ClosureException {
        if (account.getClients() != null && account.getClients().size() > 1) throw new IllegalArgumentException("Un compte d'actions a plusieurs proprietaires.");
        if (!account.getShareTitles().isEmpty()) throw new ClosureException();
        accountDAO.delete(account);
    }

    @Override
    public List<MovementDTO> getMovements(String login) throws FakeClientException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MovementDTO> getMovements(String login, int n) throws FakeClientException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MovementDTO> getMovements(String login, Date date) throws FakeClientException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MovementDTO> getMovements(String login, String rib) throws FakeAccountException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MovementDTO> getMovements(String login, String rib, int n) throws FakeAccountException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MovementDTO> getMovements(String login, String rib, Date date) throws FakeAccountException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
