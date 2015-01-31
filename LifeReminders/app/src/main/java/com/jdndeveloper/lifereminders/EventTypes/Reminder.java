package com.jdndeveloper.lifereminders.EventTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jgemig on 1/27/2015.
 */
public class Reminder extends AbstractBaseEvent {

    private List<String> notificationKeys = new ArrayList<String>();

    public Reminder() {
        super("DEFAULT REMINDER NAME", "DEFAULT_REMINDER_KEY", false);
    }

    public List<String> getNotificationKeys() {
        return notificationKeys;
    }

    public void setNotificationKeys(List<String> notificationKeys) {
        this.notificationKeys = notificationKeys;
    }
}
