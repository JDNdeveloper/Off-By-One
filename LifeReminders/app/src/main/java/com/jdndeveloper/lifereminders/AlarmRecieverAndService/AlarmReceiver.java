package com.jdndeveloper.lifereminders.AlarmRecieverAndService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jdndeveloper.lifereminders.EventTypes.Notification;

/**
 * Created by Josh Innis on 1/27/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    /*When the BR receives an intent, go to MyAlarmService*/
    @Override
    public void onReceive(Context context, Intent intent) {
        //Intent service = new Intent(context, AlarmService.class);
        //context.startService(service);
        Notification n = new Notification();
        n.sendNotification(context,"Title","Message");
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