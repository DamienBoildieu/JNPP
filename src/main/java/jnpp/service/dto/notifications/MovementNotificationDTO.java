package jnpp.service.dto.notifications;

import java.util.Date;

import jnpp.service.dto.movements.MovementDTO;

public class MovementNotificationDTO extends NotificationDTO {

    private MovementDTO movement;

    public MovementNotificationDTO(Long id, Date date, Boolean seen,
            MovementDTO movement) {
        super(id, date, seen);
        this.movement = movement;
    }

    @Override
    public Type getType() {
        return NotificationDTO.Type.MOVEMENT;
    }

    public MovementDTO getMovement() {
        return movement;
    }

    public void setMovement(MovementDTO movement) {
        this.movement = movement;
    }

}
