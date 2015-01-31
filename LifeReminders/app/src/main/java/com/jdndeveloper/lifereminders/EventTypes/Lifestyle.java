package com.jdndeveloper.lifereminders.EventTypes;

import java.util.List;

/**
 * Created by jgemig on 1/27/2015.
 */
public class Lifestyle extends AbstractBaseEvent {

    private List<String> lifestyleNotifications;

    private String name = "DEFAULT LIFESTYLE NAME"; //notification doesn't use this
    private String key = "LIFESTYLE_DEFAULT_KEY";
    private boolean enabled = true;

    public Lifestyle() {}

    public List<String> getLifestyleNotifications() {
        return lifestyleNotifications;
    }

    public void setLifestyleNotifications(List<String> lifestyleNotifications) {
        this.lifestyleNotifications = lifestyleNotifications;
    }
}
