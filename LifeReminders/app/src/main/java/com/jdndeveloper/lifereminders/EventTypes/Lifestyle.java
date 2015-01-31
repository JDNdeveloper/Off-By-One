package com.jdndeveloper.lifereminders.EventTypes;

import java.util.List;

/**
 * Created by jgemig on 1/27/2015.
 * Edited to specs by Kevin Cheng on 1/30/2015
 * (Source Topic: http://imgur.com/Zich9mQ and http://imgur.com/RONO889)
 */
public class Lifestyle extends AbstractBaseEvent {

    private List<String> notifications;

    private String name = "DEFAULT LIFESTYLE NAME"; //notification doesn't use this
    private String key = "LIFESTYLE_DEFAULT_KEY";
    private boolean enabled = true;

    public Lifestyle() {}

    public List<String> getLifestyleReminders() {
        return notifications;
    }

    public void setLifestyleReminders(List<String> lifestyleNotifications) {
        this.notifications = lifestyleNotifications;
    }

    /* addReminder(): Function that will add the key to the new reminder
            * Arguments: Reminder ID
            * Return: none
            */
    public void addReminder(String reminderKey) {
        notifications.add(reminderKey);
    }
    /* removeReminder(): Function that will remove the reminder ID from the lifestyleNotifications
        * Arguments: Reminder ID. The ID of the reminder
        * Return: none
        */
    public void removeReminder(String reminderID) {
        //Scan through the arraylist
        for(int i = 0; i < this.notifications.size(); i++ ){
            //Check if the ID is in that item in Arraylist
            if (this.notifications.get(i).equals(reminderID)) {
                //If it is fond, remove the item in Arraylist and just get out of the function.
                //The rest outside the for loop is some error debugging info.
                this.notifications.remove(i);
                return;
            }
        }
        //Something went wrong then. The list does not have the reminderID.
        System.err.printf(
                "lifestyleNotifications does not have reminderID\nredminerID = %s\n", reminderID);
        //Print contents in arraylist
        System.err.println("Lifestyle Notifications Arraylist");
        for (int j = 0; j < this.notifications.size(); j++) {
            System.err.printf("%d: %s\n", j, notifications.get(j));
        }
    }
}
