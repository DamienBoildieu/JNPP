package jnpp.dao.repositories;

import java.util.Date;
import java.util.List;
import jnpp.dao.entities.advisor.AppointmentEntity;

public interface AppointmentDAO extends GenericDAO<AppointmentEntity> {
    
    List<AppointmentEntity> findAllByLogin(String login);
    List<AppointmentEntity> findRecentByLogin(String login, Date date);
    Long countByAdvisorIdInMinMax(Long id, Date min, Date max);
    
}
