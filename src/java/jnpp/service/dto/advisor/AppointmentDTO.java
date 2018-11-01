package jnpp.service.dto.advisor;

import java.util.Date;
import jnpp.dao.entities.advisor.AppointmentEntity;

public class AppointmentDTO {
    
    public static enum Status {
        
        DEMAND,
        ACCEPTED,
        DENIED,
        CANCEL;
        
    }
    
    private final Long id;
    private final Date date;
    private final AdvisorDTO advisor;
    private final Status status;
    
    public AppointmentDTO(AppointmentEntity appointment) {
        id = appointment.getId();
        date = appointment.getDate();
        advisor = new AdvisorDTO(appointment.getClient().getAdvisor());
        switch (appointment.getStatus()) {
            case ACCEPTED:
                status = Status.ACCEPTED;
                break;
            case CANCEL:
                status = Status.CANCEL;
                break;
            case DEMAND:
                status = Status.DEMAND;
                break;
            case DENIED:
                status = Status.DENIED;
                break;
            default:
                status = null;
        }
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

    public Status getStatus() {
        return status;
    }
    
}
