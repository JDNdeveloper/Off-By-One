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

    // getters
    Lifestyle getLifestyle(String key);
    Reminder getReminder(String key);
    Notification getNotification(String key);
    Action getAction(String key);

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
    Action getNewAction();
}
