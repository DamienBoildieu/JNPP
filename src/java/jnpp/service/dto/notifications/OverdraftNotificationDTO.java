package jnpp.service.dto.notifications;

import java.util.Date;
import jnpp.dao.entities.notifications.OverdraftNotificationEntity;

public class OverdraftNotificationDTO extends NotificationDTO {

    private String rib;

    public OverdraftNotificationDTO(Long id, Date date, String rib) {
        super(id, date);
        this.rib = rib;
    }
    
    @Override
    public Type getType() {
        return NotificationDTO.Type.OVERDRAFT;
    }
    
    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }
    
}
