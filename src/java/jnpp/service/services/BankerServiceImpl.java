package jnpp.service.services;

import java.util.List;
import javax.annotation.Resource;
import jnpp.dao.entities.IdentityEntity;
import jnpp.dao.entities.accounts.CurrencyEntity;
import jnpp.dao.entities.accounts.SavingBookEntity;
import jnpp.dao.entities.accounts.ShareEntity;
import jnpp.dao.entities.advisor.AdvisorEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.repositories.AdvisorDAO;
import jnpp.dao.repositories.ClientDAO;
import jnpp.dao.repositories.SavingBookDAO;
import jnpp.dao.repositories.ShareDAO;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.accounts.CurrencyDTO;
import jnpp.service.dto.accounts.SavingBookDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.dto.clients.LoginDTO;
import jnpp.service.exceptions.duplicates.DuplicateAdvisorException;
import jnpp.service.exceptions.duplicates.DuplicateSavingbookException;
import jnpp.service.exceptions.duplicates.DuplicateShareException;
import jnpp.service.exceptions.entities.FakeAdvisorException;
import jnpp.service.exceptions.entities.FakeClientException;
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
    
    @Override
    public ShareDTO addShare(String name, Double value, CurrencyDTO currency) 
            throws DuplicateShareException {
        if (name == null || value == null || value <= 0 || currency == null)
            throw new IllegalArgumentException();
        ShareEntity share = shareDAO.findByName(name);
        if (share != null) throw new DuplicateShareException();
        share = new ShareEntity(name, value, CurrencyEntity.toEntity(currency));
        shareDAO.save(share);
        return share.toDTO();
    }
    
    @Override
    public SavingBookDTO addSavingbook(String name, Double moneyRate, Double timeRate)
            throws DuplicateSavingbookException {
        if (name == null || moneyRate == null || moneyRate <= 0 
                || timeRate == null || timeRate <= 0)
            throw new IllegalArgumentException();
        
        SavingBookEntity savingbook = savingBookDAO.findByName(name);
        if (savingbook != null) throw new DuplicateSavingbookException();
        savingbook = new SavingBookEntity(name, moneyRate, timeRate);
        savingBookDAO.save(savingbook);
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
                || street == null || city == null || state == null)
            throw new IllegalArgumentException();
        AdvisorEntity advisor = advisorDAO.findByIdentity(firstname, lastname);
        if (advisor != null) throw new DuplicateAdvisorException();
        advisor = new AdvisorEntity(IdentityEntity.Gender.toEntity(gender), 
                firstname, lastname, email, phone, number, street, city, state);
        advisorDAO.save(advisor);
        return advisor.toDTO();
    }

    @Override
    public List<LoginDTO> getAdvisorLogins(String firstname, String lastname) throws FakeAdvisorException {
        if (firstname == null || lastname == null) throw new IllegalArgumentException();
        AdvisorEntity advisor = advisorDAO.findByIdentity(firstname, lastname);
        if (advisor == null) throw new FakeAdvisorException();
        List<ClientEntity> clients = advisorDAO.findAllClientByID(advisor.getId());
        return ClientEntity.toLoginDTO(clients);
    }

    @Override
    public LoginDTO getLogin(String login) throws FakeClientException {
        if (login == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        return client.toLoginDTO();
    }
    
}
