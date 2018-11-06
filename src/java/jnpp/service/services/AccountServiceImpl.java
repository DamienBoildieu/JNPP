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
import jnpp.dao.entities.IdentityEntity;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.accounts.CloseRequestEntity;
import jnpp.dao.entities.accounts.CurrencyEntity;
import jnpp.dao.entities.accounts.CurrentAccountEntity;
import jnpp.dao.entities.accounts.JointAccountEntity;
import jnpp.dao.entities.accounts.SavingAccountEntity;
import jnpp.dao.entities.accounts.SavingBookEntity;
import jnpp.dao.entities.accounts.ShareAccountEntity;
import jnpp.dao.entities.accounts.ShareEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.clients.PrivateEntity;
import jnpp.dao.entities.movements.MovementEntity;
import jnpp.dao.repositories.AccountDAO;
import jnpp.dao.repositories.ClientDAO;
import jnpp.dao.repositories.CloseRequestDAO;
import jnpp.dao.repositories.MovementDAO;
import jnpp.dao.repositories.SavingBookDAO;
import jnpp.dao.repositories.ShareDAO;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.dto.accounts.CurrentAccountDTO;
import jnpp.service.dto.accounts.JointAccountDTO;
import jnpp.service.dto.accounts.SavingAccountDTO;
import jnpp.service.dto.accounts.SavingBookDTO;
import jnpp.service.dto.accounts.ShareAccountDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.dto.movements.MovementDTO;
import jnpp.service.exceptions.ClosureException;
import jnpp.service.exceptions.accounts.ClientTypeException;
import jnpp.service.exceptions.accounts.CloseRequestException;
import jnpp.service.exceptions.accounts.UnknownIdentityException;
import jnpp.service.exceptions.duplicates.DuplicateAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeSavingBookException;
import jnpp.service.exceptions.owners.AccountOwnerException;
import org.springframework.stereotype.Service;

@Service("AccountService")
public class AccountServiceImpl implements AccountService {

    public static final int RIB_LENGTH = 8;
    private static final int RIB_MIN = (int) Math.pow(10, RIB_LENGTH - 1);
    private static final int RIB_RANGE = 9 * RIB_MIN;

    public static final Double DEFAULT_MONEY = 0.0;
    public static final CurrencyEntity DEFAULT_CURRENCY = CurrencyEntity.EURO;
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
    @Resource
    MovementDAO movementDAO;

    private final Random random = new Random();

    @Override
    public List<SavingBookDTO> getSavingBooks() {
        List<SavingBookEntity> savingBooks = savingBookDAO.findAll();
        return SavingBookEntity.toDTO(savingBooks);
    }

    @Override
    public List<ShareDTO> getShares() {
        List<ShareEntity> shares = shareDAO.findAll();
        return ShareEntity.toDTO(shares);
    }

