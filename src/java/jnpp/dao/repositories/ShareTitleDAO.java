package jnpp.dao.repositories;

import jnpp.dao.entities.accounts.ShareTitleEntity;

public interface ShareTitleDAO extends GenericDAO<ShareTitleEntity> {
 
    ShareTitleEntity findByRibName(String login, String name);
    
}
