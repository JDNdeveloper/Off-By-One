package com.jdndeveloper.lifereminders.EventTypes;

import com.jdndeveloper.lifereminders.Utilities.CalendarEvent;
import java.util.Calendar;

/**
 * Created by jgemig on 1/27/2015.
 * Modified by Jayden Navarro on 1/28/2015.
 */
public class Notification extends AbstractBaseEvent {

    private String name = "DEFAULT NOTIFICATION NAME"; //notification doesn't use this
    private String key = "NOTIFICATION_DEFAULT_KEY";
    private boolean enabled = true;

    private Action action;
    private CalendarEvent calendar;

    private boolean repeating = false;

    private boolean[] repeatDays; //0 Sunday, 1 Monday, 2 Tuesday, ... TRUE if enabled

    private int repeatEveryBlankDays; //1 means daily, 2 means once every two days, ...


    public Notification() {
        action = new Action();
        repeatDays = new boolean[7];
        repeatEveryBlankDays = 0;
        calendar = new CalendarEvent();
    }

    public CalendarEvent getNextNotification() { //returns null if non-repeating
        if (!repeating) return null;
        return new CalendarEvent();
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action newAction) {
        action = newAction;
    }

    public CalendarEvent getTime() {
        return calendar;
    }

    public void setTime(CalendarEvent newCalendar) {
        calendar = newCalendar;
    }

    public boolean isRepeating() {
        return repeating;
    }

    public void setRepeating(boolean repeating) {
        repeating = repeating;
    }

    public boolean[] getRepeatDays() {
        return repeatDays;
    }

    public void setRepeatDay(int dayToRepeat, boolean isRepeat) {
        repeatDays[dayToRepeat] = isRepeat;
    }

    public int getRepeatEveryBlankDays() {
        return repeatEveryBlankDays;
    }

    public void setRepeatEveryBlankDays(int repeatEveryBlankDays) {
        repeatEveryBlankDays = repeatEveryBlankDays;
    }

}
