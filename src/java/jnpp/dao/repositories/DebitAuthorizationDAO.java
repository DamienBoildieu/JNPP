package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.accounts.DebitAuthorizationEntity;

public interface DebitAuthorizationDAO extends GenericDAO<DebitAuthorizationEntity> {

    DebitAuthorizationEntity findByRibFromRibTo(String ribFrom, String ribTo);

    List<DebitAuthorizationEntity> findAllByLogin(String login);

    List<DebitAuthorizationEntity> findAllByLoginRibFrom(String login, String rib);

    boolean canDebit(String ribFrom, String ribTo);

}
