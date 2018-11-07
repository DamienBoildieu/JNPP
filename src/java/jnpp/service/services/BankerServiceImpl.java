package jnpp.service.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import jnpp.dao.entities.IdentityEntity;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.accounts.CurrencyEntity;
import jnpp.dao.entities.accounts.CurrentAccountEntity;
import jnpp.dao.entities.accounts.MoneyAccountEntity;
import jnpp.dao.entities.accounts.SavingBookEntity;
import jnpp.dao.entities.accounts.ShareAccountEntity;
import jnpp.dao.entities.accounts.ShareEntity;
import jnpp.dao.entities.accounts.ShareTitleEntity;
import jnpp.dao.entities.advisor.AdvisorEntity;
import jnpp.dao.entities.advisor.MessageEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.movements.DebitEntity;
import jnpp.dao.entities.movements.DepositEntity;
import jnpp.dao.entities.movements.MovementEntity;
import jnpp.dao.entities.movements.PurchaseEntity;
import jnpp.dao.entities.movements.SaleEntity;
import jnpp.dao.entities.movements.TransfertEntity;
import jnpp.dao.entities.notifications.MessageNotificationEntity;
import jnpp.dao.entities.notifications.MovementNotificationEntity;
import jnpp.dao.entities.notifications.OverdraftNotificationEntity;
import jnpp.dao.entities.notifications.PaymentMeanNotificationEntity;
import jnpp.dao.entities.paymentmeans.PaymentMeanEntity;
import jnpp.dao.repositories.AccountDAO;
import jnpp.dao.repositories.AdvisorDAO;
import jnpp.dao.repositories.ClientDAO;
import jnpp.dao.repositories.DebitAuthorizationDAO;
import jnpp.dao.repositories.MessageDAO;
import jnpp.dao.repositories.MovementDAO;
import jnpp.dao.repositories.NotificationDAO;
import jnpp.dao.repositories.PaymentMeanDAO;
import jnpp.dao.repositories.SavingBookDAO;
import jnpp.dao.repositories.ShareDAO;
import jnpp.dao.repositories.ShareTitleDAO;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.dto.accounts.CurrencyDTO;
import jnpp.service.dto.accounts.SavingBookDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.dto.advisor.MessageDTO;
import jnpp.service.dto.clients.LoginDTO;
import jnpp.service.dto.movements.DebitDTO;
import jnpp.service.dto.movements.DepositDTO;
import jnpp.service.dto.movements.PurchaseDTO;
import jnpp.service.dto.movements.SaleDTO;
import jnpp.service.dto.movements.TransfertDTO;
import jnpp.service.dto.paymentmeans.PaymentMeanDTO;
import jnpp.service.exceptions.accounts.NoCurrentAccountException;
import jnpp.service.exceptions.advisors.NoAdvisorException;
import jnpp.service.exceptions.duplicates.DuplicateAdvisorException;
import jnpp.service.exceptions.duplicates.DuplicateSavingbookException;
import jnpp.service.exceptions.duplicates.DuplicateShareException;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeAdvisorException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakePaymentMeanException;
import jnpp.service.exceptions.entities.FakeShareException;
import jnpp.service.exceptions.entities.FakeShareTitleException;
import jnpp.service.exceptions.movements.AccountTypeException;
import jnpp.service.exceptions.movements.AmountException;
import jnpp.service.exceptions.movements.DebitAuthorizationException;
import jnpp.service.exceptions.movements.OverdraftException;
import org.springframework.stereotype.Service;

@Service("BankerService")
public class BankerServiceImpl implements BankerService {

    @Resource
    ShareDAO shareDAO;
    @Resource
    SavingBookDAO savingBookDAO;
    @Resource
    ClientDAO clientDAO;
    @Resource
    AdvisorDAO advisorDAO;
    @Resource
    MessageDAO messageDAO;
    @Resource
    PaymentMeanDAO paymentMeanDAO;
    @Resource
    NotificationDAO notificationDAO;
    @Resource
    AccountDAO accountDAO;
    @Resource
    MovementDAO movementDAO;
    @Resource
    DebitAuthorizationDAO debitAuthorizationDAO;
    @Resource
    ShareTitleDAO shareTitleDAO;

    @Override
    public ShareDTO addShare(String name, Double value, CurrencyDTO currency)
            throws DuplicateShareException {
        if (name == null || value == null || value <= 0 || currency == null) {
            throw new IllegalArgumentException();
        }
        ShareEntity share = shareDAO.findByName(name);
        if (share != null) {
            throw new DuplicateShareException();
        }
        share = new ShareEntity(name, value, CurrencyEntity.toEntity(currency));
        share = shareDAO.save(share);
        return share.toDTO();
    }

