package jnpp.dao.entities.notifications;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import jnpp.dao.entities.advisor.MessageEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.service.dto.notifications.MessageNotificationDTO;

@Entity
@DiscriminatorValue(value = NotificationEntity.Type.Values.MESSAGE)
public class MessageNotificationEntity extends NotificationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "message_fk")
    private MessageEntity message;

    public MessageNotificationEntity() {}
    
    public MessageNotificationEntity(ClientEntity client, Date date, Boolean seen, MessageEntity message) {
        super(client, date, seen);
        this.message = message;
    }
    
    @Override
    public Type getType() {
        return NotificationEntity.Type.MESSAGE;
    }

    public MessageEntity getMessage() {
        return message;
    }

    public void setMessage(MessageEntity message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.notifications.MessageNotificationEntity[ id=" + getId() + " ]";
    }
    
    @Override
    public MessageNotificationDTO toDTO() {
        return new MessageNotificationDTO(getId(), getDate(), message.toDTO());
    }
    
}
