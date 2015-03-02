package com.jdndeveloper.lifereminders.EventTypes;

import android.util.Log;

import java.util.List;

/**
 * Created by jgemig on 1/27/2015.
 * Edited to specs by Kevin Cheng on 1/30/2015
 * (Source Topic: http://imgur.com/Zich9mQ and http://imgur.com/RONO889)
 */
public class Lifestyle extends AbstractBaseEvent {

    private List<String> lifestyleReminders;

    public Lifestyle() {
        super("DEFAULT LIFESTYLE NAME", "DEFAULT_LIFESTYLE_KEY", false);
    }

    public List<String> getReminders() {
        return lifestyleReminders;
    }

    public void setReminders(List<String> lifestyleNotificationsArg) {
        this.lifestyleReminders = lifestyleNotificationsArg;
    }

    /* addReminder(): Function that will add the key to the new reminder
            * Arguments: Reminder ID
            * Return: none
            */
    public void addReminder(String reminderKey) {
        lifestyleReminders.add(reminderKey);
        return;
    }
    /* removeReminder(): Function that will remove the reminder ID from the lifestyleNotifications
        * Arguments: Reminder ID. The ID of the reminder
        * Return: none
        */
    public void removeReminder(String reminderID) {
        //Scan through the arraylist
        for(int i = 0; i < this.lifestyleReminders.size(); i++ ){
            //Check if the ID is in that item in Arraylist
            if (this.lifestyleReminders.get(i).equals(reminderID)) {
                //If it is fond, remove the item in Arraylist and just get out of the function.
                //The rest outside the for loop is some error debugging info.
                this.lifestyleReminders.remove(i);
                return;
            }
        }
        //Something went wrong then. The list does not have the reminderID.
        System.err.printf(
                "lifestyleNotifications does not have reminderID\nredminerID = %s\n", reminderID);
        //Print contents in arraylist
        System.err.println("Lifestyle Notifications Arraylist");
        for (int j = 0; j < this.lifestyleReminders.size(); j++) {
            System.err.printf("%d: %s\n", j, lifestyleReminders.get(j));
        }
        return;
    }

    @Override
    public void clean() {
        // fill with whatever needs to be cleaned/removed on object deletion from storage
        Log.e("Lifestyle", "clean() called");
    }
}
