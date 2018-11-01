package jnpp.service.dto.notifications;

import java.util.Date;
import jnpp.dao.entities.notifications.AppointmentNotificationEntity;
import jnpp.dao.entities.notifications.MessageNotificationEntity;
import jnpp.dao.entities.notifications.MovementNotificationEntity;
import jnpp.dao.entities.notifications.NotificationEntity;
import jnpp.dao.entities.notifications.OverdraftNotificationEntity;
import jnpp.dao.entities.notifications.PaymentMeanNotificationEntity;

public abstract class NotificationDTO {
    
    public static enum Type {
        
        APPOINTMENT,
        PAYMENT_MEAN,
        MESSAGE,
        MOVEMENT,
        OVERDRAFT;
        
    }
    
    private Long id;
    private Date date;

    public NotificationDTO(Long id, Date date) {
        this.id = id;
        this.date = date;
    }
    
    public abstract Type getType();

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

}
