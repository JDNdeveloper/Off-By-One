package com.jdndeveloper.lifereminders.Tests;

import android.util.Log;

import com.google.gson.Gson;
import com.jdndeveloper.lifereminders.EventTypes.Action;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.storage.Storage;

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

        Reminder reminder = new Reminder();
        Reminder reminder2 = Storage.getInstance().getNewReminder();

        Notification notification = new Notification();
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

        Log.e("MainActivity","onCreate gson test lifestyle [" + lifestyleGson + "]");
        Log.e("MainActivity","onCreate gson test lifestyle2 [" + lifestyle2Gson + "]");

        Log.e("MainActivity","onCreate gson test reminder [" + reminderGson + "]");
        Log.e("MainActivity","onCreate gson test reminder2 [" + reminder2Gson + "]");

        Log.e("MainActivity","onCreate gson test notification [" + notificationGson + "]");
        Log.e("MainActivity","onCreate gson test notification2 [" + notification2Gson + "]");

        Log.e("MainActivity","onCreate gson test action [" + actionGson + "]");
        Log.e("MainActivity","onCreate gson test action2 [" + action2Gson + "]");

        Lifestyle lifestyleFromGson = gsonObject.fromJson(lifestyleGson, Lifestyle.class);
        Lifestyle lifestyle2FromGson = gsonObject.fromJson(lifestyle2Gson, Lifestyle.class);
        Reminder reminderFromGson = gsonObject.fromJson(reminderGson, Reminder.class);
        Reminder reminder2FromGson = gsonObject.fromJson(reminder2Gson, Reminder.class);
        Notification notificationFromGson = gsonObject.fromJson(notificationGson, Notification.class);
        Notification notification2FromGson = gsonObject.fromJson(notification2Gson, Notification.class);
        Action actionFromGson = gsonObject.fromJson(actionGson, Action.class);
        Action action2FromGson = gsonObject.fromJson(action2Gson, Action.class);

        Log.e("MainActivity","onCreate gson test lifestyleFromGson [" + lifestyleFromGson.getKey() + "]");
        Log.e("MainActivity","onCreate gson test lifestyleFromGson [" + lifestyleFromGson.getName() + "]");
        Log.e("MainActivity","onCreate gson test lifestyle2FromGson [" + lifestyle2FromGson.getKey() + "]");
        Log.e("MainActivity","onCreate gson test lifestyle2FromGson [" + lifestyle2FromGson.getName() + "]");

        Log.e("MainActivity","onCreate gson test reminderFromGson [" + reminderFromGson.getKey() + "]");
        Log.e("MainActivity","onCreate gson test reminderFromGson [" + reminderFromGson.getName() + "]");
        Log.e("MainActivity","onCreate gson test reminder2FromGson [" + reminder2FromGson.getKey() + "]");
        Log.e("MainActivity","onCreate gson test reminder2FromGson [" + reminder2FromGson.getName() + "]");

        Log.e("MainActivity","onCreate gson test notificationFromGson [" + notificationFromGson.getKey() + "]");
        Log.e("MainActivity","onCreate gson test notificationFromGson [" + notificationFromGson.getName() + "]");
        Log.e("MainActivity","onCreate gson test notification2FromGson [" + notification2FromGson.getKey() + "]");
        Log.e("MainActivity","onCreate gson test notification2FromGson [" + notification2FromGson.getName() + "]");

        Log.e("MainActivity","onCreate gson test actionFromGson [" + actionFromGson.getKey() + "]");
        Log.e("MainActivity","onCreate gson test actionFromGson [" + actionFromGson.getName() + "]");
        Log.e("MainActivity","onCreate gson test action2FromGson [" + action2FromGson.getKey() + "]");
        Log.e("MainActivity","onCreate gson test action2FromGson [" + action2FromGson.getName() + "]");

        Log.e("MainActivity","onCreate json test end");
    }
}