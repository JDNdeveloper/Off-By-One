package com.jdndeveloper.lifereminders.Tests;

import android.util.Log;

import com.google.gson.Gson;
import com.jdndeveloper.lifereminders.EventTypes.Action;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jgemig on 2/13/2015.
 */
public class GsonTester {

////////////////////////////////////////////////////////////////////////////////////////////////////
//        http://mvnrepository.com/artifact/com.google.code.gson/gson
//        http://stackoverflow.com/questions/16608135/android-studio-add-jar-as-library
//        https://www.youtube.com/watch?v=1MyBO9z7ojk
//        http://stackoverflow.com/questions/16608135/android-studio-add-jar-as-library
////////////////////////////////////////////////////////////////////////////////////////////////////

    public GsonTester() {
    }

    public static void test() {

        Log.e("MainActivity","onCreate json test begin");

        Lifestyle lifestyle = new Lifestyle();
        Lifestyle lifestyle2 = Storage.getInstance().getNewLifeStyle();
        Storage.getInstance().commitNewLifestyle(lifestyle2);

        Reminder reminder = new Reminder();

        ArrayList<String> L = new ArrayList<String>();
        L.add("GSON_Test1");
        L.add("GSON_Test2");

        reminder.setNotificationKeys(L);

        Reminder reminder2 = Storage.getInstance().getNewReminder();
        Storage.getInstance().commitNewReminder(reminder2);

        Notification notification = new Notification();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);

        notification.setTime(cal);

        Notification notification2 = Storage.getInstance().getNewNotification();


        Action action = new Action();
        Action action2 = Storage.getInstance().getNewAction();

        Gson gsonObject = new Gson();

        String lifestyleGson = gsonObject.toJson(lifestyle);
        String lifestyle2Gson = gsonObject.toJson(lifestyle2);

        String reminderGson = gsonObject.toJson(reminder);
        String reminder2Gson = gsonObject.toJson(reminder2);

        String notificationGson = gsonObject.toJson(notification);
        String notification2Gson = gsonObject.toJson(notification2);

        String actionGson = gsonObject.toJson(action);
        String action2Gson = gsonObject.toJson(action2);

        Log.e("GsonTester","onCreate gson test lifestyle [" + lifestyleGson + "]");
        Log.e("GsonTester","onCreate gson test lifestyle2 [" + lifestyle2Gson + "]");
/*
        Log.e("GsonTester","onCreate gson test lifestyle1 [" +
                gsonObject.toJson(Storage.getInstance().getLifestyle("Lifestyle_01"))+ "]");
        Log.e("GsonTester","onCreate gson test lifestyle2 [" +
                gsonObject.toJson(Storage.getInstance().getLifestyle("Lifestyle_02"))+ "]");
        Log.e("GsonTester","onCreate gson test lifestyle3 [" +
                gsonObject.toJson(Storage.getInstance().getLifestyle("Lifestyle_03"))+ "]");
        Log.e("GsonTester","onCreate gson test Test_Lifestyle_01 [" +
                gsonObject.toJson(Storage.getInstance().getLifestyle("Test_Lifestyle_01"))+ "]");
        Log.e("GsonTester","onCreate gson test Failed_Lifestyle_01 [" +
                gsonObject.toJson(Storage.getInstance().getLifestyle("Failed_Lifestyle_01"))+ "]");

        Log.e("GsonTester","onCreate gson test Reminder_01 [" +
                gsonObject.toJson(Storage.getInstance().getReminder("Reminder_01"))+ "]");
        Log.e("GsonTester","onCreate gson test Reminder_02 [" +
                gsonObject.toJson(Storage.getInstance().getReminder("Reminder_02"))+ "]");
        Log.e("GsonTester","onCreate gson test Reminder_03 [" +
                gsonObject.toJson(Storage.getInstance().getReminder("Reminder_03"))+ "]");
        Log.e("GsonTester","onCreate gson test Reminder_04 [" +
                gsonObject.toJson(Storage.getInstance().getReminder("Reminder_04"))+ "]");
        Log.e("GsonTester","onCreate gson test Test_Reminder_01 [" +
                gsonObject.toJson(Storage.getInstance().getReminder("Test_Reminder_01"))+ "]");
        Log.e("GsonTester","onCreate gson test Failed_Reminder_01 [" +
                gsonObject.toJson(Storage.getInstance().getReminder("Failed_Reminder_01"))+ "]");

        Log.e("GsonTester","onCreate gson test Notification_01 [" +
                gsonObject.toJson(Storage.getInstance().getNotification("Notification_01"))+ "]");
        Log.e("GsonTester","onCreate gson test Notification_02 [" +
                gsonObject.toJson(Storage.getInstance().getNotification("Notification_02"))+ "]");
        Log.e("GsonTester","onCreate gson test Notification_03 [" +
                gsonObject.toJson(Storage.getInstance().getNotification("Notification_03"))+ "]");
        Log.e("GsonTester","onCreate gson test Test_Notification_01 [" +
                gsonObject.toJson(Storage.getInstance().getNotification("Test_Notification_01"))+ "]");
        Log.e("GsonTester","onCreate gson test Failed_Notification_01 [" +
                gsonObject.toJson(Storage.getInstance().getNotification("Failed_Notification_01"))+ "]");

        Log.e("GsonTester","onCreate gson test Test_Action_01 [" +
                gsonObject.toJson(Storage.getInstance().getAction("Test_Action_01"))+ "]");
        Log.e("GsonTester","onCreate gson test Failed_Action_01 [" +
                gsonObject.toJson(Storage.getInstance().getAction("Failed_Action_01"))+ "]");
*/
        Log.e("GsonTester","onCreate gson test reminder [" + reminderGson + "]");
        Log.e("GsonTester","onCreate gson test reminder2 [" + reminder2Gson + "]");

