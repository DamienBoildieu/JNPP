package jnpp.dao.repositories;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jnpp.dao.entities.advisor.AppointmentEntity;

@Repository
public class AppointmentDAOImpl extends GenericDAOImpl<AppointmentEntity>
        implements AppointmentDAO {

    @Transactional(readOnly = true)
    @Override
    public List<AppointmentEntity> findAllByLogin(String login) {
        Query query = getEm().createNamedQuery("find_all_appointment_by_rib");
        query.setParameter("login", login);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<AppointmentEntity> findRecentByLogin(String login, Date date) {
        Query query = getEm()
                .createNamedQuery("find_recent_appointment_by_rib");
        query.setParameter("login", login);
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Long countByAdvisorIdInMinMax(Long id, Date min, Date max) {
        Query query = getEm().createNamedQuery(
                "count_advisor_appointment_in_min_max", Long.class);
        query.setParameter("id", id);
        query.setParameter("min", min);
        query.setParameter("max", max);
        return (Long) query.getSingleResult();
    }

}
