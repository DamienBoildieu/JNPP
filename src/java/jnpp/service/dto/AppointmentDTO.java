package jnpp.service.dto;

import java.util.Date;
import jnpp.dao.entities.AppointmentEntity;

public class AppointmentDTO {
    
    public static enum Status {
        
        DEMAND,
        ACCEPTED,
        DENIED,
        CANCEL;
        
    }
    
    private final Date date;
    private final AdvisorDTO advisor;
    private final Status status;
    
    public AppointmentDTO(AppointmentEntity appointment) {
        date = appointment.getDate();
        advisor = new AdvisorDTO(appointment.getAdvisor());
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
    
}
