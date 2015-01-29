package com.jdndeveloper.lifereminders.storage;

import com.jdndeveloper.lifereminders.lifestyle.LifeStyle;
import com.jdndeveloper.lifereminders.notification.Notification;
import com.jdndeveloper.lifereminders.interfaces.StorageOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jgemig on 1/27/2015.
 */
public class Storage implements StorageOperations {
    private static Storage ourInstance = new Storage();

    public static Storage getInstance() {
        return ourInstance;
    }

    private Storage() {
    }

    @Override
    public LifeStyle getLifeStyle(String key) {
        // temporary
        LifeStyle lifeStyle = new LifeStyle();
        ArrayList<String> lifeStyleNotifications = new ArrayList<String>();
        lifeStyleNotifications.add("notification_01");
        lifeStyleNotifications.add("notification_02");
        lifeStyleNotifications.add("notification_03");
        lifeStyleNotifications.add("notification_04");
        lifeStyle.setLifeStyleNotifications(lifeStyleNotifications);
        return lifeStyle;
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
    public boolean replaceLifeStyle(LifeStyle lifeStyle, String key) {

        return false;
    }

    @Override
    public boolean replaceNotification(Notification notification, String key) {

        return false;
    }

    @Override
    public String newLifeStyle(LifeStyle lifeStyle) {

        return "new_dummy_lifestyle_key";
    }

    @Override
    public String newNotification(Notification notification) {

        return "new_dummy_notification_key";
    }
}
