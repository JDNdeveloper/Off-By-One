package com.jdndeveloper.lifereminders.interfaces;

import java.util.List;

/**
 * Created by jgemig on 1/27/2015.
 */
public interface StorageOperations {

    // getters
    LifeStyle getLifeStyle(String key);
    Notification getNotification(String key);
    List<String> getCurrentAlarmKeys();

    // for debugging
    List<String> getAllKeys();

    // updaters - they return a boolean status
    boolean replaceLifeStyle(LifeStyle lifeStyle, String key);
    boolean replaceNotification(Notification notification, String key);

    // creation - they return a key in the form of a string
    String newLifeStyle(LifeStyle lifeStyle);
    String newNotification(Notification notification);
}
