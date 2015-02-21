package com.jdndeveloper.lifereminders.interfaces;

import com.jdndeveloper.lifereminders.EventTypes.Action;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by jgemig on 1/27/2015.
 */
public interface StorageInterface {

    // get all getters
    List<Lifestyle> getAllLifestyles();
    List<Reminder> getAllReminders();
    List<Notification> getAllNotifications();
    List<String> getAllKeys();
    List<String> getCurrentAlarmKeys();

    // single getters
    Lifestyle getLifestyle(String key);
    Reminder getReminder(String key);
    Notification getNotification(String key);
    Action getAction(String key);

    // single updaters - they return a boolean status
    boolean replaceLifeStyle(Lifestyle lifestyle, String key);
    boolean replaceReminder(Reminder reminder, String key);
    boolean replaceNotification(Notification notification, String key);
    boolean replaceAction(Action action, String key);

    // single creation - they return an object of said type
    Lifestyle getNewLifeStyle();
    Reminder getNewReminder();
    Notification getNewNotification();
    Action getNewAction();

    void commitNewLifestyle(Lifestyle lifestyle);
    void commitNewReminder(Reminder reminder);
    void commitNewNotification(Notification notification);
    void commitNewAction(Action action);
}
