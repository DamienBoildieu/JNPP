package jnpp.controller.views;

import java.util.Calendar;
import jnpp.dao.entities.notifications.AppointmentNotificationEntity;
import jnpp.dao.entities.notifications.MessageNotificationEntity;
import jnpp.dao.entities.notifications.MovementNotificationEntity;
import jnpp.dao.entities.notifications.NotificationEntity;
import jnpp.dao.entities.notifications.OverdraftNotificationEntity;
import jnpp.dao.entities.notifications.PaymentMeanNotificationEntity;

/**
 *
 * @author Damien
 */
public class NotifView {
    private final int year;
    private final int month;
    private final int day;
    private final String message;
    
    public NotifView(NotificationEntity notif) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(notif.getDate());
        year = cal.get(Calendar.YEAR);
        month = (cal.get(Calendar.MONTH)+1);
        day = cal.get(Calendar.DAY_OF_MONTH);
        switch (notif.getType()) {
            case APPOINTMENT:
                AppointmentNotificationEntity appoint = (AppointmentNotificationEntity) notif;
                message = "Vous avez rendez-vous avec conseiller le " + appoint.getAppointment().getDate().toString();
                break;
            case PAYMENT_MEAN:
                PaymentMeanNotificationEntity mean = (PaymentMeanNotificationEntity) notif;    
                message = "PaymentMean";
                break;
            case MESSAGE:
                MessageNotificationEntity msg = (MessageNotificationEntity) notif;    
                message = "Vous avez un reçu un nouveau message";
                break;
            case MOVEMENT:
                MovementNotificationEntity move = (MovementNotificationEntity) notif;                    
                message = "movement";
                break;
            case OVERDRAFT:
                OverdraftNotificationEntity over = (OverdraftNotificationEntity) notif;
                message = "overdraft";
                break;
            default:
                throw new AssertionError(notif.getType().name());     
        }
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getMessage() {
        return message;
    }
    
    
}
