package com.jdndeveloper.lifereminders.storage;

import com.jdndeveloper.lifereminders.interfaces.LifeStyle;
import com.jdndeveloper.lifereminders.interfaces.Notification;
import com.jdndeveloper.lifereminders.interfaces.StorageOperations;

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
        return null;
    }

    @Override
    public Notification getNotification(String key) {
        return null;
    }

    @Override
    public List<String> getCurrentAlarmKeys() {
        return null;
    }

    @Override
    public List<String> getAllKeys() {
        return null;
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
        return null;
    }

    @Override
    public String newNotification(Notification notification) {
        return null;
    }
}
