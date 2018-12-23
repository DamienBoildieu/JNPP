package jnpp.service.dto.notifications;

import java.util.Date;

public class OverdraftNotificationDTO extends NotificationDTO {

    private String rib;

    public OverdraftNotificationDTO(Long id, Date date, Boolean seen, String rib) {
        super(id, date, seen);
        this.rib = rib;
    }

    @Override
    public Type getType() {
        return NotificationDTO.Type.OVERDRAFT;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

}
