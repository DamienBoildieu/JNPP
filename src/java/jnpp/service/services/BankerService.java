package jnpp.service.services;

import java.util.List;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.accounts.CurrencyDTO;
import jnpp.service.dto.accounts.SavingBookDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.dto.advisor.MessageDTO;
import jnpp.service.dto.clients.LoginDTO;
import jnpp.service.dto.movements.TransfertDTO;
import jnpp.service.dto.paymentmeans.PaymentMeanDTO;
import jnpp.service.exceptions.advisors.NoAdvisorException;
import jnpp.service.exceptions.duplicates.DuplicateAdvisorException;
import jnpp.service.exceptions.duplicates.DuplicateSavingbookException;
import jnpp.service.exceptions.duplicates.DuplicateShareException;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeAdvisorException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakePaymentMeanException;
import jnpp.service.exceptions.movements.AccountTypeException;

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
    
    TransfertDTO transfert(String ribFrom, String ribTo, Double amount, CurrencyDTO currencyDTO)
            throws FakeAccountException, AccountTypeException;
    
}
