package com.jdndeveloper.lifereminders.EventTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jgemig on 1/27/2015.
 */
public class Lifestyle extends BaseEvent {

    private List<String> lifestyleNotifications;

    String name = "DEFAULT LIFESTYLE NAME"; //notification doesn't use this
    String key = "LIFESTYLE_DEFAULT_KEY";
    boolean enabled = true;

    public Lifestyle() {}

    public List<String> getLifestyleNotifications() {
        return lifestyleNotifications;
    }

    public void setLifestyleNotifications(List<String> lifestyleNotifications) {
        this.lifestyleNotifications = lifestyleNotifications;
    }
}