    @Override
    public SavingBookDTO addSavingbook(String name, Double moneyRate, Double timeRate)
            throws DuplicateSavingbookException {
        if (name == null || moneyRate == null || moneyRate <= 0
                || timeRate == null || timeRate <= 0) {
            throw new IllegalArgumentException();
        }

        SavingBookEntity savingbook = savingBookDAO.findByName(name);
        if (savingbook != null) {
            throw new DuplicateSavingbookException();
        }
        savingbook = new SavingBookEntity(name, moneyRate, timeRate);
        savingbook = savingBookDAO.save(savingbook);
        return savingbook.toDTO();
    }

    @Override
    public List<LoginDTO> getClientLogins() {
        List<ClientEntity> clients = clientDAO.findAll();
        return ClientEntity.toLoginDTO(clients);
    }

    @Override
    public List<AdvisorDTO> getAdvisors() {
        List<AdvisorEntity> advisors = advisorDAO.findAll();
        return AdvisorEntity.toDTO(advisors);
    }

    @Override
    public AdvisorDTO addAdvisor(IdentityDTO.Gender gender, String firstname,
            String lastname, String email, String phone, Integer number,
            String street, String city, String state)
            throws DuplicateAdvisorException {
        if (gender == null || firstname == null || lastname == null
                || email == null || phone == null || number == null
                || street == null || city == null || state == null) {
            throw new IllegalArgumentException();
        }
        AdvisorEntity advisor = advisorDAO.findByIdentity(firstname, lastname);
        if (advisor != null) {
            throw new DuplicateAdvisorException();
        }
        advisor = new AdvisorEntity(IdentityEntity.Gender.toEntity(gender),
                firstname, lastname, email, phone, number, street, city, state);
        advisor = advisorDAO.save(advisor);

        List<ClientEntity> clients = clientDAO.findAllWithoutAdvisor();
        for (ClientEntity client : clients) {
            client.setAdvisor(advisor);
            clientDAO.update(client);
        }

        return advisor.toDTO();
    }

    @Override
    public List<LoginDTO> getAdvisorLogins(String firstname, String lastname) throws FakeAdvisorException {
        if (firstname == null || lastname == null) {
            throw new IllegalArgumentException();
        }
        AdvisorEntity advisor = advisorDAO.findByIdentity(firstname, lastname);
        if (advisor == null) {
            throw new FakeAdvisorException();
        }
        List<ClientEntity> clients = advisorDAO.findAllClientByID(advisor.getId());
        return ClientEntity.toLoginDTO(clients);
    }

