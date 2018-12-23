package jnpp.dao.entities.notifications;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.movements.MovementEntity;
import jnpp.service.dto.notifications.MovementNotificationDTO;

@Entity
@DiscriminatorValue(value = NotificationEntity.Type.Values.MOVEMENT)
public class MovementNotificationEntity extends NotificationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "movement_fk")
    private MovementEntity movement;

    public MovementNotificationEntity() {
    }

    public MovementNotificationEntity(ClientEntity client, Date date, Boolean seen, MovementEntity movement) {
        super(client, date, seen);
        this.movement = movement;
    }

    @Override
    public Type getType() {
        return NotificationEntity.Type.MOVEMENT;
    }

    public MovementEntity getMovement() {
        return movement;
    }

    public void setMovement(MovementEntity movement) {
        this.movement = movement;
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.notifications.MovementNotificationEntity[ id=" + getId() + " ]";
    }

    @Override
    public MovementNotificationDTO toDTO() {
        return new MovementNotificationDTO(getId(), getDate(), getSeen(), movement.toDTO());
    }

}
