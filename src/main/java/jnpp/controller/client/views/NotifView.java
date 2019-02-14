package jnpp.controller.client.views;

import java.util.Calendar;
import java.util.Map;

import jnpp.controller.client.views.Translator.Language;
import jnpp.service.dto.notifications.AppointmentNotificationDTO;
import jnpp.service.dto.notifications.MessageNotificationDTO;
import jnpp.service.dto.notifications.MovementNotificationDTO;
import jnpp.service.dto.notifications.NotificationDTO;
import jnpp.service.dto.notifications.OverdraftNotificationDTO;
import jnpp.service.dto.notifications.PaymentMeanNotificationDTO;
import jnpp.service.dto.paymentmeans.PaymentMeanDTO;

/**
 * Vue des notifications
 */
public class NotifView {

    /**
     * L'année de la notification
     */
    private final int year;
    /**
     * Le mois de la notification
     */
    private final int month;
    /**
     * Le jour de la notification
     */
    private final int day;
    /**
     * Le message de la notification
     */
    private final String message;
    /**
     * Indique si la notification a été vue
     */
    private final boolean seen;

    /**
     * Constructeur
     *
     * @param notif la notification
     */
    public NotifView(NotificationDTO notif) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(notif.getDate());
        year = cal.get(Calendar.YEAR);
        month = (cal.get(Calendar.MONTH) + 1);
        day = cal.get(Calendar.DAY_OF_MONTH);
        seen = notif.getSeen();
        switch (notif.getType()) {
        case APPOINTMENT:
            AppointmentNotificationDTO appoint = (AppointmentNotificationDTO) notif;
            message = "Vous avez rendez-vous avec conseiller le "
                    + appoint.getAppointment().getDate().toString();
            break;
        case PAYMENT_MEAN:
            PaymentMeanNotificationDTO mean = (PaymentMeanNotificationDTO) notif;
            Map<PaymentMeanDTO.Type, String> types = Translator.getInstance()
                    .translatePaymentMean(Language.FR);
            switch (mean.getPaymentMean().getStatus()) {
            case ORDERED:
                message = "Votre " + types.get(mean.getPaymentMean().getType())
                        + " est commandé";
                break;
            case ARRIVED:
                message = "Votre " + types.get(mean.getPaymentMean().getType())
                        + " est arrivé";
                break;
            case DELIVERED:
                message = "Vous avez récupéré votre "
                        + types.get(mean.getPaymentMean().getType());
                break;
            default:
                throw new AssertionError(
                        mean.getPaymentMean().getStatus().name());
            }
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
