package com.jdndeveloper.lifereminders.storage;

import android.util.Log;

import com.google.gson.Gson;
import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventTypes.Action;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.interfaces.StorageInterface;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jgemig on 1/27/2015.
 */
public class Storage implements StorageInterface {
    private static Storage ourInstance = new Storage();
    private static Gson gsonObject = new Gson();

    public static StorageInterface getInstance() {
        return ourInstance;
    }

    private static SharedStorage sharedStorageInstance = SharedStorage.getInstance();

    private Storage() {

    }

    // temporary fake lifestyle

    // test set for unit tests
//    String Failed_Lifestyle_01 = "Failed Lifestyle,false,Failed_Reminder_01";
    String Failed_Reminder_01 = "Failed Reminder,false,Failed_Notification_01";
    String Failed_Notification_01 = "Failed Notification,false";
    String Failed_Action_01 = "Failed Action,false";

//    String Test_Lifestyle_01 = "Test Lifestyle 01,true,Test_Reminder_01";
    String Test_Reminder_01 = "Test Reminder 01,true,Test_Notification_01";
    String Test_Notification_01 = "Test Notification,true";
    String Test_Action_01 = "Test Action,true";

//    String Lifestyle_01 = "Happy Time,false,Reminder_01,Reminder_02,Reminder_03,Reminder_04";
//    String Lifestyle_02 = "UCSC,true,Reminder_01,Reminder_02,Reminder_03,Reminder_04";
//    String Lifestyle_03 = "Vacation,true,Reminder_01,Reminder_02,Reminder_03,Reminder_04";

    String Reminder_01 = "Scrum Meeting,true,Notification_01,Notification_02,Notification_03";
    String Reminder_02 = "Potty Break,true,Notification_01,Notification_02,Notification_03";
    String Reminder_03 = "Time out,true,Notification_01,Notification_02,Notification_03";
    String Reminder_04 = "Stuff,true,Notification_01,Notification_02,Notification_03";

    String Notification_01 = "Notification 1,true,time,action";
    String Notification_02 = "Notification 2,true,time,action";
    String Notification_03 = "Notification 3,false,time,action";

    private List<String> toArrayList(String string){
        return new ArrayList<String>(Arrays.asList(string.split("\\,")));
    }

    private String retrieveKey(String key, String type){
        if (key != null && type != null){
            if (!key.contains(type)) return null;
//            if (key == Constants.LIFESTYLE_TEST_KEY ||
//                    key.contentEquals("Test_Lifestyle_01")) return Test_Lifestyle_01;
            if (key == Constants.REMINDER_TEST_KEY ||
                    key.contentEquals("Test_Reminder_01")) return Test_Reminder_01;
            if (key == Constants.NOTIFICATION_TEST_KEY ||
                    key.contentEquals("Test_Notification_01")) return Test_Notification_01;
            if (key == Constants.ACTION_TEST_KEY) return Test_Action_01;

//            if (key.contentEquals("Lifestyle_01")) return Lifestyle_01;
//            if (key.contentEquals("Lifestyle_02")) return Lifestyle_02;
//            if (key.contentEquals("Lifestyle_03")) return Lifestyle_03;
//            if (key.contentEquals("Failed_Lifestyle_01")) return Failed_Lifestyle_01;

            if (key.contentEquals("Reminder_01")) return Reminder_01;
            if (key.contentEquals("Reminder_02")) return Reminder_02;
            if (key.contentEquals("Reminder_03")) return Reminder_03;
            if (key.contentEquals("Reminder_04")) return Reminder_04;
            if (key.contentEquals("Failed_Reminder_01")) return Failed_Reminder_01;

            if (key.contentEquals("Notification_01")) return Notification_01;
            if (key.contentEquals("Notification_02")) return Notification_02;
            if (key.contentEquals("Notification_03")) return Notification_03;
            if (key.contentEquals("Failed_Notification_01")) return Failed_Notification_01;
        }
        return null;
    }

    @Override
    public List<Lifestyle> getAllLifestyles() {
        List<Lifestyle> lifestyles = new ArrayList<Lifestyle>();
        for(String lifestyle : toArrayList(sharedStorageInstance.getSharedPreferenceKey("all_lifestyles")))
            lifestyles.add(getLifestyle(lifestyle));
        return lifestyles;
    }

    @Override
    public List<Reminder> getAllReminders() {
        List<Reminder> reminders = new ArrayList<Reminder>();
        for(String reminder : toArrayList(sharedStorageInstance.getSharedPreferenceKey("all_reminders")))
            reminders.add(getReminder(reminder));
        return reminders;
    }

    @Override
    public List<Notification> getAllNotifications() {
        List<Notification> notifications = new ArrayList<Notification>();
        for(String notification : toArrayList(sharedStorageInstance.getSharedPreferenceKey("all_notifications")))
            notifications.add(getNotification(notification));
        return notifications;
    }

