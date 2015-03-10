package com.jdndeveloper.lifereminders.AlarmReceiverAndService;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventTypes.Action;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.MainActivity;
import com.jdndeveloper.lifereminders.interfaces.StorageInterface;
import com.jdndeveloper.lifereminders.storage.SharedStorage;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Josh Innis on 1/27/2015.
 *
 */
public class AlarmReceiver extends BroadcastReceiver {

    /*When the BR receives an intent, go to MyAlarmService*/
    @Override
    public void onReceive(Context context, Intent intent) {

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

            //TEMPORARY - makes sure that the notification is in the keychain
            boolean isInKeychain = false;
            List<Notification> notifications = Storage.getInstance().getAllNotifications();
            for (Notification notif : notifications) {
                if (notif.getKey().equals(n.getKey())) {
                    isInKeychain = true;
                    break;
                }
            }

            /*Check if n is enabled, still does not check if actionkey is valid, need to know what the
            const value is called*/
            Lifestyle lifeContainer = Storage.getInstance().getLifestyle(n.getLifestyleContainerKey());
            Reminder reminderContainer = Storage.getInstance().getReminder(n.getReminderContainerKey());
            Action action = Storage.getInstance().getAction(n.getActionKey());
            if(n.isEnabled() && lifeContainer.isEnabled() && reminderContainer.isEnabled()
                    && !Constants.NOTIFICATION_FAILED_KEY.equals(notifKey)
                    //&& Constants.LIFESTYLE_FAILED_KEY != lifeContainer.getKey()
                    //&& Constants.REMINDER_FAILED_KEY != reminderContainer.getKey()
                    && !Constants.ACTION_FAILED_KEY.equals(action.getKey())
                    && isInKeychain) {

                Log.i("AlarmReceiver", "Valid notification");
                //TEMPORARY - Sprint 1 Presentation - REMOVE AFTER STORAGE IS FUNCTIONAL

                //n.setLifestyleContainerKey(Constants.LIFESTYLE_TEST_KEY);
                //n.setReminderContainerKey(Constants.REMINDER_TEST_KEY);
                //n.setActionKey(Constants.ACTION_TEST_KEY);

                ///END OF TEMPORARY

                if (rightNow.getTimeInMillis() - 60000 <= n.getTime().getTimeInMillis())
                    n.sendNotification(context);
                //set next alarm - Uncomment to add set next alarm functionality
                n.makeNextNotificationTime(context);

                //delete notification if it is one time and the setting is enabled for that
                if (Storage.getInstance().getOption(Constants.OPTION_TEST_KEY4).isEnabled()
                        && (!n.isRepeatDaysEnabled() && !n.isRepeatEveryBlankDaysEnabled())) {
                    Storage.getInstance().deleteAbstractBaseEvent(n);

                    //If main is open we will relaunch to ensure that notification is removed
                    if (MainActivity.activityIsVisible()) {
                        Intent mainIntent = new Intent(context, MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(mainIntent);
                    }

                }
                    //COMMENTED OUT DELETE TEST NOTIFICATION CODE
                    //Storage.getInstance().deleteAbstractBaseEvent(n);
                    // This is the proper way to use delete/commit/replace - please follow this example - john
                    // and ideally the proper way to use Storage, but Storage is more your own taste
                    /*StorageInterface storageInterface = Storage.getInstance();
                    if (storageInterface.deleteAbstractBaseEvent(n) == false){
                        //Toast.makeText(context, "AlarmReceiver deletion of " + n.getKey() + " failed.", Toast.LENGTH_SHORT).show();
                        Log.e("AlarmReciever", "AlarmReceiver deletion of " + n.getKey() + " failed.");
                    }*/


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
                if(Constants.NOTIFICATION_FAILED_KEY.equals(notifKey)){
                    Log.i("AlarmReceiver", "Notification key is Failed");
                }
                if(Constants.ACTION_FAILED_KEY.equals(action.getKey())){
                    Log.i("AlarmReceiver", "Action Key is Failed");
                }
            }
        }


    }
}