package jnpp.service.dto;

import jnpp.dao.entities.MessageEntity;

public class MessageDTO {
    
    public static enum Direction {
        
        CLIENT_TO_ADVISOR,
        ADVISOR_TO_CLIENT;
        
    }
        
    private final Direction direction;
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
        content = message.getContent();
    }

    public Direction getDirection() {
        return direction;
    }

    public String getContent() {
        return content;
    }
    
}
