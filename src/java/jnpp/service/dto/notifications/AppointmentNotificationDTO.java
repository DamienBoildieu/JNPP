package jnpp.service.dto.notifications;

import java.util.Date;
import jnpp.service.dto.advisor.AppointmentDTO;

public class AppointmentNotificationDTO extends NotificationDTO {
    
    private AppointmentDTO appointment;

    public AppointmentNotificationDTO(Long id, Date date, Boolean seen, AppointmentDTO appointment) {
        super(id, date, seen);
        this.appointment = appointment;
    }

    @Override
    public Type getType() {
        return NotificationDTO.Type.APPOINTMENT;
    }

    public AppointmentDTO getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentDTO appointment) {
        this.appointment = appointment;
    }
    
}
