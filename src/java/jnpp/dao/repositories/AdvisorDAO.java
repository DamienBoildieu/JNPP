package jnpp.dao.repositories;

import java.util.List;
import jnpp.dao.entities.advisor.AdvisorEntity;
import jnpp.dao.entities.clients.ClientEntity;

public interface AdvisorDAO extends GenericDAO<AdvisorEntity> {

    List<AdvisorEntity> findAll();

    AdvisorEntity findByIdentity(String firstname, String lastname);

    List<ClientEntity> findAllClientByID(Long id);

}
