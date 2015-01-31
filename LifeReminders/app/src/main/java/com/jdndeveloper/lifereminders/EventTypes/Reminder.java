package com.jdndeveloper.lifereminders.EventTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jgemig on 1/27/2015.
 */
public class Reminder extends BaseEvent {


    private List<String> notificationKeys = new ArrayList<String>();

    String name = "DEFAULT REMINDER NAME"; //notification doesn't use this
    String key = "REMINDER_DEFAULT_KEY";
    boolean enabled = true;


    public Reminder() {}


    public List<String> getNotificationKeys() {
        return notificationKeys;
    }

    public void setNotificationKeys(List<String> notificationKeys) {
        this.notificationKeys = notificationKeys;
    }
}
