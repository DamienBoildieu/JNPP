package jnpp.dao.repositories;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jnpp.dao.entities.accounts.SavingBookEntity;

@Repository
public class SavingBookDAOImpl extends GenericDAOImpl<SavingBookEntity>
        implements SavingBookDAO {

    @Transactional(readOnly = true)
    @Override
    public List<SavingBookEntity> findAll() {
        Query query = getEm().createNamedQuery("find_all_savingbook");
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public SavingBookEntity findByName(String name) {
        Query query = getEm().createNamedQuery("find_savingbook_by_name");
        query.setParameter("name", name);
        try {
            return (SavingBookEntity) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
