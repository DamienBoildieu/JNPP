package jnpp.service.dto.notifications;

import java.util.Date;

public abstract class NotificationDTO {
    
    public static enum Type {
        
        APPOINTMENT,
        PAYMENT_MEAN,
        MESSAGE,
        MOVEMENT,
        OVERDRAFT;
        
    }
    
    private Long id;
    private Date date;

    public NotificationDTO(Long id, Date date) {
        this.id = id;
        this.date = date;
    }
    
    public abstract Type getType();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
