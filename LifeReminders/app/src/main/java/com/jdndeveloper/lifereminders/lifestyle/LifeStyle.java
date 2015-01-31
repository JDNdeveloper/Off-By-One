package com.jdndeveloper.lifereminders.lifestyle;

import android.provider.CalendarContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin Cheng on 1/27/2015.
 */
public class LifeStyle {

    private String name = "";
    private String key = "";
    boolean enabled = false;
/* *
 * Lifestyle Name: The following contains the setting and returning the name of the lifestyle.
 * Values used: String
 * Variables used: name
 */
    /* *
     * getKey(): Function that will return the value of name
     * Arguments: none
     * Return:
     *      * Type: String
     *      * Item: The key of the lifestyle
     */
    public String getKey() {
        return this.key;
    }
    /* *
     * setKey(): Function that will set the name of the lifestyle
     * Arguments:
     *       * Type: String
     *       * Item: key of the Lifestyle
     * Return: none
     */
    public void setKey(String inputKey) {
        this.key = inputKey;
    }

    /* *
         * getName(): Function that will return the value of name
         * Arguments: none
         * Return:
         *      * Type: String
         *      * Item: The name of the lifestyle
         */
    public String getName() {
        return this.name;
    }
    /* *
         * getName(): Function that will return the value of name
         * Arguments: none
         * Return:
         *      * Type: String
         *      * Item: The key of the lifestyle
         */
    public void setName(String inputName) {
        this.name = inputName;
    }
    /* removeReminder(): Function that will remove the reminder from the lifestyle
            * Arguments: Reminder ID. The ID of the reminder
            * Return: none
            */
    public void addReminder(String reminderName) {
        //Create a new Reminder
        Reminder newReminder = getNew<Reminder>();
        commit<Reminder>(newReminder);
        //Set the reminder
        newReminder.setName(reminderName);
        newReminder.setEnabled();
        //Set the
        commit<Reminder>(newReminder);
    }
    /* removeReminder(): Function that will remove the reminder from the lifestyle
        * Arguments: Reminder ID. The ID of the reminder
        * Return: none
        */
    public void removeReminder(String reminderID) {
        //Get the reminder object from the reminderID
        Reminder object =  getReminder(reminderID);
        //Disable it just in case
        object.setDisable();
        //Set it to null so that it points to nothing now so that
        object = null;
        //Garbage Collector will clean it up for us
        //Remove object in list

    }
    /* getState(): Function that will return the state of the lifestyle
    * Arguments: none
    * Return:
    *      * Type: boolean
    *      * Item: Enabled/Disabled (Disabled by default)
    */
    public boolean getState() {
        return this.enabled;
    }
    /* getState(): Function that will return the state of the lifestyle
        * Arguments:
        *      * Type: boolean
        *      * Item: Enabled/Disabled
        * Return: none
        */
    public void setState(boolean state) {
        this.enabled = state;
    }
}