    @Override
    public Lifestyle getLifestyle(String key) {
        String lifestyleGsonString = sharedStorageInstance.getSharedPreferenceKey(key);

        if (lifestyleGsonString != null)
            return gsonObject.fromJson(lifestyleGsonString, Lifestyle.class);

        // temporary
        List<String> decodedString;

        if (retrieveKey(key, "Lifestyle") != null) {
            decodedString = toArrayList(retrieveKey(key, "Lifestyle"));
        } else {
            String failedLifestyleGsonString = sharedStorageInstance.getSharedPreferenceKey("Failed_Lifestyle_01");
            return gsonObject.fromJson(lifestyleGsonString, Lifestyle.class);
//            decodedString = toArrayList(Failed_Lifestyle_01);
        }
        Lifestyle lifestyle = new Lifestyle();
        // temp
        lifestyle.setKey(key);
        int index = 0;
        lifestyle.setName(decodedString.get(index++));
        lifestyle.setEnabled(Boolean.valueOf(decodedString.get(index++)));

        ArrayList<String> reminderKeys = new ArrayList<String>();
        while (index < decodedString.size()) {
            reminderKeys.add(decodedString.get(index++));
        }
        lifestyle.setReminders(reminderKeys);
        return lifestyle;
    }

    @Override
    public Reminder getReminder(String key) {
        // temporary
        List<String> decodedString;
        if (retrieveKey(key, "Reminder") != null) {
            decodedString = toArrayList(retrieveKey(key, "Reminder"));
        }
        else {
            decodedString = toArrayList(Failed_Reminder_01);
        }

        Reminder reminder = new Reminder();
        // temp
        reminder.setKey(key);
        int index = 0;
        reminder.setName(decodedString.get(index++));
        reminder.setEnabled(Boolean.valueOf(decodedString.get(index++)));

        ArrayList<String> notificationKeys = new ArrayList<String>();
        while (index < decodedString.size()){
            notificationKeys.add(decodedString.get(index++));
        }
        reminder.setNotificationKeys(notificationKeys);
        return reminder;
    }

    @Override
    public Notification getNotification(String key) {
        List<String> decodedString;
        if (retrieveKey(key, "Notification") != null) {
            decodedString = toArrayList(retrieveKey(key, "Notification"));
        }
        else {
            decodedString = toArrayList(Failed_Notification_01);
        }

        Notification notification = new Notification();
        notification.setKey(key);
        notification.setName(decodedString.get(0));
        notification.setEnabled(Boolean.valueOf(decodedString.get(1)));
        return notification;
    }

    @Override
    public Action getAction(String key) {
        List<String> decodedString;
        if (retrieveKey(key, "Action") != null) {
            decodedString = toArrayList(retrieveKey(key, "Action"));
        }
        else {
            decodedString = toArrayList(Failed_Action_01);
        }

        Action action = new Action();
        action.setKey(key);
        action.setName(decodedString.get(0));
        action.setEnabled(Boolean.valueOf(decodedString.get(1)));
        return action;
    }

    @Override
    public List<String> getCurrentAlarmKeys() {
        // temporary
        ArrayList<String> currentAlarmKeys = new ArrayList<String>();
        currentAlarmKeys.add("notification_11");
        currentAlarmKeys.add("notification_12");
        currentAlarmKeys.add("notification_13");
        currentAlarmKeys.add("notification_14");
        return currentAlarmKeys;
    }

    @Override
    public List<String> getAllKeys() {
        // temporary
        ArrayList<String> allKeys = new ArrayList<String>();
        allKeys.add("notification_01");
        allKeys.add("notification_02");
        allKeys.add("notification_03");
        allKeys.add("notification_04");
        allKeys.add("notification_11");
        allKeys.add("notification_12");
        allKeys.add("notification_13");
        allKeys.add("notification_14");
        return allKeys;
    }

    @Override
    public boolean replaceLifeStyle(Lifestyle lifestyle, String key) {

        return false;
    }

    @Override
    public boolean replaceNotification(Notification notification, String key) {

        return false;
    }

    @Override
    public boolean replaceReminder(Reminder reminder, String key) {
        return false;
    }

    @Override
    public boolean replaceAction(Action action, String key) {
        return false;
    }

    @Override
    public Lifestyle getNewLifeStyle() {

        return getLifestyle(Constants.LIFESTYLE_TEST_KEY);
    }

    @Override
    public Reminder getNewReminder(){

        return getReminder(Constants.REMINDER_TEST_KEY);
    }

    @Override
    public Notification getNewNotification() {

        return getNotification(Constants.NOTIFICATION_TEST_KEY);
    }

    @Override
    public Action getNewAction() {

        return getAction(Constants.ACTION_TEST_KEY);
    }
}
