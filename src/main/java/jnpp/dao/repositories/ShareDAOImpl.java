/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.dao.repositories;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jnpp.dao.entities.accounts.ShareEntity;

@Repository
public class ShareDAOImpl extends GenericDAOImpl<ShareEntity>
        implements ShareDAO {

    @Transactional(readOnly = true)
    @Override
    public List<ShareEntity> findAll() {
        Query query = getEm().createNamedQuery("find_all_share");
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public ShareEntity findByName(String name) {
        Query query = getEm().createNamedQuery("find_share_by_name");
        query.setParameter("name", name);
        try {
            return (ShareEntity) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
