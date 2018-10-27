package jnpp.dao.entities.notifications;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jnpp.dao.entities.Appointment;

@Entity
@DiscriminatorValue(value = Notification.Type.Values.APPOINTMENT)
public class AppointmentNotification extends Notification 
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "appointment_fk")
    private Appointment appointment;

    @Override
    public Type getType() {
        return Notification.Type.APPOINTMENT;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.notifications.AppointmentNotification[ id=" + getId() + " ]";
    }
    
}
