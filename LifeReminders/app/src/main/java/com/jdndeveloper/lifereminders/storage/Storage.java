package com.jdndeveloper.lifereminders.storage;

import android.util.Log;

import com.google.gson.Gson;
import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventTypes.AbstractBaseEvent;
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
    private static Gson gsonObject = new Gson();

    public static StorageInterface getInstance() {
        return ourInstance;
    }

    private static SharedStorage sharedStorageInstance = SharedStorage.getInstance();

    private Storage() {

    }

    private List<String> toArrayList(String string){
        return new ArrayList<String>(Arrays.asList(string.split("\\,")));
    }

    @Override
    public List<Lifestyle> getAllLifestyles() {
        List<Lifestyle> lifestyles = new ArrayList<Lifestyle>();
        for(String lifestyle : toArrayList(sharedStorageInstance.getSharedPreferenceKey("all_lifestyles")))
            lifestyles.add(getLifestyle(lifestyle));
        return lifestyles;
    }

    @Override
    public List<Reminder> getAllReminders() {
        List<Reminder> reminders = new ArrayList<Reminder>();
        for(String reminder : toArrayList(sharedStorageInstance.getSharedPreferenceKey("all_reminders")))
            reminders.add(getReminder(reminder));
        return reminders;
    }

    @Override
    public List<Notification> getAllNotifications() {
        List<Notification> notifications = new ArrayList<Notification>();
        for(String notification : toArrayList(sharedStorageInstance.getSharedPreferenceKey("all_notifications")))
            notifications.add(getNotification(notification));
        return notifications;
    }

    @Override
    public Lifestyle getLifestyle(String key) {
        String lifestyleGsonString = sharedStorageInstance.getSharedPreferenceKey(key);

        if (lifestyleGsonString != null)
            return gsonObject.fromJson(lifestyleGsonString, Lifestyle.class);

        String failedLifestyleGsonString = sharedStorageInstance.getSharedPreferenceKey("Failed_Lifestyle_01");
        return gsonObject.fromJson(lifestyleGsonString, Lifestyle.class);
    }

    @Override
    public Reminder getReminder(String key) {
        String reminderGsonString = sharedStorageInstance.getSharedPreferenceKey(key);

        if (reminderGsonString != null)
            return gsonObject.fromJson(reminderGsonString, Reminder.class);

        String failedReminderGsonString = sharedStorageInstance.getSharedPreferenceKey("Failed_Reminder_01");
        return gsonObject.fromJson(failedReminderGsonString, Reminder.class);
    }

    @Override
    public Notification getNotification(String key) {
        String notificationGsonString = sharedStorageInstance.getSharedPreferenceKey(key);

        if (notificationGsonString != null)
            return gsonObject.fromJson(notificationGsonString, Notification.class);

        String failedNotificationGsonString = sharedStorageInstance.getSharedPreferenceKey("Failed_Notification_01");
        return gsonObject.fromJson(failedNotificationGsonString, Notification.class);
    }

    @Override
    public Action getAction(String key) {
        String actionGsonString = sharedStorageInstance.getSharedPreferenceKey(key);

        if (actionGsonString != null)
            return gsonObject.fromJson(actionGsonString, Action.class);

        String failedActionGsonString = sharedStorageInstance.getSharedPreferenceKey("Failed_Action_01");
        return gsonObject.fromJson(failedActionGsonString, Action.class);
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
    public boolean replaceAbstractBaseEvent(AbstractBaseEvent abstractBaseEvent) {
        sharedStorageInstance.saveAbstractBaseEvent(abstractBaseEvent);
        return false;
    }

    @Override
    public Lifestyle getNewLifeStyle() {
        Lifestyle lifestyle = new Lifestyle();

        String key = sharedStorageInstance.getNewLifestyleKey();
        if (key == null) return null;

        lifestyle.setName("New " + key);
        lifestyle.setKey(key);
        lifestyle.setEnabled(true);
        lifestyle.setReminders(new ArrayList<String>());

        return lifestyle;
    }

    @Override
    public Reminder getNewReminder(){
        Reminder reminder = new Reminder();

        String key = sharedStorageInstance.getNewReminderKey();
        if (key == null) return null;

        reminder.setName("New " + key);
        reminder.setKey(key);
        reminder.setEnabled(true);
        reminder.setNotificationKeys(new ArrayList<String>());

        return reminder;
    }

    @Override
    public Notification getNewNotification() {
        Notification notification = new Notification();

        String key = sharedStorageInstance.getNewNotificationKey();
        if (key == null) return null;

        notification.setName("New " + key);
        notification.setKey(key);
        notification.setEnabled(true);

        return notification;
    }

    @Override
    public Action getNewAction() {
        Action action = new Action();

        String key = sharedStorageInstance.getNewActionKey();
        if (key == null) return null;

        action.setName("New " + key);
        action.setKey(key);
        action.setEnabled(true);

        return action;
    }

    @Override
    public boolean commitAbstractBaseEvent(AbstractBaseEvent abstractBaseEvent) {
        return sharedStorageInstance.commitNewAbstractBaseEvent(abstractBaseEvent);
    }

    @Override
    public boolean deleteAbstractBaseEvent(AbstractBaseEvent abstractBaseEvent) {
        return sharedStorageInstance.deleteAbstractBaseEvent(abstractBaseEvent);
    }
}
