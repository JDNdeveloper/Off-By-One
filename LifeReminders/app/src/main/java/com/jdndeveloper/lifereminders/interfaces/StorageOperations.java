package com.jdndeveloper.lifereminders.interfaces;

import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;

import java.util.List;

/**
 * Created by jgemig on 1/27/2015.
 */
public interface StorageOperations {

    // getters
    Lifestyle getLifeStyle(String key);
    Reminder getReminder(String key);
    Notification getNotification(String key);

    List<String> getCurrentAlarmKeys();

    // for debugging
    List<String> getAllKeys();

    // updaters - they return a boolean status
    boolean replaceLifeStyle(Lifestyle lifestyle, String key);
    boolean replaceNotification(Notification notification, String key);

    // creation - they return an object of said type
    Lifestyle getNewLifeStyle();
    Reminder getNewReminder();
    Notification getNewNotification();
}
