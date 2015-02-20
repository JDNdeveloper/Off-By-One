package com.jdndeveloper.lifereminders.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jgemig on 2/16/2015.
 */
public class SharedStorage {

    private final Context context;
    private static SharedStorage instance;
    private final SharedPreferences sharedPreferences;
    private final Gson gsonObject = new Gson();

    String All_Lifestyles = "Lifestyle_01,Lifestyle_02,Lifestyle_03," +
            "Test_Lifestyle_01,Failed_Lifestyle_01";
    String All_Reminders = "Reminder_01,Reminder_02,Reminder_03,Reminder_04," +
            "Test_Reminder_01,Failed_Reminder_01";
    String All_Notifications = "Notification_01,Notification_02,Notification_03" +
            ",Test_Notification_01,Failed_Notification_01";

    private SharedStorage(Context context){
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        loadPreferences();
    }

    public static final SharedStorage getInstance(){
        return instance;
    }

    public static final void initializeInstance(Context context){
        if (instance == null){
            instance = new SharedStorage(context);
        }
    }
    private void toastSaved(CharSequence entry){
        Toast toast = Toast.makeText(context, entry + " has been saved.", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.getView().setBackgroundColor(0xff303030);
        toast.show();
    }

    private void initializeFirstRun(){
        sharedPreferencePutString("all_lifestyles", All_Lifestyles);
        sharedPreferencePutString("all_reminders", All_Reminders);
        sharedPreferencePutString("all_notifications", All_Notifications);
        toastSaved("initializedFirstRun");
        sharedPreferencePutString("Lifestyle_01", lifestyle_01);
        sharedPreferencePutString("Lifestyle_02", lifestyle_02);
        sharedPreferencePutString("Lifestyle_03", lifestyle_03);
        sharedPreferencePutString("Test_Lifestyle_01", test_Lifestyle_01);
        sharedPreferencePutString("Failed_Lifestyle_01", failed_Lifestyle_01);
    }

    public String getSharedPreferenceKey(String key){
        return sharedPreferences.getString(key, null);
    }
    private void sharedPreferencePutString(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value).commit();
    }
    private void loadPreferences(){
        // for the very first time the app is run and preferences haven't been inited yet.
//        if (sharedPreferences.getString("all_lifestyles", null) == null ){
            initializeFirstRun();
//        }
    }

    String lifestyle_01 = "{\"lifestyleReminders\":[\"Reminder_01\",\"Reminder_02\",\"Reminder_03\",\"Reminder_04\"],\"key\":\"Lifestyle_01\",\"name\":\"Happy Time\",\"enabled\":false}";
    String lifestyle_02 = "{\"lifestyleReminders\":[\"Reminder_01\",\"Reminder_02\",\"Reminder_03\",\"Reminder_04\"],\"key\":\"Lifestyle_02\",\"name\":\"UCSC\",\"enabled\":true}";
    String lifestyle_03 = "{\"lifestyleReminders\":[\"Reminder_01\",\"Reminder_02\",\"Reminder_03\",\"Reminder_04\"],\"key\":\"Lifestyle_03\",\"name\":\"Vacation\",\"enabled\":true}";
    String test_Lifestyle_01 = "{\"lifestyleReminders\":[\"Test_Reminder_01\"],\"key\":\"Test_Lifestyle_01\",\"name\":\"Test Lifestyle 01\",\"enabled\":true}";
    String failed_Lifestyle_01 = "{\"lifestyleReminders\":[\"Failed_Reminder_01\"],\"key\":\"Failed_Lifestyle_01\",\"name\":\"Failed Lifestyle\",\"enabled\":false}";
}