        Log.e("GsonTester","onCreate gson test notification [" + notificationGson + "]");
        Log.e("GsonTester","onCreate gson test notification2 [" + notification2Gson + "]");

        Log.e("GsonTester","onCreate gson test action [" + actionGson + "]");
        Log.e("GsonTester","onCreate gson test action2 [" + action2Gson + "]");

        Lifestyle lifestyleFromGson = gsonObject.fromJson(lifestyleGson, Lifestyle.class);
        Lifestyle lifestyle2FromGson = gsonObject.fromJson(lifestyle2Gson, Lifestyle.class);
        Reminder reminderFromGson = gsonObject.fromJson(reminderGson, Reminder.class);
        Reminder reminder2FromGson = gsonObject.fromJson(reminder2Gson, Reminder.class);
        Notification notificationFromGson = gsonObject.fromJson(notificationGson, Notification.class);
        Notification notification2FromGson = gsonObject.fromJson(notification2Gson, Notification.class);
        Action actionFromGson = gsonObject.fromJson(actionGson, Action.class);
        Action action2FromGson = gsonObject.fromJson(action2Gson, Action.class);

        Log.e("GsonTester","onCreate gson test lifestyleFromGson [" + lifestyleFromGson.getKey() + "]");
        Log.e("GsonTester","onCreate gson test lifestyleFromGson [" + lifestyleFromGson.getName() + "]");
        Log.e("GsonTester","onCreate gson test lifestyle2FromGson [" + lifestyle2FromGson.getKey() + "]");
        Log.e("GsonTester","onCreate gson test lifestyle2FromGson [" + lifestyle2FromGson.getName() + "]");

        Log.e("GsonTester","onCreate gson test reminderFromGson [" + reminderFromGson.getKey() + "]");
        Log.e("GsonTester","onCreate gson test reminderFromGson [" + reminderFromGson.getName() + "]");
        Log.e("GsonTester","onCreate gson test reminderFromGson [" + reminderFromGson.getNotificationKeys() + "]");
        Log.e("GsonTester","onCreate gson test reminder2FromGson [" + reminder2FromGson.getKey() + "]");
        Log.e("GsonTester","onCreate gson test reminder2FromGson [" + reminder2FromGson.getName() + "]");

        Log.e("GsonTester","onCreate gson test notificationFromGson [" + notificationFromGson.getKey() + "]");
        Log.e("GsonTester","onCreate gson test notificationFromGson [" + notificationFromGson.getName() + "]");
        Log.e("GsonTester","onCreate gson test notificationFromGson [" + notificationFromGson.getTime() + "]");
        Log.e("GsonTester","onCreate gson test notification2FromGson [" + notification2FromGson.getKey() + "]");
        Log.e("GsonTester","onCreate gson test notification2FromGson [" + notification2FromGson.getName() + "]");

        Log.e("GsonTester","onCreate gson test actionFromGson [" + actionFromGson.getKey() + "]");
        Log.e("GsonTester","onCreate gson test actionFromGson [" + actionFromGson.getName() + "]");
        Log.e("GsonTester","onCreate gson test action2FromGson [" + action2FromGson.getKey() + "]");
        Log.e("GsonTester","onCreate gson test action2FromGson [" + action2FromGson.getName() + "]");

        Log.e("GsonTester","onCreate json test end");
    }
}
