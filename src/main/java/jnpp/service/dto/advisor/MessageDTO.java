package jnpp.service.dto.advisor;

import java.util.Date;

public class MessageDTO {

    public static enum Direction {

        CLIENT_TO_ADVISOR, ADVISOR_TO_CLIENT;

    }

    private Direction direction;
    private Date date;
    private String content;
    private AdvisorDTO advisor;

    public MessageDTO(Direction direction, Date date, String content,
            AdvisorDTO advisor) {
        this.direction = direction;
        this.date = date;
        this.content = content;
        this.advisor = advisor;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AdvisorDTO getAdvisor() {
        return advisor;
    }

    public void setAdvisor(AdvisorDTO advisor) {
        this.advisor = advisor;
    }

}
