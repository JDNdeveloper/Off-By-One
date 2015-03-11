package com.jdndeveloper.lifereminders.Tests;


import android.util.Log;

import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Used Notification Tester by Jayden Navarro on 1/31/2015 as base
 * Modified by Alexander Gonzalez agonza26 on 2/2/2015
 */
public class ReminderTester {

    public void runTest() {
        Reminder reminder1 = Storage.getInstance().getReminder(Constants.REMINDER_TEST_KEY);
        Log.d("RemTest", "Before Individual Addition: \n" + reminder1.toString());

        //attempt to add list with less than 10 "keys"
        String tempString = "";
        List<String> notificationTest = new ArrayList<String>();
        for(int i =0; i<10;i++){
            tempString += i;
            reminder1.addNotification(tempString);
        }
        //should properly add.
        reminder1.setNotificationKeys(notificationTest);
        Log.d("RemTest", "After Individual Addition: \n" + reminder1.toString());


        reminder1.clean();

        Log.d("RemTest", "After called .clean() \n" + reminder1.toString());
        


    }

    public void runTest2() {
        Reminder reminder2 = Storage.getInstance().getReminder(Constants.REMINDER_TEST_KEY);
        Log.d("RemTest", "Before Addition: \n" + reminder2.toString());
        //attempt to add list with more than 10 "keys"
        String tempString = "";
        List<String> notificationTest = new ArrayList<String>();
        for(int i =0; i<=10;i++){
            tempString += i;
            notificationTest.add(tempString);
        }
        //should return with error
        Log.d("RemTest", "Should return with error \n" + reminder2.toString());
        reminder2.setNotificationKeys(notificationTest);
        




    }


    public void runTest3() {
        Reminder reminder3 = Storage.getInstance().getReminder(Constants.REMINDER_TEST_KEY);
        Log.d("RemTest", "Before Addition: \n" + reminder3.toString());

        //attempt to add list with less than 10 "keys"
        String tempString = "";
        List<String> notificationTest = new ArrayList<String>();
        for(int i =0; i<10;i++){
            tempString += i;
            notificationTest.add(tempString);
        }
        //should properly add.
        reminder3.setNotificationKeys(notificationTest);
        Log.d("RemTest", "After Addition: \n" + reminder3.toString());

    }



}
