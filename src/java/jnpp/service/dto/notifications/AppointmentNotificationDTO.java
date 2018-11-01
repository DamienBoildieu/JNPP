package jnpp.service.dto.notifications;

import jnpp.dao.entities.notifications.AppointmentNotificationEntity;
import jnpp.service.dto.advisor.AppointmentDTO;

public class AppointmentNotificationDTO extends NotificationDTO {
    
    private final AppointmentDTO appointment;
    
    public AppointmentNotificationDTO(AppointmentNotificationEntity notification) {
        super(notification);
        appointment = new AppointmentDTO(notification.getAppointment());
    }
    
    @Override
    public Type getType() {
        return NotificationDTO.Type.APPOINTMENT;
    }

    public AppointmentDTO getAppointment() {
        return appointment;
    }
    
}
