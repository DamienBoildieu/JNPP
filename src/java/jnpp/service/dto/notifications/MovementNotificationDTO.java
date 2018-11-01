package jnpp.service.dto.notifications;

import jnpp.dao.entities.notifications.MovementNotificationEntity;
import jnpp.service.dto.movements.MovementDTO;

public class MovementNotificationDTO extends NotificationDTO {
    
    private final MovementDTO movement;
    
    public MovementNotificationDTO(MovementNotificationEntity notification) {
        super(notification);
        movement = MovementDTO.newDTO(notification.getMovement());
    }
    
    @Override
    public Type getType() {
        return NotificationDTO.Type.MOVEMENT;
    }
    
    public MovementDTO getMovement() {
        return movement;
    }
    
}
