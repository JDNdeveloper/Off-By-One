package com.jdndeveloper.lifereminders.storage;

import android.util.Log;

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

    public static StorageInterface getInstance() {
        return ourInstance;
    }

    private Storage() {
    }

    // temporary fake lifestyle
    String All_LifeStyles = "LifeStyle_01,LifeStyle_02";

    // test set for unit tests
    String Failed_Lifestyle_01 = "Failed Lifestyle,false,Failed_Reminder_01";
    String Failed_Reminder_01 = "Failed Reminder,false,Failed_Notification_01";
    String Failed_Notification_01 = "Failed Notification,false";
    String Failed_Action_01 = "Failed Action,false";

    String Test_Lifestyle_01 = "Test Lifestyle 01,false,Test_Reminder_01";
    String Test_Reminder_01 = "Test Reminder 01,false,Test_Notification_01";
    String Test_Notification_01 = "Test Notification,false";
    String Test_Action_01 = "Test Action,false";

    String LifeStyle_01 = "Happy Time,false,Reminder_01,Reminder_02,Reminder_03,Reminder_04";
    String LifeStyle_02 = "UCSC,true,Reminder_01,Reminder_02,Reminder_03,Reminder_04";

    String Reminder_01 = "Scrum Meeting,true,Notification_01,Notification_02,Notification_03";

    String Notification_01 = "time,action";
    String Notification_02 = "time,action";
    String Notification_03 = "time,action";

    private List<String> toArrayList(String string){
        return new ArrayList<String>(Arrays.asList(string.split("\\,")));
    }
    private String retrieveKey(String key){
        if (key != null){
            if (key.contentEquals("Test_Lifestyle_01")) return Test_Lifestyle_01;
            if (key.contentEquals("Test_Reminder_01")) return Test_Reminder_01;
            if (key.contentEquals("Test_Notification_01")) return Test_Notification_01;
            if (key.contentEquals("Test_Action_01")) return Test_Action_01;
        }
        return null;
    }
    @Override
    public Lifestyle getLifestyle(String key) {
        // temporary
        List<String> encodedLifeStyle;
        if (retrieveKey(key) != null) {
            encodedLifeStyle = toArrayList(retrieveKey(key));
        }
        else {
            encodedLifeStyle = toArrayList(Failed_Lifestyle_01);
        }

        Lifestyle lifestyle = new Lifestyle();
        // temp
        lifestyle.setKey(key);
        int index = 0;
        lifestyle.setName(encodedLifeStyle.get(index++));
        lifestyle.setEnabled(Boolean.valueOf(encodedLifeStyle.get(index++)));

        ArrayList<String> reminderKeys = new ArrayList<String>();
        while (index < encodedLifeStyle.size()) {
            reminderKeys.add(encodedLifeStyle.get(index++));
        }
        lifestyle.setReminders(reminderKeys);
        return lifestyle;
    }

    @Override
    public Reminder getReminder(String key) {
        // temporary
        List<String> encodedReminder;
        if (retrieveKey(key) != null) {
            encodedReminder = toArrayList(retrieveKey(key));
        }
        else {
            encodedReminder = toArrayList(Failed_Reminder_01);
        }

        Reminder reminder = new Reminder();
        // temp
        reminder.setKey(key);
        int index = 0;
        reminder.setName(encodedReminder.get(index++));
        reminder.setEnabled(Boolean.valueOf(encodedReminder.get(index++)));

        ArrayList<String> notificationKeys = new ArrayList<String>();
        while (index < encodedReminder.size()){
            notificationKeys.add(encodedReminder.get(index++));
        }
        reminder.setNotificationKeys(notificationKeys);
        return reminder;
    }

    @Override
    public Notification getNotification(String key) {
        Notification notification = new Notification();
        notification.setKey(key);
        return notification;
    }

    @Override
    public Action getAction(String key) {
        return new Action();
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
    public Lifestyle getNewLifeStyle() {

        return new Lifestyle();
    }

    @Override
    public Reminder getNewReminder(){
        return new Reminder();
    }

    @Override
    public Notification getNewNotification() {

        return new Notification();
    }

    @Override
    public Action getNewAction() {
        return null;
    }
}
