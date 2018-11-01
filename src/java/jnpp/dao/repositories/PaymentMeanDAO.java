package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.paymentmeans.BankCardEntity;
import jnpp.dao.entities.paymentmeans.CheckbookEntity;
import jnpp.dao.entities.paymentmeans.PaymentMeanEntity;

public interface PaymentMeanDAO extends GenericDAO<PaymentMeanEntity> {
    
    List<BankCardEntity> findBankCardByLogin(String login);
    List<BankCardEntity> findBankCardByLoginStatus(String login, PaymentMeanEntity.Status status);
    List<BankCardEntity> findBankCardByLoginRib(String login, String rib);
    
    List<CheckbookEntity> findCheckBookByLogin(String login);
    List<CheckbookEntity> findCheckBookByLoginStatus(String login, PaymentMeanEntity.Status status);
    List<CheckbookEntity> findCheckBookByLoginRib(String login, String rib);
    
}
