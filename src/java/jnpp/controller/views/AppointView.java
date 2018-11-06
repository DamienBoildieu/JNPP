/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.controller.views;

import java.util.Calendar;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.dto.advisor.AppointmentDTO;

public class AppointView {
    private final long id;
    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;
    private final AdvisorDTO advisor;
   
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
