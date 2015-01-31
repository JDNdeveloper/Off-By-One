package com.jdndeveloper.lifereminders.EventTypes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.jdndeveloper.lifereminders.AlarmRecieverAndService.AlarmReceiver;
import com.jdndeveloper.lifereminders.MainActivity;
import com.jdndeveloper.lifereminders.R;
import com.jdndeveloper.lifereminders.Utilities.CalendarEvent;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by jgemig on 1/27/2015.
 * Modified by Jayden Navarro on 1/28/2015.
 */
public class Notification extends AbstractBaseEvent {

    private Action action;
    private Calendar calendar;

    private ArrayList<Integer> repeatDays; //Uses Calendar static day values
    private boolean repeatDaysEnabled;

    private int repeatEveryBlankDays; //1 means daily, 2 means once every two days, ...
    private boolean repeatEveryBlankDaysEnabled;


    public Notification() {
        super("DEFAULT NOTIFICATION NAME", "DEFAULT_NOTIFICATION_KEY", true);

        action = new Action();

        repeatDays = new ArrayList<Integer>(7);
        repeatDaysEnabled = false;

        repeatEveryBlankDays = 0;
        repeatEveryBlankDaysEnabled = false;

        calendar = Calendar.getInstance();
    }

    //returns null if non-repeating, sets calendar to next day that repeats and returns it
    public Calendar getNextNotificationTime() {
        if (repeatDaysEnabled) {
            do {
                //might not loop back around to the first day of week
                calendar.add(Calendar.DAY_OF_WEEK, 1);
            } while (!repeatDays.contains(calendar.get(Calendar.DAY_OF_WEEK)));
        } else if (repeatEveryBlankDaysEnabled) {
            calendar.add(Calendar.DAY_OF_WEEK, repeatEveryBlankDays);
        }

        return calendar;
    }

    public void sendNotification(Context context, String title, String text) {
        action.sendCorrectNotification(context, title, text);
    }

    public void setAlarm(Context context) {
        //Setup the intent, it must be a pending intent
        Intent myIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent,0);

        //Create the AlarmManager
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //Set the alarm
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action newAction) {
        if (newAction == null) return;
        action = newAction;
    }

    public Calendar getTime() {
        return calendar;
    }

    public void setTime(Calendar newCalendar) {
        calendar = newCalendar;
    }

    public ArrayList<Integer> getRepeatDays() {
        return repeatDays;
    }

    public void setRepeatDay(int dayToRepeat, boolean isRepeat) {
        int i = repeatDays.indexOf(dayToRepeat);

        if (isRepeat) {
            repeatDaysEnabled = true;
            if (i == -1) {
                repeatDays.add(dayToRepeat);
            }
        } else {
            if (i != -1) {
                repeatDays.remove(i);
            }
            if (repeatDays.size() == 0) {
                repeatDaysEnabled = false;
            }
        }
    }

    public int getRepeatEveryBlankDays() {
        return repeatEveryBlankDays;
    }

    public void setRepeatEveryBlankDays(int newRepeatEveryBlankDays) {
        if (newRepeatEveryBlankDays < 1 || newRepeatEveryBlankDays > 7) {
            repeatEveryBlankDaysEnabled = false;
            repeatEveryBlankDays = 0;
            return;
        } else {
            repeatEveryBlankDaysEnabled = true;
            repeatEveryBlankDays = newRepeatEveryBlankDays;
        }
    }

    public boolean isRepeating() {
        return (repeatDaysEnabled || repeatEveryBlankDaysEnabled);
    }

}
