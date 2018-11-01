package jnpp.service.services;

import java.util.Date;
import java.util.List;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.clients.Identity;
import jnpp.dao.entities.movements.MovementEntity;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.dto.accounts.CurrentAccountDTO;
import jnpp.service.dto.accounts.SavingAccountDTO;
import jnpp.service.dto.accounts.SavingBookDTO;
import jnpp.service.dto.accounts.ShareAccountDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.exceptions.ClosureException;
import jnpp.service.exceptions.accounts.CloseRequestException;
import jnpp.service.exceptions.duplicates.DuplicateAccountException;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.entities.FakeSavingBookException;
import jnpp.service.exceptions.owners.AccountOwnerException;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.dto.accounts.CurrentAccountDTO;
import jnpp.service.dto.accounts.JointAccountDTO;
import jnpp.service.dto.accounts.SavingAccountDTO;
import jnpp.service.dto.accounts.SavingBookDTO;
import jnpp.service.dto.accounts.ShareAccountDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.dto.movements.MovementDTO;
import jnpp.service.exceptions.accounts.UnknownIdentityException;

public interface AccountService extends IService {
    
    List<SavingBookDTO> getSavingBooks();
    List<ShareDTO> getShares();
    
    List<AccountDTO> getAccounts(String login) throws FakeClientException;
    
    CurrentAccountDTO openCurrentAccount(String login) throws DuplicateAccountException, FakeClientException;
    JointAccountDTO openJointAccount(String login, List<Identity> identities) throws FakeClientException, UnknownIdentityException;
    SavingAccountDTO openSavingAccount(String login, String name) throws FakeClientException, FakeSavingBookException, DuplicateAccountException;
    ShareAccountDTO openShareAccount(String login) throws FakeClientException, DuplicateAccountException;
    
    void closeAccount(String login, String rib) throws FakeClientException, FakeAccountException, AccountOwnerException, ClosureException, CloseRequestException;
    
    List<MovementDTO> getMovements(String login)
            throws FakeClientException;
    List<MovementDTO> getMovements(String login, int n)
            throws FakeClientException;
    List<MovementDTO> getMovements(String login, Date date)
            throws FakeClientException;
    
    List<MovementDTO> getMovements(String login, String rib)
            throws FakeAccountException;
    List<MovementDTO> getMovements(String login, String rib, int n)
            throws FakeAccountException;
    List<MovementDTO> getMovements(String login, String rib, Date date)
            throws FakeAccountException;
    
}
