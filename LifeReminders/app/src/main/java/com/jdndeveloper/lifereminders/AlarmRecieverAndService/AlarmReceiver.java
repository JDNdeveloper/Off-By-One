package com.jdndeveloper.lifereminders.AlarmRecieverAndService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.storage.Storage;

/**
 * Created by Josh Innis on 1/27/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    /*When the BR receives an intent, go to MyAlarmService*/
    @Override
    public void onReceive(Context context, Intent intent) {
        //Intent service = new Intent(context, AlarmService.class);
        //context.startService(service);

        String notifKey = intent.getStringExtra("NOTIF_KEY");

        //Log.i("AlarmReceiver", notifKey);
        if(notifKey == null){
            Log.i("AlarmReceiver: ", "notifKey is null");

        }else{
            Log.i("AlarmReceiver: ", notifKey);
            Notification n = Storage.getInstance().getNotification(notifKey);
            /*Check if n is enabled, still does not check if actionkey is valid, need to know what the
            const value is called*/
            if(n.isEnabled() && Constants.Failed_Notification_01 != notifKey) {
                Log.i("AlarmReceiver: ", "Valid notification");
                //TEMPORARY - Sprint 1 Presentation - REMOVE AFTER STORAGE IS FUNCTIONAL

                n.setLifestyleContainerKey(Constants.LIFESTYLE_TEST_KEY);
                n.setReminderContainerKey(Constants.REMINDER_TEST_KEY);
                n.setActionKey(Constants.ACTION_TEST_KEY);

                ///END OF TEMPORARY

                n.sendNotification(context);
            }else{
                Log.i("AlarmReceiver: ", "Invalid notification");
            }
        }


        //set next alarm - Uncomment to add set next alarm functionality
        //n.makeNextNotificationTime();
        //n.setAlarm(context);

        //Uncomment after storage is working - tell storage to save the new notification time
        //Storage.getInstance().replaceNotification(n, n.getKey());
    }
}


/*This is how you would setup an alarm to go off a minute later*/
/*
//Setup the calendar to one minute later
Calendar calendar = Calendar.getInstance();
calendar.set(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, Calendar.getInstance().get(Calendar.MINUTE) + 1);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.getInstance().get(Calendar.DAY_OF_WEEK));



        //Setup the intent, it must be a pending intent
        Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent,0);

        //Create the AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //Set the alarm
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
*/