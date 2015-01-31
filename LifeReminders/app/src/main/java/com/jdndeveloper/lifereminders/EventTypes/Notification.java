package com.jdndeveloper.lifereminders.EventTypes;

/**
 * Created by jgemig on 1/27/2015.
 * Modified by Jayden Navarro on 1/28/2015.
 */
public class Notification extends AbstractBaseEvent {

    private String name = "DEFAULT NOTIFICATION NAME"; //notification doesn't use this
    private String key = "NOTIFICATION_DEFAULT_KEY";
    private boolean enabled = true;

    Action action;

    public Notification (){}
}
