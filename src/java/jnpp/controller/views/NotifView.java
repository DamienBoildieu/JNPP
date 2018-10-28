/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.controller.views;

import java.util.Calendar;
import jnpp.dao.entities.notifications.AppointmentNotification;
import jnpp.dao.entities.notifications.MessageNotification;
import jnpp.dao.entities.notifications.MovementNotification;
import jnpp.dao.entities.notifications.Notification;
import jnpp.dao.entities.notifications.OverdraftNotification;
import jnpp.dao.entities.notifications.PaymentMeanNotification;

/**
 *
 * @author Damien
 */
public class NotifView {
    private final int year;
    private final int month;
    private final int day;
    private final String message;
    
    public NotifView(Notification notif) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(notif.getDate());
        year = cal.get(Calendar.YEAR);
        month = (cal.get(Calendar.MONTH)+1);
        day = cal.get(Calendar.DAY_OF_MONTH);
        switch (notif.getType()) {
            case APPOINTMENT:
                AppointmentNotification appoint = (AppointmentNotification) notif;
                message = "Vous avez rendez-vous avec conseiller le " + appoint.getAppointment().getDate().toString();
                break;
            case PAYMENT_MEAN:
                PaymentMeanNotification mean = (PaymentMeanNotification) notif;    
                message = "PaymentMean";
                break;
            case MESSAGE:
                MessageNotification msg = (MessageNotification) notif;    
                message = "Vous avez un reçu un nouveau message";
                break;
            case MOVEMENT:
                MovementNotification move = (MovementNotification) notif;                    
                message = "movement";
                break;
            case OVERDRAFT:
                OverdraftNotification over = (OverdraftNotification) notif;
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
