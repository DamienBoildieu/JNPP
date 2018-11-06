package jnpp.service.services;

import java.util.List;
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

public interface BankerService {

    ShareDTO addShare(String name, Double value, CurrencyDTO currency)
            throws DuplicateShareException;

    SavingBookDTO addSavingbook(String name, Double moneyRate, Double timeRate)
            throws DuplicateSavingbookException;

    List<LoginDTO> getClientLogins();

    List<AdvisorDTO> getAdvisors();

    AdvisorDTO addAdvisor(IdentityDTO.Gender gender, String firstname,
            String lastname, String email, String phone, Integer number,
            String street, String city, String state)
            throws DuplicateAdvisorException;

    List<LoginDTO> getAdvisorLogins(String firstname, String lastname)
            throws FakeAdvisorException;

    LoginDTO getLogin(String login)
            throws FakeClientException;

    MessageDTO sendMessage(String login, String content)
            throws FakeClientException, NoAdvisorException;

    List<PaymentMeanDTO> getPaymentMeans();

    PaymentMeanDTO upgradePaymentMean(String id) throws FakePaymentMeanException;

    List<AccountDTO> getAccounts();

    DepositDTO deposit(String rib, Double amount, CurrencyDTO currency)
            throws FakeAccountException, AccountTypeException;

    TransfertDTO transfert(String ribFrom, String ribTo, Double amount, CurrencyDTO currency)
            throws FakeAccountException, AccountTypeException, OverdraftException;

    DebitDTO debit(String ribFrom, String ribTo, Double amount, CurrencyDTO currency)
            throws FakeAccountException, AccountTypeException, DebitAuthorizationException, OverdraftException;

    PurchaseDTO purchase(String rib, String name, Integer amount)
            throws FakeAccountException, FakeShareException, NoCurrentAccountException, AccountTypeException;

    SaleDTO sale(String rib, String name, Integer amount)
            throws FakeAccountException, FakeShareException, NoCurrentAccountException, AccountTypeException, AmountException, FakeShareTitleException;

}
