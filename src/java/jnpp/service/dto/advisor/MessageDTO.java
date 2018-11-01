package jnpp.service.dto.advisor;

import java.util.Date;
import jnpp.dao.entities.advisor.MessageEntity;

public class MessageDTO {
    
    public static enum Direction {
        
        CLIENT_TO_ADVISOR,
        ADVISOR_TO_CLIENT;
        
    }
        
    private final Direction direction;
    private final Date date;
    private final String content;  
    private final AdvisorDTO advisor;
        
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
        advisor = new AdvisorDTO(message.getAdvisor());
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

    public AdvisorDTO getAdvisor() {
        return advisor;
    }
        
}
