package jnpp.service.services;

import java.util.Date;
import java.util.List;
import jnpp.dao.entities.IdentityEntity;
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
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeSavingBookException;
import jnpp.service.exceptions.owners.AccountOwnerException;

public interface AccountService extends IService {
    
    List<SavingBookDTO> getSavingBooks();
    List<ShareDTO> getShares();
    
    List<AccountDTO> getAccounts(String login) throws FakeClientException;
    
    CurrentAccountDTO openCurrentAccount(String login) throws DuplicateAccountException, FakeClientException;
    JointAccountDTO openJointAccount(String login, List<IdentityEntity> identities) throws FakeClientException, UnknownIdentityException, ClientTypeException;
    SavingAccountDTO openSavingAccount(String login, String name) throws FakeClientException, FakeSavingBookException, DuplicateAccountException, ClientTypeException;
    ShareAccountDTO openShareAccount(String login) throws FakeClientException, DuplicateAccountException;
    
    void closeAccount(String login, String rib) throws FakeClientException, AccountOwnerException, ClosureException, CloseRequestException;
        
    List<MovementDTO> getMovements(String login, String rib)
            throws FakeClientException, AccountOwnerException;
    List<MovementDTO> getMovements(String login, String rib, int n)
            throws FakeClientException, AccountOwnerException;
    List<MovementDTO> getMovements(String login, String rib, Date date)
            throws FakeClientException, AccountOwnerException;
    
}
