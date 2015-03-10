package com.jdndeveloper.lifereminders.Tests;

import android.content.Context;
import android.util.Log;

import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Kevin Cheng.
 */
public class LifestyleTester {

    public void runTestAddDisplayClearDisplay() {
        Lifestyle lifestyle = Storage.getInstance().getLifestyle(Constants.LIFESTYLE_TEST_KEY);
        Log.d("LifestyleTester", "Before Individual Addition: \n" + lifestyle.toString());

        //Attempt to add list with less than 10 keys
        String tempString = "";
        List<String> lifestyleTest = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            tempString += i;
            lifestyle.addReminder(tempString);
        }
        //Should properly add
        lifestyle.setReminders(lifestyleTest);
        Log.d("LifestyleTester", "After individual addition \n" + lifestyle.getReminders().toString());
        //Clearing Everything Out
        lifestyleTest.clear();
        lifestyle.clean();
        //Print out the things in the Lifestyle Object. Should be clean by now
        Log.d("LifestyleTester", "After individual addition \n" + lifestyle.toString());
    }

    public void runTestAddRemoveDisplay() {
        Lifestyle lifestyle2 = Storage.getInstance().getLifestyle(Constants.LIFESTYLE_TEST_KEY);
        Log.d("LifestyleTester", "Before Individual Addition: \n" + lifestyle2.toString());

        //Attempt to add list with less than 10 keys
        String tempString = "";
        List<String> lifestyleTest = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            tempString += i;
            lifestyle2.addReminder(tempString);
            lifestyle2.removeReminder(tempString);
        }
        //Should properly add
        lifestyle2.setReminders(lifestyleTest);
        //Should be clean
        Log.d("LifestyleTester", "After individual addition \n" + lifestyle2.toString());

    }

    public void runTestMore10() {
        Lifestyle lifestyle3 = Storage.getInstance().getLifestyle(Constants.LIFESTYLE_TEST_KEY);
        Log.d("LifestyleTester", "Before Individual Addition: \n" + lifestyle3.toString());
        //attempt to add list with more than 10 "keys"
        String tempString = "";
        List<String> reminderTest = new ArrayList<String>();
        for(int i =0; i<=10;i++){
            tempString += i;
            reminderTest.add(tempString);
        }
        //should return with error
        lifestyle3.setReminders(reminderTest);
    }

    public void runTestLess10() {
        Lifestyle lifestyle4 = Storage.getInstance().getLifestyle(Constants.LIFESTYLE_TEST_KEY);
        Log.d("LifestyleTester", "Before Individual Addition: \n" + lifestyle4.toString());
        //attempt to add list with less than 10 "keys"
        String tempString = "";
        List<String> reminderTest = new ArrayList<String>();
        for(int i =0; i < 10;i++){
            tempString += i;
            reminderTest.add(tempString);
        }
        //should return with error
        lifestyle4.setReminders(reminderTest);
        Log.d("LIfestyleTester", "After addition: \n" + lifestyle4.toString());
    }

}
