package jnpp.service.services;

import java.util.List;
import javax.annotation.Resource;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.accounts.DebitAuthorizationEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.repositories.AccountDAO;
import jnpp.dao.repositories.ClientDAO;
import jnpp.dao.repositories.DebitAuthorizationDAO;
import jnpp.service.dto.accounts.DebitAuthorizationDTO;
import jnpp.service.exceptions.duplicates.DuplicateDebitAuthorizationException;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeDebitAuthorizationException;
import jnpp.service.exceptions.owners.AccountOwnerException;
import org.springframework.stereotype.Service;

@Service("DebitAuthorizationService")
public class DebitAuthorizationServiceImpl implements DebitAuthorizationService {

    @Resource
    ClientDAO clientDAO;
    @Resource
    AccountDAO accountDAO;
    @Resource
    DebitAuthorizationDAO debitAuthorizationDAO;
    
    @Override
    public DebitAuthorizationDTO createDebitAuthorization(String login, String ribFrom, String ribTo) throws FakeClientException, FakeAccountException, AccountOwnerException, DuplicateDebitAuthorizationException {
        if (login == null || ribFrom == null || ribTo == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        AccountEntity account = accountDAO.find(ribFrom);
        if (account == null) throw new FakeAccountException();
        if (!account.isOwnBy(client)) throw new AccountOwnerException();
        DebitAuthorizationEntity authorization = debitAuthorizationDAO.findByRibFromRibTo(ribFrom, ribTo);
        if (authorization != null) throw new DuplicateDebitAuthorizationException();      
        authorization = new DebitAuthorizationEntity(account, ribTo);
        debitAuthorizationDAO.save(authorization);
        return authorization.toDTO();
    }

    @Override
    public void deleteDebitAuthorization(String login, String ribFrom, String ribTo) throws FakeClientException, FakeDebitAuthorizationException, AccountOwnerException {
        if (login == null || ribFrom == null || ribTo == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        DebitAuthorizationEntity authorization = debitAuthorizationDAO.findByRibFromRibTo(ribFrom, ribTo);    
        if (authorization == null) throw new FakeDebitAuthorizationException();
        if (!authorization.getFrom().isOwnBy(client)) throw new AccountOwnerException();
        debitAuthorizationDAO.delete(authorization);
    }

    @Override
    public List<DebitAuthorizationDTO> getDebitAuthorizations(String login) throws FakeClientException {
        if (login == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();    
        List<DebitAuthorizationEntity> authorizations = debitAuthorizationDAO.findAllByLogin(login);
        return DebitAuthorizationEntity.toDTO(authorizations);
    }

    @Override
    public List<DebitAuthorizationDTO> getDebitAuthorizations(String login, String rib) throws FakeClientException, FakeAccountException, AccountOwnerException {
        if (login == null || rib == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        AccountEntity account = accountDAO.find(rib);
        if (account == null) throw new FakeAccountException();
        if (!account.isOwnBy(client)) throw new AccountOwnerException();    
        List<DebitAuthorizationEntity> authorizations = debitAuthorizationDAO.findAllByLoginRibFrom(login, rib);
        return DebitAuthorizationEntity.toDTO(authorizations);    
    }
    
}
