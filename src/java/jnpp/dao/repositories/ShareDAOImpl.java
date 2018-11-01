/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.dao.repositories;

import java.util.List;
import javax.persistence.Query;
import jnpp.dao.entities.accounts.ShareEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ShareDAOImpl extends GenericDAOImpl<ShareEntity> implements ShareDAO {
    
    @Transactional(readOnly = true)
    @Override
    public List<ShareEntity> findAll() {
        Query query = getEm().createNamedQuery("find_all_share");
        return query.getResultList();
    }
    
}
