package com.jdndeveloper.lifereminders.storage;

import android.util.Log;

import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventTypes.Action;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.interfaces.StorageInterface;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jgemig on 1/27/2015.
 */
public class Storage implements StorageInterface {
    private static Storage ourInstance = new Storage();

    public static StorageInterface getInstance() {
        return ourInstance;
    }

    private Storage() {
    }

    // temporary fake lifestyle
    String All_Lifestyles = "Lifestyle_01,Lifestyle_02,Lifestyle_03";

    // test set for unit tests
    String Failed_Lifestyle_01 = "Failed Lifestyle,false,Failed_Reminder_01";
    String Failed_Reminder_01 = "Failed Reminder,false,Failed_Notification_01";
    String Failed_Notification_01 = "Failed Notification,false";
    String Failed_Action_01 = "Failed Action,false";

    String Test_Lifestyle_01 = "Test Lifestyle 01,false,Test_Reminder_01";
    String Test_Reminder_01 = "Test Reminder 01,false,Test_Notification_01";
    String Test_Notification_01 = "Test Notification,false";
    String Test_Action_01 = "Test Action,false";

    String Lifestyle_01 = "Happy Time,false,Reminder_01,Reminder_02,Reminder_03,Reminder_04";
    String Lifestyle_02 = "UCSC,true,Reminder_01,Reminder_02,Reminder_03,Reminder_04";
    String Lifestyle_03 = "Vacation,true,Reminder_01,Reminder_02,Reminder_03,Reminder_04";

    String Reminder_01 = "Scrum Meeting,true,Notification_01,Notification_02,Notification_03";

    String Notification_01 = "time,action";
    String Notification_02 = "time,action";
    String Notification_03 = "time,action";

    private List<String> toArrayList(String string){
        return new ArrayList<String>(Arrays.asList(string.split("\\,")));
    }

    private String retrieveKey(String key, String type){
        if (key != null && type != null){
            if (!key.contains(type)) return null;
            if (key == Constants.LIFESTYLE_TEST_KEY) return Test_Lifestyle_01;
            if (key == Constants.REMINDER_TEST_KEY) return Test_Reminder_01;
            if (key == Constants.NOTIFICATION_TEST_KEY) return Test_Notification_01;
            if (key == Constants.ACTION_TEST_KEY) return Test_Action_01;

            if (key == "Lifestyle_01") return Lifestyle_01;
            if (key == "Lifestyle_02") return Lifestyle_02;
            if (key == "Lifestyle_03") return Lifestyle_03;
        }
        return null;
    }

    @Override
    public Lifestyle getLifestyle(String key) {
        // temporary
        List<String> decodedString;
        if (retrieveKey(key, "Lifestyle") != null) {
            decodedString = toArrayList(retrieveKey(key, "Lifestyle"));
        }
        else {
            decodedString = toArrayList(Failed_Lifestyle_01);
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
    public List<String> getAllLifestyles() {
        return toArrayList(All_Lifestyles);
    }

    @Override
    public List<String> getAllReminders() {
        return null;
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
