package jnpp.dao.entities.notifications;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jnpp.dao.entities.movements.Movement;

@Entity
@DiscriminatorValue(value = Notification.Type.Values.MOVEMENT)
public class MovementNotification extends Notification implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    @JoinColumn(name = "movement_fk")
    private Movement movement;

    @Override
    public Type getType() {
        return Notification.Type.MOVEMENT;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.notifications.MovementNotification[ id=" + getId() + " ]";
    }
    
}
