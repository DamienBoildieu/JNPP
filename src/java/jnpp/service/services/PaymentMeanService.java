package jnpp.service.services;

import java.util.List;
import jnpp.dao.entities.paymentmeans.PaymentMeanEntity.Status;
import jnpp.service.dto.paymentmeans.BankCardDTO;
import jnpp.service.dto.paymentmeans.CheckbookDTO;
import jnpp.service.exceptions.entities.FakeAccountException;
import jnpp.service.exceptions.entities.FakeClientException;
import jnpp.service.exceptions.movements.AccountTypeException;
import jnpp.service.exceptions.owners.AccountOwnerException;

public interface PaymentMeanService {

    BankCardDTO commandBankCard(String login, String rib)
            throws FakeClientException, AccountTypeException, AccountOwnerException;

    CheckbookDTO commandCheckbook(String login, String rib)
            throws FakeClientException, AccountTypeException, AccountOwnerException;

    List<BankCardDTO> getBankCards(String login)
            throws FakeClientException;

    List<BankCardDTO> getBankCards(String login, Status status)
            throws FakeClientException;

    List<BankCardDTO> getBankCards(String login, String rib)
            throws FakeClientException, FakeAccountException,
            AccountOwnerException;

    List<CheckbookDTO> getCheckBooks(String login)
            throws FakeClientException;

    List<CheckbookDTO> getCheckBooks(String login, Status status)
            throws FakeClientException;

    List<CheckbookDTO> getCheckBooks(String login, String rib)
            throws FakeClientException, AccountOwnerException;

}
