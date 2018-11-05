package jnpp.dao.entities.notifications;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import jnpp.dao.entities.advisor.AppointmentEntity;
import jnpp.service.dto.notifications.AppointmentNotificationDTO;

@Entity
@DiscriminatorValue(value = NotificationEntity.Type.Values.APPOINTMENT)
public class AppointmentNotificationEntity extends NotificationEntity 
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "appointment_fk")
    private AppointmentEntity appointment;

    @Override
    public Type getType() {
        return NotificationEntity.Type.APPOINTMENT;
    }

    public AppointmentEntity getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentEntity appointment) {
        this.appointment = appointment;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.notifications.AppointmentNotificationEntity[ id=" + getId() + " ]";
    }
    
    @Override
    public AppointmentNotificationDTO toDTO() {
        return new AppointmentNotificationDTO(getId(), getDate(), getSeen(), appointment.toDTO());
    }
    
}
