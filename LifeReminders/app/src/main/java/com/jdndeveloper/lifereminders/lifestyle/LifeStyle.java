package com.jdndeveloper.lifereminders.lifestyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jgemig on 1/27/2015.
 */
public class LifeStyle {

    private String lifeStyleKey = "lifeStyle_default_key";
    private String lifeStyleName = "life style default name";
    private boolean enabled = false;
    private List<String> lifeStyleNotifications;

    public LifeStyle (){
    }

    public String getLifeStyleKey() {
        return lifeStyleKey;
    }

    public void setLifeStyleKey(String lifeStyleKey) {
        this.lifeStyleKey = lifeStyleKey;
    }

    public String getLifeStyleName() {
        return lifeStyleName;
    }

    public void setLifeStyleName(String lifeStyleName) {
        this.lifeStyleName = lifeStyleName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getLifeStyleNotifications() {
        return lifeStyleNotifications;
    }

    public void setLifeStyleNotifications(List<String> lifeStyleNotifications) {
        this.lifeStyleNotifications = lifeStyleNotifications;
    }
}
