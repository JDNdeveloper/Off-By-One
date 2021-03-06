package com.jdndeveloper.lifereminders.EventTypes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jdndeveloper.lifereminders.AlarmReceiverAndService.AlarmReceiver;
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

    private Context mContext;

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
        newCalendar.set(Calendar.SECOND, 0);
        newCalendar.set(Calendar.MINUTE, 59);
        newCalendar.set(Calendar.HOUR_OF_DAY, 23);
        newCalendar.set(Calendar.DAY_OF_MONTH, 1);
        newCalendar.set(Calendar.MONTH, 1);
        newCalendar.set(Calendar.YEAR, 2000);

        calendar = newCalendar;

    }

    //returns original calendar if non-repeating, sets calendar to next day that repeats
    //and returns it.
    public Calendar makeNextNotificationTime(Context context) {

        if (!this.isEnabled()) return calendar;

        if (repeatDaysEnabled) {
            makeNextRepeatDays();
        } else if (repeatEveryBlankDaysEnabled) {
            makeNextRepeatEveryBlankDays();
        } else {
            return calendar;
        }

        this.setAndroidAlarm(context);

        return calendar;
    }

    //Sets initial calendar to instance event will happen, only for repeatDays
    public Calendar makeInitialCorrectDay() {
        if (!repeatDaysEnabled) return null;

        while (!repeatDays.contains(calendar.get(Calendar.DAY_OF_WEEK))) {
            calendar.add(Calendar.DATE, 1);
        }

        return calendar;
    }

    //removes alarm from the phone
    public boolean removeAlarm(Context context) {
        mContext = context;
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

        String title = buildTitle();
        String subTitle = buildSubTitle();

        Storage.getInstance().getAction(this.getActionKey()).sendCorrectNotification(context,
                title,
                subTitle,
                requestID,
                reminderContainerKey);
    }

    private String buildTitle() {
        String title = Storage.getInstance().getReminder(reminderContainerKey).getName();

        if (Storage.getInstance().getReminder(reminderContainerKey)
                .getKey().equals(Constants.REMINDER_FAILED_KEY)) {
            if (!isRepeatDaysEnabled() && !isRepeatEveryBlankDaysEnabled()) {
                title = "One time Notification";
            } else {
                title = getRepeatText();
            }
        }

        return title;
    }

    private String buildSubTitle() {
        String subTitle = Storage.getInstance().getLifestyle(lifestyleContainerKey).getName();

        if (Storage.getInstance().getReminder(reminderContainerKey)
                .getKey().equals(Constants.REMINDER_FAILED_KEY)) {
            if (!isRepeatDaysEnabled() && !isRepeatEveryBlankDaysEnabled()) {
                subTitle = getRepeatText();
            } else {
                subTitle = getAlarmTypeText();
            }
        } else if (Storage.getInstance().getLifestyle(lifestyleContainerKey)
                .getKey().equals(Constants.LIFESTYLE_FAILED_KEY)) {
            subTitle = getRepeatText();
        }

        return subTitle;
    }

    private boolean isRepeatDaily() {
        if (isRepeatEveryBlankDaysEnabled()) {
            if (repeatEveryBlankDays == 1) return true;
        } else if (isRepeatDaysEnabled()) {
            if (repeatDays.contains(Calendar.MONDAY)
                    && repeatDays.contains(Calendar.TUESDAY)
                    && repeatDays.contains(Calendar.WEDNESDAY)
                    && repeatDays.contains(Calendar.THURSDAY)
                    && repeatDays.contains(Calendar.FRIDAY)
                    && repeatDays.contains(Calendar.SATURDAY)
                    && repeatDays.contains(Calendar.SUNDAY))
                return true;
        }

        return false;
    }

    private String getRepeatText() {
        //if (!n.isRepeating()) return "";
        String text = "";
        if (this.isRepeatDaily()) {
            text = "Repeat daily";
        } else if (this.isRepeatDaysEnabled()) {
            text += getWeekDays(this.getRepeatDays());
        } else if (this.isRepeatEveryBlankDaysEnabled()) {
            text += "Repeat every " + this.getRepeatEveryBlankDays() + " days";
        } else {
            text += getCalendarDate(calendar);
        }

        return text;
    }

    private String getCalendarDate(Calendar c) {
        String text = "Date: ";
        text += Integer.toString(c.get(Calendar.MONTH) + 1);
        text += "/";
        text += Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        return text;
    }

    private String getAlarmTypeText() {
        Action a = Storage.getInstance().getAction(this.getActionKey());
        String text = "";
        if (a.isNotificationSound()) {
            text += "sound";
        } else if (a.isVibrate()) {
            text += "vibrate";
        } else {
            text += "silent";
        }

        if (a.isNotificationSound() && a.isVibrate())
            text = "sound/vibrate";

        return text;
    }

    private String getWeekDays(ArrayList<Integer> repeatDays) {
        final String[] DAYS_IN_WEEK = {"", "Sun", "Mon", "Tue",
                "Wed", "Thu", "Fri", "Sat"};

        String text = "";
        boolean started = false;
        for (int i = 0; i <= 7; i++) {
            if (repeatDays.contains(i)) {
                if (started) {
                    text += ", ";
                }
                text += DAYS_IN_WEEK[i];
                started = true;
            }
        }
        return text;
    }

    //sets an alarm for the current scheduled time of the notification
    public void setAlarm(Context context) {
        Calendar rightNow = Calendar.getInstance();

        if (!this.isEnabled()) return;


        if (this.isRepeatDaysEnabled()) {
            setupRepeatDays(rightNow);

        } else if (this.isRepeatEveryBlankDaysEnabled()) {
            setupRepeatEveryBlankDays(rightNow);
        }

        while (calendar.getTimeInMillis() < rightNow.getTimeInMillis()) {
            if (repeatDaysEnabled) {
                makeNextRepeatDays();
            } else if (repeatEveryBlankDaysEnabled) {
                makeNextRepeatEveryBlankDays();
            } else {
                return;
            }
        }


        this.setAndroidAlarm(context);

        //cleanup

        //if (!this.isRepeatEveryBlankDaysEnabled() && !this.isRepeatDaysEnabled()) {
            //calendar.set(Calendar.YEAR, 2000); //set the time firmly in the past to avoid using it again
        //}
    }

    private void setAndroidAlarm(Context context) {
        //Setup the intent, it must be a pending intent
        Intent myIntent = new Intent(context, AlarmReceiver.class);

        myIntent.putExtra("NOTIF_KEY", this.getKey()); //Josh, use getExtras to retrieve this

        Scanner in = new Scanner(this.getKey()).useDelimiter("[^0-9]+");
        int requestID = in.nextInt();
        Log.e("Notification", Integer.toString(requestID));


        //cancels before setting again
        //this.removeAlarm(context);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestID,
                myIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        //Create the AlarmManager
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //print the time that the alarm is schedule for
        Log.i("Notification", "NotifKey: " + this.getKey() + " Time Alarm is set for: " + calendar.toString());

        //sets the seconds to 0 so alarm goes off as soon as the minute switches over
        calendar.set(Calendar.SECOND, 0);

        //Set the alarm
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void setupRepeatEveryBlankDays(Calendar rightNow) {
        while (calendar.getTimeInMillis() > rightNow.getTimeInMillis()) {
            //subtracts in one day increments
            for (int i = 0; i < repeatEveryBlankDays; i++) {
                calendar.add(Calendar.DATE, -1); //sets one day back
            }
        }
    }

    private void setupRepeatDays(Calendar rightNow) {

        Calendar newCalendar = (Calendar) rightNow.clone();

        newCalendar.add(Calendar.DATE, -1); // sets one day back

        newCalendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        newCalendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));

        calendar = (Calendar) newCalendar.clone();
    }

    private void makeNextRepeatDays() {
        do {
            calendar.add(Calendar.DATE, 1);
        } while (!repeatDays.contains(calendar.get(Calendar.DAY_OF_WEEK)));
    }

    private void makeNextRepeatEveryBlankDays() {
        for (int i = 0; i < repeatEveryBlankDays; i++) {
            calendar.add(Calendar.DATE, 1);
        }
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

    @Override
    public void clean() {
        // fill with whatever needs to be cleaned/removed on object deletion from storage
        if (mContext != null)
            this.removeAlarm(mContext);
        calendar = null;
        Log.e("Notification", "clean() called");
    }
}
