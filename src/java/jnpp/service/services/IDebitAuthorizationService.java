package jnpp.service.services;

import java.util.List;
import jnpp.dao.entities.accounts.DebitAuthorizationEntity;
import jnpp.service.exceptions.duplicates.DuplicateDebitAuthorizationException;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeDebitAuthorizationException;
import jnpp.service.exceptions.owners.AccountOwnerException;

public interface IDebitAuthorizationService extends IService {

    DebitAuthorizationEntity createDebitAuthorization(String login, String ribFrom, 
            String ribTo)
            throws FakeClientException, FakeAccountException, AccountOwnerException,
            DuplicateDebitAuthorizationException;

    void deleteDebitAuthorization(String login, String ribFrom, 
            String ribTo)
            throws FakeClientException, FakeDebitAuthorizationException,
            AccountOwnerException;            

    List<DebitAuthorizationEntity> getDebitAuthorizations(String login)
            throws FakeClientException;

    List<DebitAuthorizationEntity> getDebitAuthorizations(String login, String rib)
            throws FakeClientException, FakeAccountException, AccountOwnerException;
    
}
