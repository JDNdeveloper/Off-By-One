package com.jdndeveloper.lifereminders.reminder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jgemig on 1/27/2015.
 */
public class Reminder {

    private String reminderKey = "reminder_default_key";
    private String reminderName = "reminder default name";
    private boolean enabled = false;
<<<<<<< HEAD
    private List<String> notificationKeys = new ArrayList<String>();
=======
    private List<String> notificationKeys;
>>>>>>> origin/John's-Branch

    public Reminder(){}

    public String getReminderKey() {
        return reminderKey;
    }

    public void setReminderKey(String reminderKey) {
        this.reminderKey = reminderKey;
    }

    public String getReminderName() {
        return reminderName;
    }

    public void setReminderName(String reminderName) {
        this.reminderName = reminderName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getNotificationKeys() {
        return notificationKeys;
    }

    public void setNotificationKeys(List<String> notificationKeys) {
        this.notificationKeys = notificationKeys;
    }
}
