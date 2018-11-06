package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.accounts.ShareTitleEntity;

public interface ShareTitleDAO extends GenericDAO<ShareTitleEntity> {

    ShareTitleEntity findByRibName(String login, String name);
    
    List<ShareTitleEntity> findAllByRib(String rib);
    
}
