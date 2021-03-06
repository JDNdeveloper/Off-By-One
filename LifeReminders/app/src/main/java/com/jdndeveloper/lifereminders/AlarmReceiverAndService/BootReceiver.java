package com.jdndeveloper.lifereminders.AlarmReceiverAndService;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventActivities.NotificationActivity;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.MainActivity;
import com.jdndeveloper.lifereminders.phone.Phone;
import com.jdndeveloper.lifereminders.storage.SharedStorage;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Jayden Navarro on 3/2/2015.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        SharedStorage.initializeInstance(context);

        List<Notification> notifications = Storage.getInstance().getAllNotifications();

        for (Notification notif : notifications) {
            Lifestyle parentLifestyle = Storage.getInstance().getLifestyle(notif.getLifestyleContainerKey());
            Reminder parentReminder = Storage.getInstance().getReminder(notif.getReminderContainerKey());

            if (parentLifestyle.isEnabled() && parentReminder.isEnabled() && notif.isEnabled()) {
                notif.setAlarm(context);
                Storage.getInstance().replaceAbstractBaseEvent(notif);
            }

            //delete notification if it is one time and the setting is enabled for that
            if (Storage.getInstance().getOption(Constants.OPTION_TEST_KEY4).isEnabled()
                    && (!notif.isRepeatDaysEnabled() && !notif.isRepeatEveryBlankDaysEnabled())) {
                Storage.getInstance().deleteAbstractBaseEvent(notif);
            }
        }

        /*
        List<Lifestyle> lifestyles = Storage.getInstance().getAllLifestyles();
        lifestyles.add(Storage.getInstance().getLifestyle(Constants.LIFESTYLE_FAILED_KEY));

        for (Lifestyle life : lifestyles) {
            //life.setAlarms(context);
            //Log.e("BootReceiver", life.getName()); logs can't happen here :'(
            if (life.isEnabled()) {
                for (String reminderKey : life.getReminders()) {
                    Reminder reminder = Storage.getInstance().getReminder(reminderKey);
                    if (reminder.isEnabled()) {
                        for (String notifKey : reminder.getNotificationKeys()) {
                            Notification notif = Storage.getInstance().getNotification(notifKey);
                            //properlySetNotif(notif, context);
                            if (notif.isEnabled()) {
                                notif.setAlarm(context);
                            }
                        }
                    }
                }
            }
        }

        Reminder unsortedReminder = Storage.getInstance().getReminder(Constants.REMINDER_FAILED_KEY);
        for (String unsortedNotifKey : unsortedReminder.getNotificationKeys()) {
            Notification unsortedNotif = Storage.getInstance().getNotification(unsortedNotifKey);
            //properlySetNotif(unsortedNotif, context);
            if (unsortedNotif.isEnabled()) {
                unsortedNotif.setAlarm(context);
            }
        }

        //Phone.getInstance(context).sendMessageToNotificationBar("Hello", "boot-up successful", 234, "Reminder_01");

        //COMMENT OUT BELOW CODE, JUST FOR TESTING TO SEE IF RECEIVER WORKS
        //Intent mainIntent = new Intent(context, MainActivity.class);
        //context.startActivity(mainIntent);

        */
    }

    /*public void properlySetNotif(Notification notif, Context context) {
        if (notif.isEnabled() == false) return;


        Calendar rightNow = Calendar.getInstance();
        Calendar notifTime = notif.getTime();
        Calendar newTime = Calendar.getInstance();

        if (notif.isRepeatDaysEnabled()) {
            newTime.set(Calendar.HOUR_OF_DAY, notifTime.get(Calendar.HOUR_OF_DAY));
            newTime.set(Calendar.MINUTE, notifTime.get(Calendar.MINUTE));
            notif.setTime(newTime);
            notif.makeInitialCorrectDay();
            notif.setAlarm(context);
            Storage.getInstance().commitAbstractBaseEvent(notif);
        } else if (notifTime.getTimeInMillis() < rightNow.getTimeInMillis()) {
            while (notif.getTime().getTimeInMillis() < rightNow.getTimeInMillis()) {
                notif.makeNextNotificationTime();
            }
            notif.setAlarm(context);
            Storage.getInstance().commitAbstractBaseEvent(notif);
        } else {
            notif.setAlarm(context);
        }
    }*/
}