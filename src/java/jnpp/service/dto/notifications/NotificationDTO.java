package jnpp.service.dto.notifications;

import java.util.Date;
import jnpp.dao.entities.notifications.NotificationEntity;

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
    
}
