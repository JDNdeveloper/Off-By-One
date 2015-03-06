package com.jdndeveloper.lifereminders.AlarmReceiverAndService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventTypes.Action;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.interfaces.StorageInterface;
import com.jdndeveloper.lifereminders.storage.SharedStorage;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.Calendar;

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

        SharedStorage.initializeInstance(context);
        Log.i("AlarmReceiver", "in Receiver");
        //Log.i("AlarmReceiver", notifKey);
        if(notifKey == null){
            Log.i("AlarmReceiver", "notifKey is null");
        }else{
            Log.i("AlarmReceiver", notifKey);
            Calendar rightNow = Calendar.getInstance();
            Notification n = Storage.getInstance().getNotification(notifKey);


            /*Check if n is enabled, still does not check if actionkey is valid, need to know what the
            const value is called*/
            Lifestyle lifeContainer = Storage.getInstance().getLifestyle(n.getLifestyleContainerKey());
            Reminder reminderContainer = Storage.getInstance().getReminder(n.getReminderContainerKey());
            Action action = Storage.getInstance().getAction(n.getActionKey());
            if(n.isEnabled() && lifeContainer.isEnabled() && reminderContainer.isEnabled()
                    && !Constants.NOTIFICATION_FAILED_KEY.equals(notifKey)
                    //&& Constants.LIFESTYLE_FAILED_KEY != lifeContainer.getKey()
                    //&& Constants.REMINDER_FAILED_KEY != reminderContainer.getKey()
                    && !Constants.ACTION_FAILED_KEY.equals(action.getKey())) {

                Log.i("AlarmReceiver", "Valid notification");
                //TEMPORARY - Sprint 1 Presentation - REMOVE AFTER STORAGE IS FUNCTIONAL

                //n.setLifestyleContainerKey(Constants.LIFESTYLE_TEST_KEY);
                //n.setReminderContainerKey(Constants.REMINDER_TEST_KEY);
                //n.setActionKey(Constants.ACTION_TEST_KEY);

                ///END OF TEMPORARY

                //if (rightNow.getTimeInMillis() - 60000 <= n.getTime().getTimeInMillis())
                    //action.setNotificationSound(true);
                    //Storage.getInstance().replaceAbstractBaseEvent(action);
                    n.sendNotification(context);
                //set next alarm - Uncomment to add set next alarm functionality
                if(n.isRepeating()) {
                    n.makeNextNotificationTime(); //make sure not null, implies no next time if null
                    n.setAlarm(context);
                }else{
                    //COMMENTED OUT DELETE TEST NOTIFICATION CODE
                    //Storage.getInstance().deleteAbstractBaseEvent(n);
                    // This is the proper way to use delete/commit/replace - please follow this example - john
                    // and ideally the proper way to use Storage, but Storage is more your own taste
                    /*StorageInterface storageInterface = Storage.getInstance();
                    if (storageInterface.deleteAbstractBaseEvent(n) == false){
                        //Toast.makeText(context, "AlarmReceiver deletion of " + n.getKey() + " failed.", Toast.LENGTH_SHORT).show();
                        Log.e("AlarmReciever", "AlarmReceiver deletion of " + n.getKey() + " failed.");
                    }*/
                }

                //Uncomment after storage is working - tell storage to save the new notification time
                //Storage.getInstance().replaceNotification(n, n.getKey());
            }else{
                Log.i("AlarmReceiver", "Invalid notification");
                if(!n.isEnabled()){
                    Log.i("AlarmReceiver", "notification not Enabled");
                }
                if(!lifeContainer.isEnabled()){
                    Log.i("AlarmReceiver", "lifestyle not Enabled");
                }
                if(!reminderContainer.isEnabled()){
                    Log.i("AlarmReceiver", "reminder not Enabled");
                }
                if(!(Constants.NOTIFICATION_FAILED_KEY != notifKey)){
                    Log.i("AlarmReceiver", "Notification key is Failed");
                }
                if(!(Constants.ACTION_FAILED_KEY != action.getKey())){
                    Log.i("AlarmReceiver", "Action Key is Failed");
                }
            }
        }


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