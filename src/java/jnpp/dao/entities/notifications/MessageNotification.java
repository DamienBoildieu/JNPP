package jnpp.dao.entities.notifications;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jnpp.dao.entities.Message;

@Entity
@DiscriminatorValue(value = Notification.Type.Values.MESSAGE)
public class MessageNotification extends Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "message_fk")
    private Message message;

    @Override
    public Type getType() {
        return Notification.Type.MESSAGE;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.notifications.MessageNotification[ id=" + getId() + " ]";
    }
    
}
