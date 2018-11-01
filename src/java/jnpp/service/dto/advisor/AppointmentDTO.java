package jnpp.service.dto.advisor;

import java.util.Date;
import jnpp.dao.entities.advisor.AppointmentEntity;

public class AppointmentDTO {
    
    private final Long id;
    private final Date date;
    private final AdvisorDTO advisor;
    
    public AppointmentDTO(AppointmentEntity appointment) {
        id = appointment.getId();
        date = appointment.getDate();
        advisor = new AdvisorDTO(appointment.getAdvisor());
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public AdvisorDTO getAdvisor() {
        return advisor;
    }
    
}
