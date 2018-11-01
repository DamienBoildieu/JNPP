package jnpp.service.dto.advisor;

import java.util.Date;
import jnpp.dao.entities.advisor.AppointmentEntity;

public class AppointmentDTO {
    
    private Long id;
    private Date date;
    private AdvisorDTO advisor;
    
    public AppointmentDTO(Long id, Date date, AdvisorDTO advisor) {
        this.id = id;
        this.date = date;
        this.advisor = advisor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AdvisorDTO getAdvisor() {
        return advisor;
    }

    public void setAdvisor(AdvisorDTO advisor) {
        this.advisor = advisor;
    }
    
}