    @Override
    public List<AccountDTO> getAccounts(String login) throws FakeClientException {
        if (login == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        List<AccountEntity> accounts = accountDAO.findAllByLogin(login);
        return AccountEntity.toDTO(accounts);
    }

    @Override
    public CurrentAccountDTO openCurrentAccount(String login) throws DuplicateAccountException, FakeClientException {
        if (login == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        if (accountDAO.hasCurrentAccount(login)) {
            throw new DuplicateAccountException();
        }
        String rib = generateNewRib();
        CurrentAccountEntity account = new CurrentAccountEntity(rib, client, DEFAULT_MONEY, DEFAULT_CURRENCY, DEFAULT_LIMIT);
        account = (CurrentAccountEntity) accountDAO.save(account);
        return account.toDTO();
    }

    @Override
    public JointAccountDTO openJointAccount(String login, List<IdentityDTO> identities) throws FakeClientException, UnknownIdentityException, ClientTypeException {
        if (login == null || identities == null || identities.size() < 2) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        if (!client.canOpen(AccountEntity.Type.JOINT)) {
            throw new ClientTypeException();
        }
        List<ClientEntity> clients = new ArrayList<ClientEntity>();
        boolean clientFound = false;
        Iterator<IdentityDTO> ite = identities.iterator();
        while (ite.hasNext()) {
            IdentityEntity identity = IdentityEntity.toEntity(ite.next());
            PrivateEntity currentClient = clientDAO.findPrivateByIdentity(identity.getGender(), identity.getFirstname(), identity.getLastname());
            if (currentClient == null) {
                throw new UnknownIdentityException();
            } else {
                clients.add(currentClient);
            }
            if (!clientFound && currentClient.getLogin().equals(login)) {
                clientFound = true;
            }
        }
        if (!clientFound) {
            throw new IllegalStateException("Le login ne fait pas reference a une identite contenue dans la liste.");
        }
        String rib = generateNewRib();
        JointAccountEntity account = new JointAccountEntity(rib, clients, DEFAULT_MONEY, DEFAULT_CURRENCY);
        account = (JointAccountEntity) accountDAO.save(account);
        return account.toDTO();
    }

    @Override
    public SavingAccountDTO openSavingAccount(String login, String name) throws FakeClientException, FakeSavingBookException, DuplicateAccountException, ClientTypeException {
        if (login == null || name == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        if (!client.canOpen(AccountEntity.Type.SAVING)) {
            throw new ClientTypeException();
        }
        SavingBookEntity savingBook = savingBookDAO.findByName(name);
        if (savingBook == null) {
            throw new FakeSavingBookException();
        }
        if (accountDAO.hasSavingAccount(login, savingBook.getId())) {
            throw new DuplicateAccountException();
        }
        String rib = generateNewRib();
        SavingAccountEntity account = new SavingAccountEntity(rib, client, DEFAULT_MONEY, DEFAULT_CURRENCY, savingBook);
        account = (SavingAccountEntity) accountDAO.save(account);
        return account.toDTO();
    }

    @Override
    public ShareAccountDTO openShareAccount(String login) throws FakeClientException, DuplicateAccountException {
        if (login == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        if (accountDAO.hasShareAccount(login)) {
            throw new DuplicateAccountException();
        }
        String rib = generateNewRib();
        ShareAccountEntity account = new ShareAccountEntity(rib, client);
        account = (ShareAccountEntity) accountDAO.save(account);
        return account.toDTO();
    }

    @Override
    public void closeAccount(String login, String rib) throws FakeClientException, AccountOwnerException, ClosureException, CloseRequestException {
        if (login == null || rib == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        AccountEntity account = accountDAO.find(rib);
        if (account == null || !account.isOwnBy(client)) {
            throw new AccountOwnerException();
        }
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
                        throw new IllegalStateException("Un professionel a ferme un compte joint.");
                }
                break;
            case SAVING:
                switch (client.getType()) {
                    case PRIVATE:
                        closeAccount((SavingAccountEntity) account);
                        break;
                    case PROFESIONAL:
                        throw new IllegalStateException("Un professionel a ferme un compte joint.");
                }
                break;
            case SHARE:
                closeAccount((ShareAccountEntity) account);
                break;
        }
    }

    private void closeAccount(CurrentAccountEntity account) throws ClosureException {
        if (account.getClients() != null && account.getClients().size() > 1) {
            throw new IllegalStateException("Un compte courant a plusieurs proprietaires.");
        }
        if (account.getMoney() != 0) {
            throw new ClosureException();
        }
        accountDAO.delete(account);
    }

    private void closeAccount(JointAccountEntity account, PrivateEntity client) throws ClosureException, CloseRequestException {
        if (account.getClients() != null && account.getClients().size() < 2) {
            throw new IllegalStateException("Un compte joint n'a que un seul proprietaire.");
        }

        if (account.getMoney() != 0) {
            throw new ClosureException();
        }

        List<ClientEntity> clients = account.getClients();
        List<CloseRequestEntity> requests = closeRequestDAO.findAllByRib(account.getRib());

        Map<String, Boolean> map = new HashMap<String, Boolean>();
        Iterator<ClientEntity> itc = clients.iterator();
        while (itc.hasNext()) {
            map.put(itc.next().getLogin(), false);
        }

        Iterator<CloseRequestEntity> itr = requests.iterator();
        while (itr.hasNext()) {
            String login = itr.next().getClient().getLogin();
            if (map.get(login) == null) {
                throw new IllegalStateException("Un client non proprietaire d'un compte joint a emis une requete de fermeture sur ce compte joint.");
            } else {
                map.put(login, true);
            }
        }

        if (map.containsValue(false)) {
            if (!map.get(client.getLogin())) {
                closeRequestDAO.save(new CloseRequestEntity(client, account));
            }
            throw new CloseRequestException();
        } else {
            itr = requests.iterator();
            while (itr.hasNext()) {
                closeRequestDAO.delete(itr.next());
            }
            accountDAO.delete(account);
        }
    }

    private void closeAccount(SavingAccountEntity account) throws ClosureException {
        if (account.getClients() != null && account.getClients().size() > 1) {
            throw new IllegalStateException("Un compte livret a plusieurs proprietaires.");
        }
        if (account.getMoney() != 0) {
            throw new ClosureException();
        }
        accountDAO.delete(account);
    }

    private void closeAccount(ShareAccountEntity account) throws ClosureException {
        if (account.getClients() != null && account.getClients().size() > 1) {
            throw new IllegalStateException("Un compte d'actions a plusieurs proprietaires.");
        }
        if (!account.getShareTitles().isEmpty()) {
            throw new ClosureException();
        }
        accountDAO.delete(account);
    }

    @Override
    public List<MovementDTO> getMovements(String login, String rib) throws FakeClientException, AccountOwnerException {
        if (login == null || rib == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        AccountEntity account = accountDAO.find(rib);
        if (account == null || !account.isOwnBy(client)) {
            throw new AccountOwnerException();
        }
        return MovementEntity.toDTO(movementDAO.findAllByRib(rib));
    }

    @Override
    public List<MovementDTO> getMovements(String login, String rib, int n) throws FakeClientException, AccountOwnerException {
        if (login == null || rib == null || n < 0) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        AccountEntity account = accountDAO.find(rib);
        if (account == null || !account.isOwnBy(client)) {
            throw new AccountOwnerException();
        }
        return MovementEntity.toDTO(movementDAO.findNByRib(rib, n));
    }

    @Override
    public List<MovementDTO> getMovements(String login, String rib, Date date) throws FakeClientException, AccountOwnerException {
        if (login == null || rib == null || date == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        AccountEntity account = accountDAO.find(rib);
        if (account == null || !account.isOwnBy(client)) {
            throw new AccountOwnerException();
        }
        return MovementEntity.toDTO(movementDAO.findRecentByRib(rib, date));
    }

    private String generateNewRib() {
        Set<String> ribs = new HashSet<String>(accountDAO.findAllRib());
        String rib = generateRandomRib();
        while (ribs.contains(rib)) {
            rib = generateRandomRib();
        }
        return rib;
    }

    private String generateRandomRib() {
        return "" + (RIB_MIN + random.nextInt(RIB_RANGE));
    }

}
