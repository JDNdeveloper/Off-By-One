package com.jdndeveloper.lifereminders.EventTypes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jgemig on 1/27/2015.
 * Edited by agonza26 last on 2/1/2015/
 */
public class Reminder extends AbstractBaseEvent {
    //maximum number of notification
    private static final int maxNotifications = 10;

    //list holding notification keys
    private List<String> notificationKeys = new ArrayList<String>();


    public Reminder() {
        super("DEFAULT REMINDER NAME", "DEFAULT_REMINDER_KEY", false);
    }

    public Reminder(String name, String key, boolean enabled) {
        super(name,key,enabled);
    }


    //simple getter
    public List<String> getNotificationKeys() {
        return notificationKeys;
    }




    public void setNotificationKeys(ArrayList<String> notificationKey){
        //empties previous list
        this.notificationKeys.clear();

        if(notificationKey.size()<=10) {
            //new List's size is in requirements
            for (int i = 0; i < notificationKey.size(); i++) {
                this.notificationKeys.add(notificationKey.get(i));
            }
        }else{

            //List passed through as parameter is too large to copy
            System.err.printf("cannot add new notificationKey List due to being bigger than maxSize");
        }

    }


    public void rearrangeNotifications(String notificationID, int index){

        if(this.notificationKeys.size()==0){
            System.err.printf("attempted to removeNotification %s on an empty list\n", notificationID);
        }



        int ind = this.notificationKeys.indexOf(notificationID);
        //as long as the ID exists, .indexOf will return a number other than -1
        if (ind != -1) {
            this.notificationKeys.remove(index);//remove from old spot
            this.notificationKeys.add(index, notificationID);//place in new position
            return;

        }



        //index = -1 and the list does not have the notificationID.
        System.err.printf(
                "Reminder does not have notificationID %s\n", notificationID);



    }






    public void addNotification(String notificationID) {
        if(this.notificationKeys.size()<10){
            this.notificationKeys.add(notificationID);
        }else{
            System.err.printf("cannot add notificationID= %s, notificationKeys list at max capacity\n", notificationID);
        }
    }




    public void removeNotification(String notificationID) {
        if(this.notificationKeys.size()==0){
            System.err.printf("attempted to removeNotification %s on an empty list\n", notificationID);
        }

        int index = this.notificationKeys.indexOf(notificationID);
        //as long as the ID exists, .indexOf will return a number other than -1
        if (index != -1) {
            this.notificationKeys.remove(index);
            return;
        }



        //index = -1 and the list does not have the notificationID.
        System.err.printf(
                "Reminder does not have notificationID %s\n", notificationID);


    }



    public String toString(){
        String obString = "Reminder name: " + this.getName() +"\nkey: "
                + this.getKey() +"\nenabled: " + this.isEnabled();

        obString += "\nNotification List Size :" + this.notificationKeys.size();

        obString += "\nNotification List Contents\n";
        for (int j = 0; j < this.notificationKeys.size(); j++) {
            obString += j + ": " + notificationKeys.get(j) + "\n";
        }

        return obString;
    }






}
