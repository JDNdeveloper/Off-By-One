package com.jdndeveloper.lifereminders.interfaces;

import com.jdndeveloper.lifereminders.EventTypes.AbstractBaseEvent;
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

    // updater and commit - returns a boolean status
    boolean replaceAbstractBaseEvent(AbstractBaseEvent abstractBaseEvent);
    boolean commitAbstractBaseEvent(AbstractBaseEvent abstractBaseEvent);

    // single creation - they return an object of said type
    // these ALL return NULL if there is a problem with creation
    Lifestyle getNewLifeStyle();
    Reminder getNewReminder();
    Notification getNewNotification();
    Action getNewAction();
}
