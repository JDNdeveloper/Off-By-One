package com.jdndeveloper.lifereminders.EventTypes;

/**
 * Created by Jayden Navarro on 1/30/2015.
 */

/**
 * This is the base class that Lifestyle, Reminder, and Notification all extend
 */
public abstract class AbstractBaseEvent {
    // these need to be overridden by concrete class and are purposely defined to catch bugs
    private String name = "ABSTRACT NAME"; //notification doesn't use this
    private String key = "ABSTRACT KEY";
    private boolean enabled = false;

    public AbstractBaseEvent(){};
    public AbstractBaseEvent(String name, String key, boolean enabled){
        this.name = name;
        this.key = key;
        this.enabled = enabled;
    }
    public String getName() { //notification class doesn't use this
        return name;
    }

    public void setName(String newName) { //notification class doesn't use this
        name = newName;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String newKey) { //should only be called by storage!
        key = newKey;
    }

    public boolean isEnabled() { //true if enabled, false if disabled
        return enabled;
    }
    public void setEnabled(boolean state) { //true if enabled, false if disabled
        enabled = state;
    }
}
