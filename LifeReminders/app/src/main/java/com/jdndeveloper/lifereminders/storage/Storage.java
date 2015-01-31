package com.jdndeveloper.lifereminders.storage;

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

    public static Storage getInstance() {
        return ourInstance;
    }

    private Storage() {
    }

    // temporary fake lifestyle
    String All_LifeStyles = "LifeStyle_01,LifeStyle_02";

    String LifeStyle_01 = "Happy Time,false,Reminder_01,Reminder_02,Reminder_03,Reminder_04";
    String LifeStyle_02 = "UCSC,true,Reminder_01,Reminder_02,Reminder_03,Reminder_04";

    String Reminder_01 = "Scrum Meeting,true,Notification_01,Notification_02,Notification_03";

    String Notification_01 = "time,action";
    String Notification_02 = "time,action";
    String Notification_03 = "time,action";

    private List<String> toArrayList(String string){
        return new ArrayList<String>(Arrays.asList(string.split("\\s*,\\s*")));
    }
    @Override
    public Lifestyle getLifeStyle(String key) {
        // temporary
        List<String> encodedLifeStyle = toArrayList(key);
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
        // needs to be set to Reminder - not Notification
        lifestyle.setLifestyleNotifications(reminderKeys);
        return lifestyle;
    }

    @Override
    public Reminder getReminder(String key) {
        // temporary
        List<String> encodedReminder = toArrayList(key);
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

        return new Notification();
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
}
