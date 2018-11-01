package jnpp.service.dto;

import java.util.Date;
import jnpp.dao.entities.MessageEntity;

public class MessageDTO {
    
    public static enum Direction {
        
        CLIENT_TO_ADVISOR,
        ADVISOR_TO_CLIENT;
        
    }
        
    private final Direction direction;
    private final Date date;
    private final String content;    
        
    public MessageDTO(MessageEntity message) {
        switch (message.getDirection()) {
            case ADVISOR_TO_CLIENT:
                direction = Direction.ADVISOR_TO_CLIENT;
                break;
            case CLIENT_TO_ADVISOR:
                direction = Direction.CLIENT_TO_ADVISOR;
                break;
            default:
                direction = null;
        }
        date = message.getDate();
        content = message.getContent();
    }

    public Direction getDirection() {
        return direction;
    }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }
    
}