    @Override
    public LoginDTO getLogin(String login) throws FakeClientException {
        if (login == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        return client.toLoginDTO();
    }

    @Override
    public MessageDTO sendMessage(String login, String content)
            throws FakeClientException, NoAdvisorException {
        if (login == null || content == null) {
            throw new IllegalArgumentException();
        }
        ClientEntity client = clientDAO.find(login);
        if (client == null) {
            throw new FakeClientException();
        }
        AdvisorEntity advisor = client.getAdvisor();
        if (advisor == null) {
            throw new NoAdvisorException();
        }
        Date now = Date.from(Instant.now());
        MessageEntity message = new MessageEntity(client, advisor, MessageEntity.Direction.ADVISOR_TO_CLIENT, now, content);
        message = messageDAO.save(message);
        if (client.getNotify()) {
            MessageNotificationEntity notification = new MessageNotificationEntity(client, now, false, message);
            notificationDAO.save(notification);
        }
        return message.toDTO();
    }

    @Override
    public List<PaymentMeanDTO> getPaymentMeans() {
        List<PaymentMeanEntity> paymentmeans = paymentMeanDAO.findAll();
        List<PaymentMeanDTO> dtos = new ArrayList<PaymentMeanDTO>(paymentmeans.size());
        Iterator<PaymentMeanEntity> it = paymentmeans.iterator();
        while (it.hasNext()) {
            dtos.add(it.next().toDTO());
        }
        return dtos;
    }

    @Override
    public PaymentMeanDTO upgradePaymentMean(String id) throws FakePaymentMeanException {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        PaymentMeanEntity paymentMean = paymentMeanDAO.find(id);
        if (paymentMean == null) {
            throw new FakePaymentMeanException();
        }
        if (paymentMean.getStatus() != PaymentMeanEntity.Status.DELIVERED) {
            paymentMean.setStatus(paymentMean.getStatus().next());
            paymentMean = paymentMeanDAO.save(paymentMean);
            ClientEntity client = paymentMean.getClient();
            if (client.getNotify()) {
                Date now = Date.from(Instant.now());
                PaymentMeanNotificationEntity notification = new PaymentMeanNotificationEntity(client, now, false, paymentMean);
                notificationDAO.save(notification);
            }
        }
        return paymentMean.toDTO();
    }

    @Override
    public List<AccountDTO> getAccounts() {
        List<AccountEntity> accounts = accountDAO.findAll();
        return AccountEntity.toDTO(accounts);
    }

    @Override
    public DepositDTO deposit(String rib, Double amount, CurrencyDTO currency, String label) throws FakeAccountException, AccountTypeException {
        if (rib == null || amount == null || currency == null || amount <= 0) {
            throw new IllegalArgumentException();
        }
        AccountEntity account = accountDAO.find(rib);
        if (account == null) {
            throw new FakeAccountException();
        }
        if (!account.canReceive(MovementEntity.Type.DEPOSIT)) {
            throw new AccountTypeException();
        }
        MoneyAccountEntity moneyAccount = (MoneyAccountEntity) account;
        moneyAccount.setMoney(moneyAccount.getMoney() + moneyAccount.getCurrency()
                .convert(amount, CurrencyEntity.toEntity(currency)));
        moneyAccount = (MoneyAccountEntity) accountDAO.update(moneyAccount);
        account = moneyAccount;
        Date now = Date.from(Instant.now());
        DepositEntity deposit = new DepositEntity(now, rib, amount, CurrencyEntity.toEntity(currency), label);
        deposit = (DepositEntity) movementDAO.save(deposit);
        for (ClientEntity client : account.getClients()) {
            if (client.getNotify()) {
                MovementNotificationEntity notification = new MovementNotificationEntity(client, now, false, deposit);
                notificationDAO.save(notification);
            }
        }
        return deposit.toDTO();
    }

    @Override
    public TransfertDTO transfert(String ribFrom, String ribTo, Double amount, CurrencyDTO currency, String label) throws FakeAccountException, AccountTypeException, OverdraftException {
        if (ribFrom == null || ribTo == null || amount == 0 || amount <= 0
                || currency == null) {
            throw new IllegalArgumentException();
        }

        AccountEntity accountFrom = accountDAO.find(ribFrom);
        if (accountFrom == null) {
            throw new FakeAccountException();
        }
        AccountEntity accountTo = accountDAO.find(ribTo);
        if (accountTo == null) {
            throw new FakeAccountException();
        }

        if (!accountFrom.canEmit(MovementEntity.Type.TRANSFERT)) {
            throw new AccountTypeException();
        }
        MoneyAccountEntity moneyAccountFrom = (MoneyAccountEntity) accountFrom;
        if (!accountTo.canReceive(MovementEntity.Type.TRANSFERT)) {
            throw new AccountTypeException();
        }
        MoneyAccountEntity moneyAccountTo = (MoneyAccountEntity) accountTo;

        CurrencyEntity currencyFrom = moneyAccountFrom.getCurrency();
        CurrencyEntity currencyTo = moneyAccountTo.getCurrency();

        CurrencyEntity currencyTransfert = CurrencyEntity.toEntity(currency);
        double amountFrom = currencyFrom.convert(amount, currencyTransfert);
        double amountTo = currencyTo.convert(amount, currencyTransfert);

        moneyAccountFrom.setMoney(moneyAccountFrom.getMoney() - amountFrom);
        moneyAccountTo.setMoney(moneyAccountTo.getMoney() + amountTo);

        if (moneyAccountFrom.getMoney() < 0 && !moneyAccountFrom.canOverdraft()) {
            throw new OverdraftException();
        }

        moneyAccountFrom = (MoneyAccountEntity) accountDAO.update(moneyAccountFrom);
        moneyAccountTo = (MoneyAccountEntity) accountDAO.update(moneyAccountTo);

        accountFrom = moneyAccountFrom;
        accountTo = moneyAccountTo;

        Date now = Date.from(Instant.now());

        TransfertEntity transfert
                = new TransfertEntity(now, ribFrom, ribTo, amount, currencyTransfert, label);
        transfert = (TransfertEntity) movementDAO.save(transfert);

        boolean isOverdraft = moneyAccountFrom.getMoney() < 0;

        List<ClientEntity> clientsFrom = accountFrom.getClients();
        Iterator<ClientEntity> itf = clientsFrom.iterator();
        while (itf.hasNext()) {
            ClientEntity clientFrom = itf.next();
            if (clientFrom.getNotify()) {
                MovementNotificationEntity movementNotificationFrom
                        = new MovementNotificationEntity(clientFrom, now, false, transfert);
                notificationDAO.save(movementNotificationFrom);
                if (isOverdraft) {
                    OverdraftNotificationEntity overdraftNotification
                            = new OverdraftNotificationEntity(clientFrom, now, false, accountFrom);
                    notificationDAO.save(overdraftNotification);
                }
            }
        }

        List<ClientEntity> clientsTo = accountTo.getClients();
        Iterator<ClientEntity> itt = clientsTo.iterator();
        while (itt.hasNext()) {
            ClientEntity clientTo = itt.next();
            if (clientTo.getNotify()) {
                MovementNotificationEntity movementNotificationFrom
                        = new MovementNotificationEntity(clientTo, now, false, transfert);
                notificationDAO.save(movementNotificationFrom);
            }
        }

        return transfert.toDTO();
    }

    @Override
    public DebitDTO debit(String ribFrom, String ribTo, Double amount, CurrencyDTO currency, String label)
            throws FakeAccountException, AccountTypeException, DebitAuthorizationException, OverdraftException {

        if (ribFrom == null || ribTo == null || amount == null || amount <= 0 || currency == null) {
            throw new IllegalArgumentException();
        }

        AccountEntity accountFrom = accountDAO.find(ribFrom);
        AccountEntity accountTo = accountDAO.find(ribTo);
        if (accountFrom == null || accountTo == null) {
            throw new FakeAccountException();
        }

        if (!accountFrom.canEmit(MovementEntity.Type.DEBIT)
                || !accountTo.canReceive(MovementEntity.Type.DEBIT)) {
            throw new AccountTypeException();
        }

        if (!debitAuthorizationDAO.canDebit(ribFrom, ribTo)) {
            throw new DebitAuthorizationException();
        }

        MoneyAccountEntity moneyAccountFrom = (MoneyAccountEntity) accountFrom;
        MoneyAccountEntity moneyAccountTo = (MoneyAccountEntity) accountTo;

        CurrencyEntity currencyDebit = CurrencyEntity.toEntity(currency);
        CurrencyEntity currencyFrom = moneyAccountFrom.getCurrency();
        CurrencyEntity currencyTo = moneyAccountTo.getCurrency();

        double amountFrom = currencyFrom.convert(amount, currencyDebit);
        double amountTo = currencyTo.convert(amount, currencyDebit);

        moneyAccountFrom.setMoney(moneyAccountFrom.getMoney() + amountFrom);
        moneyAccountTo.setMoney(moneyAccountTo.getMoney() - amountTo);

        if (moneyAccountTo.getMoney() < 0 && !moneyAccountTo.canOverdraft()) {
            throw new OverdraftException();
        }

        moneyAccountFrom = (MoneyAccountEntity) accountDAO.update(moneyAccountFrom);
        moneyAccountTo = (MoneyAccountEntity) accountDAO.update(moneyAccountTo);

        accountFrom = moneyAccountFrom;
        accountTo = moneyAccountTo;

        Date now = Date.from(Instant.now());

        DebitEntity debit = new DebitEntity(now, ribFrom, ribTo, amount, currencyDebit, label);
        debit = (DebitEntity) movementDAO.save(debit);

        boolean isOverdraft = moneyAccountTo.getMoney() < 0;

        for (ClientEntity clientFrom : accountFrom.getClients()) {
            if (clientFrom.getNotify()) {
                MovementNotificationEntity movementNotification
                        = new MovementNotificationEntity(clientFrom, now, false, debit);
                notificationDAO.save(movementNotification);
            }
        }

        for (ClientEntity clientTo : accountTo.getClients()) {
            if (clientTo.getNotify()) {
                MovementNotificationEntity movementNotification
                        = new MovementNotificationEntity(clientTo, now, false, debit);
                notificationDAO.save(movementNotification);
                if (isOverdraft) {
                    OverdraftNotificationEntity overdraftNotification
                            = new OverdraftNotificationEntity(clientTo, now, false, accountTo);
                    notificationDAO.save(overdraftNotification);
                }
            }
        }

        return debit.toDTO();
    }

    @Override
    public PurchaseDTO purchase(String rib, String name, Integer amount, String label)
            throws FakeAccountException, FakeShareException, NoCurrentAccountException, AccountTypeException {

        if (rib == null || name == null || amount == null || amount <= 0) {
            throw new IllegalArgumentException();
        }

        AccountEntity account = accountDAO.find(rib);
        if (account == null) {
            throw new FakeAccountException();
        }

        ShareEntity share = shareDAO.findByName(name);
        if (share == null) {
            throw new FakeShareException();
        }

        if (!account.canEmit(MovementEntity.Type.PURCHASE)) {
            throw new AccountTypeException();
        }
        ShareAccountEntity shareAccount = (ShareAccountEntity) account;

        if (account.getClients().size() != 1) {
            throw new IllegalStateException("Un compte d'actions n'a pas un unique proprietaire.");
        }
        ClientEntity client = account.getClients().get(0);

        CurrentAccountEntity currentAccount = accountDAO.findCurrentByLogin(client.getLogin());
        if (currentAccount == null) {
            throw new NoCurrentAccountException();
        }

        Date now = Date.from(Instant.now());

        ShareTitleEntity shareTitle = shareTitleDAO.findByRibName(rib, name);
        if (shareTitle == null) {
            shareTitle = new ShareTitleEntity(amount, share, shareAccount);
            shareTitle = shareTitleDAO.save(shareTitle);
        } else {
            shareTitle.setAmount(shareTitle.getAmount() + amount);
            shareTitle = shareTitleDAO.update(shareTitle);
        }

        double cost = amount * currentAccount.getCurrency()
                .convert(share.getValue(), share.getCurrency());
        currentAccount.setMoney(currentAccount.getMoney() - cost);
        currentAccount = (CurrentAccountEntity) accountDAO.update(currentAccount);

        PurchaseEntity purchase = new PurchaseEntity(now, rib, rib, amount, share, label);
        purchase = (PurchaseEntity) movementDAO.save(purchase);

        if (client.getNotify()) {

            MovementNotificationEntity movementNotification
                    = new MovementNotificationEntity(client, now, false, purchase);
            notificationDAO.save(movementNotification);

            if (currentAccount.getMoney() < 0) {

                OverdraftNotificationEntity overdraftNotification
                        = new OverdraftNotificationEntity(client, now, false, currentAccount);
                notificationDAO.save(overdraftNotification);
            }
        }

        return purchase.toDTO();
    }

    @Override
    public SaleDTO sale(String rib, String name, Integer amount, String label)
            throws FakeAccountException, FakeShareException, NoCurrentAccountException,
            AccountTypeException, AmountException, FakeShareTitleException {

        if (rib == null || name == null || amount == null || amount <= 0) {
            throw new IllegalArgumentException();
        }

        AccountEntity account = accountDAO.find(rib);
        if (account == null) {
            throw new FakeAccountException();
        }

        ShareEntity share = shareDAO.findByName(name);
        if (share == null) {
            throw new FakeShareException();
        }

        if (!account.canEmit(MovementEntity.Type.SALE)) {
            throw new AccountTypeException();
        }
        ShareAccountEntity shareAccount = (ShareAccountEntity) account;

        if (account.getClients().size() != 1) {
            throw new IllegalStateException("Un compte d'actions n'a pas un unique proprietaire.");
        }
        ClientEntity client = account.getClients().get(0);

        CurrentAccountEntity currentAccount = accountDAO.findCurrentByLogin(client.getLogin());
        if (currentAccount == null) {
            throw new NoCurrentAccountException();
        }

        Date now = Date.from(Instant.now());

        ShareTitleEntity shareTitle = shareTitleDAO.findByRibName(rib, name);
        if (shareTitle == null) {
            throw new FakeShareTitleException();
        } else {
            if (shareTitle.getAmount() < amount) {
                throw new AmountException();
            }
            shareTitle.setAmount(shareTitle.getAmount() - amount);
            if (shareTitle.getAmount() == 0) {
                shareTitleDAO.delete(shareTitle);
            } else {
                shareTitle = shareTitleDAO.update(shareTitle);
            }
        }

        double cost = amount * currentAccount.getCurrency()
                .convert(share.getValue(), share.getCurrency());
        currentAccount.setMoney(currentAccount.getMoney() + cost);
        currentAccount = (CurrentAccountEntity) accountDAO.update(currentAccount);

        SaleEntity sale = new SaleEntity(now, rib, rib, amount, share, label);
        sale = (SaleEntity) movementDAO.save(sale);

        if (client.getNotify()) {

            MovementNotificationEntity movementNotification
                    = new MovementNotificationEntity(client, now, false, sale);
            notificationDAO.save(movementNotification);
        }

        return sale.toDTO();
    }

}
