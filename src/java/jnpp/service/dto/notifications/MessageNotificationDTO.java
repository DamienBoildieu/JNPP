package jnpp.service.dto.notifications;

import jnpp.dao.entities.notifications.MessageNotificationEntity;
import jnpp.service.dto.advisor.MessageDTO;

public class MessageNotificationDTO extends NotificationDTO {
    
    private final MessageDTO message;
    
    public MessageNotificationDTO(MessageNotificationEntity entity) {
        super(entity);
        message = new MessageDTO(entity.getMessage());
    }
    
    @Override
    public Type getType() {
        return NotificationDTO.Type.MESSAGE;
    }
    
    public MessageDTO getMessage() {
        return message;
    }
    
}
