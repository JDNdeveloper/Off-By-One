package com.jdndeveloper.lifereminders.EventTypes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jdndeveloper.lifereminders.AlarmRecieverAndService.AlarmReceiver;
import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Created by jgemig on 1/27/2015.
 * Modified by Jayden Navarro on 1/28/2015.
 */
public class Notification extends AbstractBaseEvent {

    private Calendar calendar;

    private String lifestyleContainerKey;
    private String reminderContainerKey;
    private String actionKey;

    private ArrayList<Integer> repeatDays; //Uses Calendar static day values

    private boolean repeatDaysEnabled;

    private int repeatEveryBlankDays; //1 means daily, 2 means once every two days, ...
    private boolean repeatEveryBlankDaysEnabled;

    //sets everything to defaults
    public Notification() {
        super("DEFAULT NOTIFICATION NAME", "DEFAULT_NOTIFICATION_KEY", true);

        lifestyleContainerKey = Constants.LIFESTYLE_FAILED_KEY;
        reminderContainerKey = Constants.REMINDER_FAILED_KEY;
        actionKey = Constants.ACTION_FAILED_KEY;

        repeatDays = new ArrayList<Integer>(7);
        repeatDaysEnabled = false;

        repeatEveryBlankDays = 0;
        repeatEveryBlankDaysEnabled = false;

        Calendar newCalendar = Calendar.getInstance();
        newCalendar.set(Calendar.SECOND, 59);
        newCalendar.set(Calendar.MINUTE, 59);
        newCalendar.set(Calendar.HOUR_OF_DAY, 23);
        newCalendar.set(Calendar.DAY_OF_MONTH, 1);
        newCalendar.set(Calendar.MONTH, 1);
        newCalendar.set(Calendar.YEAR, 2000);

        calendar = newCalendar;

    }

    //returns original calendar if non-repeating, sets calendar to next day that repeats
    //and returns it.
    public Calendar makeNextNotificationTime() {

        if (repeatDaysEnabled) {
            do {
                calendar.add(Calendar.DAY_OF_WEEK, 1);
            } while (!repeatDays.contains(calendar.get(Calendar.DAY_OF_WEEK)));
        } else if (repeatEveryBlankDaysEnabled) {
            calendar.add(Calendar.DAY_OF_WEEK, repeatEveryBlankDays);
        } else {
            calendar = null;
        }

        return calendar;
    }

    //Sets initial calendar to instance event will happen, only for repeatDays
    public Calendar makeInitialCorrectDay() {
        if (!repeatDaysEnabled) return null;

        while (!repeatDays.contains(calendar.get(Calendar.DAY_OF_WEEK))) {
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }

        return calendar;
    }

    //removes alarm from the phone
    public boolean removeAlarm(Context context) {
        Intent myIntent = new Intent(context, AlarmReceiver.class);

        myIntent.putExtra("NOTIF_KEY", this.getKey()); //Josh, use getExtras to retrieve this

        Scanner in = new Scanner(this.getKey()).useDelimiter("[^0-9]+");
        int requestID = in.nextInt();
        Log.e("Notification", Integer.toString(requestID));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestID,
                myIntent, 0);

        //Create the AlarmManager
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        return true;
    }

    //Sends a notifications with the given title and text
    public void sendNotification(Context context) {
        Scanner in = new Scanner(this.getKey()).useDelimiter("[^0-9]+");
        int requestID = in.nextInt();

        Storage.getInstance().getAction(Constants.ACTION_TEST_KEY).sendCorrectNotification(context,
                Storage.getInstance().getReminder(reminderContainerKey).getName(),
                Storage.getInstance().getLifestyle(lifestyleContainerKey).getName(),
                requestID);
    }

    //sets an alarm for the current scheduled time of the notification
    public void setAlarm(Context context) {
        //Setup the intent, it must be a pending intent
        Intent myIntent = new Intent(context, AlarmReceiver.class);

        myIntent.putExtra("NOTIF_KEY", this.getKey()); //Josh, use getExtras to retrieve this

        Scanner in = new Scanner(this.getKey()).useDelimiter("[^0-9]+");
        int requestID = in.nextInt();
        Log.e("Notification", Integer.toString(requestID));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestID,
                myIntent, 0);

        //Create the AlarmManager
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //Set the alarm
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public String getActionKey() {
        return actionKey;
    }

    public void setActionKey(String newActionKey) {
        actionKey = newActionKey;
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

    public String getLifestyleContainerKey() {
        return lifestyleContainerKey;
    }

    public void setLifestyleContainerKey(String lifestyleContainerKey) {
        this.lifestyleContainerKey = lifestyleContainerKey;
    }

    public String getReminderContainerKey() {
        return reminderContainerKey;
    }

    public void setReminderContainerKey(String reminderContainerKey) {
        this.reminderContainerKey = reminderContainerKey;
    }

    public boolean isRepeatEveryBlankDaysEnabled() {
        return repeatEveryBlankDaysEnabled;
    }

    public boolean isRepeatDaysEnabled() {
        return repeatDaysEnabled;
    }

    public void setRepeatDaysEnabled(boolean repeatDaysEnabled) {
        this.repeatDaysEnabled = repeatDaysEnabled;
    }

    public void setRepeatEveryBlankDaysEnabled(boolean repeatEveryBlankDaysEnabled) {
        this.repeatEveryBlankDaysEnabled = repeatEveryBlankDaysEnabled;
    }

}
