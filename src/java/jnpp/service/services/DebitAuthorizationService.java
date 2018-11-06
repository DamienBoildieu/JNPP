package jnpp.service.services;

import java.util.List;
import jnpp.service.dto.accounts.DebitAuthorizationDTO;
import jnpp.service.exceptions.duplicates.DuplicateDebitAuthorizationException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeDebitAuthorizationException;
import jnpp.service.exceptions.movements.AccountTypeException;
import jnpp.service.exceptions.owners.AccountOwnerException;

public interface DebitAuthorizationService {

    DebitAuthorizationDTO createDebitAuthorization(String login, String ribFrom,
            String ribTo)
            throws FakeClientException, AccountOwnerException,
            DuplicateDebitAuthorizationException, AccountTypeException;

    void deleteDebitAuthorization(String login, String ribFrom,
            String ribTo)
            throws FakeClientException, FakeDebitAuthorizationException,
            AccountOwnerException;

    List<DebitAuthorizationDTO> getDebitAuthorizations(String login)
            throws FakeClientException;

    List<DebitAuthorizationDTO> getDebitAuthorizations(String login, String rib)
            throws FakeClientException, AccountOwnerException;

}
