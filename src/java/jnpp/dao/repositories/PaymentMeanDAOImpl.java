package jnpp.dao.repositories;

import java.util.List;
import javax.persistence.Query;
import jnpp.dao.entities.paymentmeans.BankCardEntity;
import jnpp.dao.entities.paymentmeans.CheckbookEntity;
import jnpp.dao.entities.paymentmeans.PaymentMeanEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PaymentMeanDAOImpl extends GenericDAOImpl<PaymentMeanEntity> implements PaymentMeanDAO {

    @Transactional(readOnly = true)
    @Override
    public List<BankCardEntity> findBankCardByLogin(String login) {
        Query query = getEm().createNamedQuery("find_bankcard_by_login");
        query.setParameter("login", login);
        return query.getResultList();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<BankCardEntity> findBankCardByLoginStatus(String login, PaymentMeanEntity.Status status) {
        Query query = getEm().createNamedQuery("find_bankcard_by_login_status");
        query.setParameter("login", login);
        query.setParameter("status", status);
        return query.getResultList();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<BankCardEntity> findBankCardByLoginRib(String login, String rib) {
        Query query = getEm().createNamedQuery("find_bankcard_by_login_rib");
        query.setParameter("login", login);
        query.setParameter("rib", rib);
        return query.getResultList();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<CheckbookEntity> findCheckBookByLogin(String login) {
        Query query = getEm().createNamedQuery("find_checkbook_by_login");
        query.setParameter("login", login);
        return query.getResultList();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<CheckbookEntity> findCheckBookByLoginStatus(String login, PaymentMeanEntity.Status status) {
        Query query = getEm().createNamedQuery("find_checkbook_by_login_status");
        query.setParameter("login", login);
        query.setParameter("status", status);
        return query.getResultList();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<CheckbookEntity> findCheckBookByLoginRib(String login, String rib) {
        Query query = getEm().createNamedQuery("find_checkbook_by_login_rib");
        query.setParameter("login", login);
        query.setParameter("rib", rib);
        return query.getResultList();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<PaymentMeanEntity> findAll() {
        Query query = getEm().createNamedQuery("find_all_paymentmeans");
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> findAllId() {
        Query query = getEm().createNamedQuery("find_all_paymentmean_ids");
        return query.getResultList();
    }    
    
}
