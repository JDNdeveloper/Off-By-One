package com.jdndeveloper.lifereminders.Tests;

import android.content.Context;
import android.util.Log;

import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.Calendar;

/**
 * Created by Jayden Navarro on 1/31/2015.
 */
public class NotificationTester {

    public static void runTest(Context context) {
        Notification notification = Storage.getInstance().getNotification(Constants.NOTIFICATION_TEST_KEY);
        notification.setActionKey(Constants.ACTION_TEST_KEY);

        //Arbitrary change

        runDaysOfTheWeekTest(notification, context);
        runEveryBlankDaysTest(notification, context);
        //runNotificationTest(context, notification);
        //alarmTester(context, notification);
    }

    private static void runDaysOfTheWeekTest(Notification notification, Context context) {

        notification.setRepeatDay(Calendar.MONDAY, true);
        notification.setRepeatDay(Calendar.FRIDAY, true);
        notification.setRepeatDay(Calendar.TUESDAY, true);

        Calendar tempCal = Calendar.getInstance();

        tempCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        notification.setTime(tempCal);

        Log.i("NotifTest", "Testing Days Of Week");

        Log.i("NotifTest", Integer.toString(notification.getTime().get(Calendar.DAY_OF_WEEK)));

        Log.i("NotifTest", Integer.toString(notification.makeNextNotificationTime(context).get(Calendar.DAY_OF_WEEK)));

        Log.i("NotifTest", Integer.toString(notification.makeNextNotificationTime(context).get(Calendar.DAY_OF_WEEK)));

        Log.i("NotifTest", Integer.toString(notification.makeNextNotificationTime(context).get(Calendar.DAY_OF_WEEK)));

        Log.i("NotifTest", Integer.toString(notification.makeNextNotificationTime(context).get(Calendar.DAY_OF_WEEK)));

    }

    private static void runEveryBlankDaysTest(Notification notification, Context context) {
        notification.setRepeatEveryBlankDays(3);

        Log.i("NotifTest", "Testing Every Blank Days: 3");

        Log.i("NotifTest", Integer.toString(notification.getTime().get(Calendar.DAY_OF_WEEK)));

        Log.i("NotifTest", Integer.toString(notification.makeNextNotificationTime(context).get(Calendar.DAY_OF_WEEK)));

        Log.i("NotifTest", Integer.toString(notification.makeNextNotificationTime(context).get(Calendar.DAY_OF_WEEK)));

        Log.i("NotifTest", Integer.toString(notification.makeNextNotificationTime(context).get(Calendar.DAY_OF_WEEK)));

        Log.i("NotifTest", Integer.toString(notification.makeNextNotificationTime(context).get(Calendar.DAY_OF_WEEK)));
    }

    private static void runNotificationTest(Context context, Notification notification) {
        notification.sendNotification(context);
    }

    private static void alarmTester(Context context, Notification notification) {
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.add(Calendar.MINUTE, 1);
        newCalendar.set(Calendar.SECOND, 00);
        notification.setTime(newCalendar);
        notification.setAlarm(context);
    }

}
