package com.jdndeveloper.lifereminders.Utilities;

/**
 * Created by Jayden Navarro on 1/30/2015.
 */
public class CalendarEvent {

    private int dayOfWeek;
    private int hours; //military time
    private int minutes;

    public CalendarEvent() {}

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int timeMinutes) {
        this.minutes = timeMinutes;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int timeHours) {
        this.hours = timeHours;
    }

}
