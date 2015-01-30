package com.jdndeveloper.lifereminders.interfaces;

import com.jdndeveloper.lifereminders.lifestyle.LifeStyle;
import com.jdndeveloper.lifereminders.notification.Notification;
import com.jdndeveloper.lifereminders.reminder.Reminder;

import java.util.List;

/**
 * Created by jgemig on 1/27/2015.
 */
public interface StorageOperations {

    // getters
    LifeStyle getLifeStyle(String key);
    Reminder getReminder(String key);
    Notification getNotification(String key);

    List<String> getCurrentAlarmKeys();

    // for debugging
    List<String> getAllKeys();

    // updaters - they return a boolean status
    boolean replaceLifeStyle(LifeStyle lifeStyle, String key);
    boolean replaceNotification(Notification notification, String key);

    // creation - they return an object of said type
    LifeStyle getNewLifeStyle();
    Reminder getNewReminder();
    Notification getNewNotification();
}
