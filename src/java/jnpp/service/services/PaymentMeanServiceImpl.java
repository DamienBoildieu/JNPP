package jnpp.service.services;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
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

    public static final int ID_LENGTH = 8;
    private static final int ID_MIN = (int) Math.pow(10, ID_LENGTH - 1);
    private static final int ID_RANGE = 9 * ID_MIN;

    @Resource
    ClientDAO clientDAO;
    @Resource
    AccountDAO accountDAO;
    @Resource
    PaymentMeanDAO paymentMeanDAO;

    private final Random random = new Random();

    @Override
    public BankCardDTO commandBankCard(String login, String rib) throws FakeClientException, AccountTypeException, AccountOwnerException {
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
        if (!canBindToBanckCard(account)) {
            throw new AccountTypeException();
        }
        String id = generateNewId();
        BankCardEntity bankcard = new BankCardEntity(id, client, account, PaymentMeanEntity.Status.ORDERED);
        bankcard = (BankCardEntity) paymentMeanDAO.save(bankcard);
        return bankcard.toDTO();
    }

    @Override
    public CheckbookDTO commandCheckbook(String login, String rib) throws FakeClientException, AccountTypeException, AccountOwnerException {
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
        if (!canBindToCheckbook(account)) {
            throw new AccountTypeException();
        }
        String id = generateNewId();
        CheckbookEntity checkbook = new CheckbookEntity(id, client, account, PaymentMeanEntity.Status.ORDERED);
        checkbook = (CheckbookEntity) paymentMeanDAO.save(checkbook);
        return checkbook.toDTO();
    }

    @Override
    public List<BankCardDTO> getBankCards(String login) throws FakeClientException {
        if (login == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        List<BankCardEntity> bankcards = paymentMeanDAO.findBankCardByLogin(login);
        return BankCardEntity.toDTO(bankcards);
    }

    @Override
    public List<BankCardDTO> getBankCards(String login, PaymentMeanEntity.Status status) throws FakeClientException {
        if (login == null || status == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        List<BankCardEntity> bankcards = paymentMeanDAO.findBankCardByLoginStatus(login, status);
        return BankCardEntity.toDTO(bankcards);
    }

    @Override
    public List<BankCardDTO> getBankCards(String login, String rib) throws FakeClientException, AccountOwnerException {
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
        List<BankCardEntity> bankcards = paymentMeanDAO.findBankCardByLoginRib(login, rib);
        return BankCardEntity.toDTO(bankcards);
    }

    @Override
    public List<CheckbookDTO> getCheckBooks(String login) throws FakeClientException {
        if (login == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        List<CheckbookEntity> checkbooks = paymentMeanDAO.findCheckBookByLogin(login);
        return (List<CheckbookDTO>) CheckbookEntity.toDTO(checkbooks);
    }

    @Override
    public List<CheckbookDTO> getCheckBooks(String login, PaymentMeanEntity.Status status) throws FakeClientException {
        if (login == null || status == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        List<CheckbookEntity> checkbooks = paymentMeanDAO.findCheckBookByLoginStatus(login, status);
        return CheckbookEntity.toDTO(checkbooks);
    }

    @Override
    public List<CheckbookDTO> getCheckBooks(String login, String rib) throws FakeClientException, AccountOwnerException {
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
        List<CheckbookEntity> checkbooks = paymentMeanDAO.findCheckBookByLoginRib(login, rib);
        return CheckbookEntity.toDTO(checkbooks);
    }

    private String generateNewId() {
        Set<String> ids = new HashSet<String>(paymentMeanDAO.findAllId());
        String id = generateRandomId();
        while (ids.contains(id)) {
            id = generateRandomId();
        }
        return id;
    }

    private String generateRandomId() {
        return "" + (ID_MIN + random.nextInt(ID_RANGE));
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
