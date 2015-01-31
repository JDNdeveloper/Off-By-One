package com.jdndeveloper.lifereminders.Tests;

import android.content.Context;
import android.util.Log;

import com.jdndeveloper.lifereminders.EventTypes.Notification;

import java.util.Calendar;

/**
 * Created by Jayden Navarro on 1/31/2015.
 */
public class NotificationTester {

    public void runTest(Context context) {
        Notification notification = new Notification();

        //runDaysOfTheWeekTest(notification);
        //runEveryBlankDaysTest(notification);
        //runNotificationTest(context, notification);
        //alarmTester(context, notification);
    }

    private void runDaysOfTheWeekTest(Notification notification) {

        notification.setRepeatDay(Calendar.MONDAY, true);
        notification.setRepeatDay(Calendar.FRIDAY, true);
        notification.setRepeatDay(Calendar.TUESDAY, true);

        Calendar tempCal = Calendar.getInstance();

        tempCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        notification.setTime(tempCal);

        Log.i("NotifTest", "Testing Days Of Week");

        Log.i("NotifTest", Integer.toString(notification.getTime().get(Calendar.DAY_OF_WEEK)));

        Log.i("NotifTest", Integer.toString(notification.getNextNotificationTime().get(Calendar.DAY_OF_WEEK)));

        Log.i("NotifTest", Integer.toString(notification.getNextNotificationTime().get(Calendar.DAY_OF_WEEK)));

        Log.i("NotifTest", Integer.toString(notification.getNextNotificationTime().get(Calendar.DAY_OF_WEEK)));

        Log.i("NotifTest", Integer.toString(notification.getNextNotificationTime().get(Calendar.DAY_OF_WEEK)));
    }

    private void runEveryBlankDaysTest(Notification notification) {
        notification.setRepeatEveryBlankDays(3);

        Log.i("NotifTest", "Testing Every Blank Days: 3");

        Log.i("NotifTest", Integer.toString(notification.getTime().get(Calendar.DAY_OF_WEEK)));

        Log.i("NotifTest", Integer.toString(notification.getNextNotificationTime().get(Calendar.DAY_OF_WEEK)));

        Log.i("NotifTest", Integer.toString(notification.getNextNotificationTime().get(Calendar.DAY_OF_WEEK)));

        Log.i("NotifTest", Integer.toString(notification.getNextNotificationTime().get(Calendar.DAY_OF_WEEK)));

        Log.i("NotifTest", Integer.toString(notification.getNextNotificationTime().get(Calendar.DAY_OF_WEEK)));
    }

    private void runNotificationTest(Context context, Notification notification) {
        notification.sendNotification(context, "Testing 123", "8:00 am");
    }

    private void alarmTester(Context context, Notification notification) {
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.add(Calendar.MINUTE, 1);
        newCalendar.set(Calendar.SECOND, 00);
        notification.setTime(newCalendar);
        notification.setAlarm(context);
    }

}
