package com.jdndeveloper.lifereminders.storage;

import android.util.Log;

import com.google.gson.Gson;
import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventTypes.AbstractBaseEvent;
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
//    String Failed_Reminder_01 = "Failed Reminder,false,Failed_Notification_01";
//    String Failed_Notification_01 = "Failed Notification,false";
//    String Failed_Action_01 = "Failed Action,false";

//    String Test_Lifestyle_01 = "Test Lifestyle 01,true,Test_Reminder_01";
//    String Test_Reminder_01 = "Test Reminder 01,true,Test_Notification_01";
//    String Test_Notification_01 = "Test Notification,true";
//    String Test_Action_01 = "Test Action,true";

//    String Lifestyle_01 = "Happy Time,false,Reminder_01,Reminder_02,Reminder_03,Reminder_04";
//    String Lifestyle_02 = "UCSC,true,Reminder_01,Reminder_02,Reminder_03,Reminder_04";
//    String Lifestyle_03 = "Vacation,true,Reminder_01,Reminder_02,Reminder_03,Reminder_04";

//    String Reminder_01 = "Scrum Meeting,true,Notification_01,Notification_02,Notification_03";
//    String Reminder_02 = "Potty Break,true,Notification_01,Notification_02,Notification_03";
//    String Reminder_03 = "Time out,true,Notification_01,Notification_02,Notification_03";
//    String Reminder_04 = "Stuff,true,Notification_01,Notification_02,Notification_03";

//    String Notification_01 = "Notification 1,true,time,action";
//    String Notification_02 = "Notification 2,true,time,action";
//    String Notification_03 = "Notification 3,false,time,action";

    private List<String> toArrayList(String string){
        return new ArrayList<String>(Arrays.asList(string.split("\\,")));
    }

//    private String retrieveKey(String key, String type){
//        if (key != null && type != null){
//            if (!key.contains(type)) return null;
//            if (key == Constants.LIFESTYLE_TEST_KEY ||
//                    key.contentEquals("Test_Lifestyle_01")) return Test_Lifestyle_01;
//            if (key == Constants.REMINDER_TEST_KEY ||
//                    key.contentEquals("Test_Reminder_01")) return Test_Reminder_01;
//            if (key == Constants.NOTIFICATION_TEST_KEY ||
//                    key.contentEquals("Test_Notification_01")) return Test_Notification_01;
//            if (key == Constants.ACTION_TEST_KEY) return Test_Action_01;

//            if (key.contentEquals("Lifestyle_01")) return Lifestyle_01;
//            if (key.contentEquals("Lifestyle_02")) return Lifestyle_02;
//            if (key.contentEquals("Lifestyle_03")) return Lifestyle_03;
//            if (key.contentEquals("Failed_Lifestyle_01")) return Failed_Lifestyle_01;

//            if (key.contentEquals("Reminder_01")) return Reminder_01;
//            if (key.contentEquals("Reminder_02")) return Reminder_02;
//            if (key.contentEquals("Reminder_03")) return Reminder_03;
//            if (key.contentEquals("Reminder_04")) return Reminder_04;
//            if (key.contentEquals("Failed_Reminder_01")) return Failed_Reminder_01;

//            if (key.contentEquals("Notification_01")) return Notification_01;
//            if (key.contentEquals("Notification_02")) return Notification_02;
//            if (key.contentEquals("Notification_03")) return Notification_03;
//            if (key.contentEquals("Failed_Notification_01")) return Failed_Notification_01;
//        }
//        return null;
//    }

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

        String failedLifestyleGsonString = sharedStorageInstance.getSharedPreferenceKey("Failed_Lifestyle_01");
        return gsonObject.fromJson(lifestyleGsonString, Lifestyle.class);

        // temporary
//        List<String> decodedString;

//        if (retrieveKey(key, "Lifestyle") != null) {
//            decodedString = toArrayList(retrieveKey(key, "Lifestyle"));
//        } else {
//            String failedLifestyleGsonString = sharedStorageInstance.getSharedPreferenceKey("Failed_Lifestyle_01");
//            return gsonObject.fromJson(lifestyleGsonString, Lifestyle.class);
//            decodedString = toArrayList(Failed_Lifestyle_01);
//        }
/*        Lifestyle lifestyle = new Lifestyle();
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
        return null;
*/    }

    @Override
    public Reminder getReminder(String key) {
        String reminderGsonString = sharedStorageInstance.getSharedPreferenceKey(key);

        if (reminderGsonString != null)
            return gsonObject.fromJson(reminderGsonString, Reminder.class);

        String failedReminderGsonString = sharedStorageInstance.getSharedPreferenceKey("Failed_Reminder_01");
        return gsonObject.fromJson(failedReminderGsonString, Reminder.class);
    }

    @Override
    public Notification getNotification(String key) {
        String notificationGsonString = sharedStorageInstance.getSharedPreferenceKey(key);

        if (notificationGsonString != null)
            return gsonObject.fromJson(notificationGsonString, Notification.class);

        String failedNotificationGsonString = sharedStorageInstance.getSharedPreferenceKey("Failed_Reminder_01");
        return gsonObject.fromJson(failedNotificationGsonString, Notification.class);
    }

    @Override
    public Action getAction(String key) {
        String actionGsonString = sharedStorageInstance.getSharedPreferenceKey(key);

        if (actionGsonString != null)
            return gsonObject.fromJson(actionGsonString, Action.class);

        String failedActionGsonString = sharedStorageInstance.getSharedPreferenceKey("Failed_Reminder_01");
        return gsonObject.fromJson(failedActionGsonString, Action.class);
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
    public boolean replaceAbstractBaseEvent(AbstractBaseEvent abstractBaseEvent) {
        sharedStorageInstance.saveAbstractBaseEvent(abstractBaseEvent);
        return false;
    }

    @Override
    public Lifestyle getNewLifeStyle() {
        Lifestyle lifestyle = new Lifestyle();

        String key = sharedStorageInstance.getNewLifestyleKey();

        lifestyle.setName("New " + key);
        lifestyle.setKey(key);
        lifestyle.setEnabled(true);
        lifestyle.setReminders(new ArrayList<String>());

        return lifestyle;
    }

    @Override
    public Reminder getNewReminder(){
        Reminder reminder = new Reminder();

        String key = sharedStorageInstance.getNewReminderKey();

        reminder.setName("New " + key);
        reminder.setKey(key);
        reminder.setEnabled(true);
        reminder.setNotificationKeys(new ArrayList<String>());

        return reminder;
    }

    @Override
    public Notification getNewNotification() {
        Notification notification = new Notification();

        String key = sharedStorageInstance.getNewNotificationKey();

        notification.setName("New " + key);
        notification.setKey(key);
        notification.setEnabled(true);

        return notification;
    }

    @Override
    public Action getNewAction() {
        Action action = new Action();

        String key = sharedStorageInstance.getNewActionKey();

        action.setName("New " + key);
        action.setKey(key);
        action.setEnabled(true);

        return action;
    }

    @Override
    public void commitNewLifestyle(Lifestyle lifestyle) {
        sharedStorageInstance.commitNewLifestyle(lifestyle);
    }

    @Override
    public void commitNewReminder(Reminder reminder) {
        sharedStorageInstance.commitNewReminder(reminder);
    }

    @Override
    public void commitNewNotification(Notification notification) {

    }

    @Override
    public void commitNewAction(Action action) {

    }
}
