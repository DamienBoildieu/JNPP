package jnpp.service.dto.notifications;

import java.util.Date;
import jnpp.service.dto.AbstractDTO;

public abstract class NotificationDTO extends AbstractDTO {

    public static enum Type {

        APPOINTMENT, PAYMENT_MEAN, MESSAGE, MOVEMENT, OVERDRAFT;

    }

    private Long id;
    private Date date;
    private Boolean seen;

    public NotificationDTO(Long id, Date date, Boolean seen) {
        this.id = id;
        this.date = date;
        this.seen = seen;
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

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

}
