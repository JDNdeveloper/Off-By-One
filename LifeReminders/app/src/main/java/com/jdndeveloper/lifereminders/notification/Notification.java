package com.jdndeveloper.lifereminders.notification;

/**
 * Created by jgemig on 1/27/2015.
 * Modified by Jayden Navarro on 1/28/2015.
 */
public class Notification {

    private String notificationKey = "notification_default_key";
    private String notificationName = "notification default name";
    private boolean enabled = false;

    public Notification (){}

    public String getNotificationKey() {
        return notificationKey;
    }

    public void setNotificationKey(String notificationKey) {
        this.notificationKey = notificationKey;
    }

    public String getNotificationName() {
        return notificationName;
    }

    public void setNotificationName(String notificationName) {
        this.notificationName = notificationName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
