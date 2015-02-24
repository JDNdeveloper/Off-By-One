package com.jdndeveloper.lifereminders.Tests;

import android.content.Context;

import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.Calendar;

/**
 * Created by Jayden Navarro on 2/23/2015.
 */
public class SprintPresentationTester {

    public static void runTest(Context context) {
        //what we're going to do is schedule an alarm to go off in 1 minute, and push a notification
        Notification testNotif = Storage.getInstance().getNotification(Constants.NOTIFICATION_TEST_KEY);
        //Log.e("MainActivity", "onCreate name [" + testNotif.getName() + "]");
        //Log.e("MainActivity", "onCreate key [" + testNotif.getKey() + "]");
        testNotif.setLifestyleContainerKey(Constants.LIFESTYLE_TEST_KEY);
        //Log.e("MainActivity", "onCreate lifestyle container key [" + testNotif.getLifestyleContainerKey() + "]");
        testNotif.setReminderContainerKey(Constants.REMINDER_TEST_KEY);
        //Log.e("MainActivity", "onCreate reminder container key [" + testNotif.getReminderContainerKey() + "]");
        testNotif.setActionKey(Constants.ACTION_TEST_KEY);
        //Log.e("MainActivity", "onCreate name [" + testNotif.getActionKey() + "]");

        Calendar cal = Calendar.getInstance();

        //cal.add(Calendar.MINUTE, 1);
        cal.add(Calendar.SECOND, 10);
        //cal.set(Calendar.SECOND, 0);


        testNotif.setTime(cal);
        testNotif.setAlarm(context);

        Storage.getInstance().replaceAbstractBaseEvent(testNotif);

        Notification n = Storage.getInstance().getNotification("Notification_02");

        //cal.add(Calendar.SECOND, 20);

        n.setTime(cal);

        n.setAlarm(context);

        Storage.getInstance().replaceAbstractBaseEvent(n);
    }
}
