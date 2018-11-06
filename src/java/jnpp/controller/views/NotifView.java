package jnpp.controller.views;

import java.util.Calendar;
import java.util.Map;
import jnpp.controller.SessionController;
import jnpp.controller.views.Translator.Language;
import jnpp.service.dto.notifications.AppointmentNotificationDTO;
import jnpp.service.dto.notifications.MessageNotificationDTO;
import jnpp.service.dto.notifications.MovementNotificationDTO;
import jnpp.service.dto.notifications.NotificationDTO;
import jnpp.service.dto.notifications.OverdraftNotificationDTO;
import jnpp.service.dto.notifications.PaymentMeanNotificationDTO;
import jnpp.service.dto.paymentmeans.PaymentMeanDTO;

/**
 *
 * @author Damien
 */
public class NotifView {
    private final int year;
    private final int month;
    private final int day;
    private final String message;
    private final boolean seen;
    
    public NotifView(NotificationDTO notif) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(notif.getDate());
        year = cal.get(Calendar.YEAR);
        month = (cal.get(Calendar.MONTH)+1);
        day = cal.get(Calendar.DAY_OF_MONTH);
        seen = notif.getSeen();
        switch (notif.getType()) {
            case APPOINTMENT:
                AppointmentNotificationDTO appoint = (AppointmentNotificationDTO) notif;
                message = "Vous avez rendez-vous avec conseiller le " + appoint.getAppointment().getDate().toString();
                break;
            case PAYMENT_MEAN:
                PaymentMeanNotificationDTO mean = (PaymentMeanNotificationDTO) notif;
                Map<PaymentMeanDTO.Type, String> types = Translator.getInstance().translatePaymentMean(Language.FR);
                message = "Votre " + types.get(mean.getPaymentMean().getType()) + " est arrivé";
                break;
            case MESSAGE:
                MessageNotificationDTO msg = (MessageNotificationDTO) notif;    
                message = "Vous avez un reçu un nouveau message";
                break;
            case MOVEMENT:
                MovementNotificationDTO move = (MovementNotificationDTO) notif;
                message = "Une transaction a eu lieu sur un de vos compte";
                break;
            case OVERDRAFT:
                OverdraftNotificationDTO over = (OverdraftNotificationDTO) notif;
                message = "Vous êtes en négatif sur le compte " + over.getRib();
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

    public boolean isSeen() {
        return seen;
    }
}
