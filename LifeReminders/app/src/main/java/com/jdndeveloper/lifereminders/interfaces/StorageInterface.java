package com.jdndeveloper.lifereminders.interfaces;

import com.jdndeveloper.lifereminders.EventTypes.AbstractBaseEvent;
import com.jdndeveloper.lifereminders.EventTypes.Action;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Option;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by jgemig on 1/27/2015.
 */
public interface StorageInterface {

    // get all getters
    // returns a list of Lifestyles, containing all lifestyles
    List<Lifestyle> getAllLifestyles();
    // returns a list of Reminders, containing all reminders
    List<Reminder> getAllReminders();
    // returns a list of Notifications, containing all notifications
    List<Notification> getAllNotifications();
    // single getters
    // return a Lifestyle given a key
    Lifestyle getLifestyle(String key);
    // return a Reminder given a key
    Reminder getReminder(String key);
    // return a Notification given a key
    Notification getNotification(String key);
    // return an Action given a key
    Action getAction(String key);

    // updater and commit - returns a boolean status
    // replace an EXISTING ABE
    boolean replaceAbstractBaseEvent(AbstractBaseEvent abstractBaseEvent);
    // commit a NEW ABE
    boolean commitAbstractBaseEvent(AbstractBaseEvent abstractBaseEvent);
    // delete an EXISTING ABE
    boolean deleteAbstractBaseEvent(AbstractBaseEvent abstractBaseEvent);

    // single creation - they return an object of said type
    // these ALL return NULL if there is a problem with creation
    // return a new, unique, empty Lifestyle
    Lifestyle getNewLifeStyle();
    // return a new, unique, empty Reminder
    Reminder getNewReminder();
    // return a new, unique, empty Notification
    Notification getNewNotification();
    // return a new, unique, empty Action
    Action getNewAction();

    // get an option given a key
    Option getOption(String key);
    // save an option, returns false on failure
    boolean saveOption(Option option);
}
