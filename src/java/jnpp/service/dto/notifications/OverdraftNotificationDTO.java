package jnpp.service.dto.notifications;

import jnpp.dao.entities.notifications.OverdraftNotificationEntity;

public class OverdraftNotificationDTO extends NotificationDTO {

    private final String rib;
    
    public OverdraftNotificationDTO(OverdraftNotificationEntity notification) {
        super(notification);
        rib = notification.getAccount().getRib();
    }
    
    @Override
    public Type getType() {
        return NotificationDTO.Type.OVERDRAFT;
    }
    
    public String getRib() {
        return rib;
    }
    
}
