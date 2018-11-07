package jnpp.controller.views;

import java.util.Calendar;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.dto.advisor.AppointmentDTO;

/**
 * Structure des vues des rendez-vous
 */
public class AppointView {
    /**
     * l'id du rendez-vous
     */
    private final long id;
    /**
     * L'année du rendez-vous 
     */
    private final int year;
    /**
     * Le mois du rendez-vous 
     */
    private final int month;
    /**
     * Le jour du rendez-vous 
     */
    private final int day;
    /**
     * L'heure du rendez-vous 
     */
    private final int hour;
    /**
     * La minute du rendez-vous 
     */
    private final int minute;
    /**
     * Le conseiller qui a rendez-vous
     */
    private final AdvisorDTO advisor;
   /**
    * Constructeur
    * @param appoint le rendez-vous
    */
    public AppointView(AppointmentDTO appoint) {
        id = appoint.getId();
        Calendar cal = Calendar.getInstance();
        cal.setTime(appoint.getDate());
        year = cal.get(Calendar.YEAR);
        month = (cal.get(Calendar.MONTH)+1);
        day = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        advisor = appoint.getAdvisor();
    }

    public long getId() {
        return id;
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

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public AdvisorDTO getAdvisor() {
        return advisor;
    }
}
