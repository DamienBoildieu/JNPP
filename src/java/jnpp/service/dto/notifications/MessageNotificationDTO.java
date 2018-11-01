package jnpp.service.dto.notifications;

import java.util.Date;
import jnpp.dao.entities.notifications.MessageNotificationEntity;
import jnpp.service.dto.advisor.MessageDTO;

public class MessageNotificationDTO extends NotificationDTO {
    
    private MessageDTO message;

    public MessageNotificationDTO(Long id, Date date, MessageDTO message) {
        super(id, date);
        this.message = message;
    }
            
    @Override
    public Type getType() {
        return NotificationDTO.Type.MESSAGE;
    }
    
    public MessageDTO getMessage() {
        return message;
    }

    public void setMessage(MessageDTO message) {
        this.message = message;
    }
    
}
