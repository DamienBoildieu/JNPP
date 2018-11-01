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
    
    private final Long id;
    private final Date date;
    
    public NotificationDTO(NotificationEntity notification) {
        id = notification.getId();
        date = notification.getDate();
    }
    
    public abstract Type getType();

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }
    
    public static NotificationDTO newDTO(NotificationEntity notification) {
        switch (notification.getType()) {
            case APPOINTMENT:
                return new AppointmentNotificationDTO((AppointmentNotificationEntity) notification);
            case MESSAGE:
                return new MessageNotificationDTO((MessageNotificationEntity) notification);
            case MOVEMENT:
                return new MovementNotificationDTO((MovementNotificationEntity) notification);
            case OVERDRAFT:
                return new OverdraftNotificationDTO((OverdraftNotificationEntity) notification);
            case PAYMENT_MEAN:
                return new PaymentMeanNotificationDTO((PaymentMeanNotificationEntity) notification);
        }
        return null;
    }
    
}
