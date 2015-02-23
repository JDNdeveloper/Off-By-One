package com.jdndeveloper.lifereminders.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jdndeveloper.lifereminders.EventTypes.AbstractBaseEvent;
import com.jdndeveloper.lifereminders.EventTypes.Action;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;

/**
 * Created by jgemig on 2/16/2015.
 */
public class SharedStorage {

    private final Context context;
    private static SharedStorage instance;
    private final SharedPreferences sharedPreferences;
    private final Gson gsonObject = new Gson();

    private SharedStorage(Context context){
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        loadPreferences();
    }

    protected static final SharedStorage getInstance(){
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
        sharedPreferencePutString("all_actions", All_Actions);

        sharedPreferencePutString("Lifestyle_01", lifestyle_01);
        sharedPreferencePutString("Lifestyle_02", lifestyle_02);
        sharedPreferencePutString("Lifestyle_03", lifestyle_03);
        sharedPreferencePutString("Test_Lifestyle_01", test_Lifestyle_01);
        sharedPreferencePutString("Failed_Lifestyle_01", failed_Lifestyle_01);

        sharedPreferencePutString("Reminder_01", reminder_01);
        sharedPreferencePutString("Reminder_02", reminder_02);
        sharedPreferencePutString("Reminder_03", reminder_03);
        sharedPreferencePutString("Reminder_04", reminder_04);
        sharedPreferencePutString("Test_Reminder_01", test_reminder_01);
        sharedPreferencePutString("Failed_Reminder_01", failed_reminder_01);

        sharedPreferencePutString("Notification_01", notification_01);
        sharedPreferencePutString("Notification_02", notification_02);
        sharedPreferencePutString("Notification_03", notification_03);
        sharedPreferencePutString("Test_Notification_01", test_notification_01);
        sharedPreferencePutString("Failed_Notification_01", failed_notification_01);

        sharedPreferencePutString("Test_Action_01", test_action_01);
        sharedPreferencePutString("Failed_Action_01", failed_action_01);

        sharedPreferencePutInt("lifestyleIndex", 10);
        sharedPreferencePutInt("reminderIndex", 10);
        sharedPreferencePutInt("notificationIndex", 10);
        sharedPreferencePutInt("actionIndex", 10);

        toastSaved("initializedFirstRun");
    }

    public String getNewLifestyleKey(){
        int key = sharedPreferences.getInt("lifestyleIndex", -1);

        if (key != -1) {
            sharedPreferencePutInt("lifestyleIndex", ++key);
            return ("Lifestyle_" + key);
        }
        return null;
    }

    public boolean commitNewAbstractBaseEvent(AbstractBaseEvent abstractBaseEvent){
        // check for null
        if (abstractBaseEvent == null) return false;
        // get key
        String key = abstractBaseEvent.getKey();
        // check for null key
        if (key == null) return false;
        // check to see if key exists
        if (getSharedPreferenceKey(key) != null) return false;
        // set up a null keyChain
        String keyChain = null;
        // get a keyChain for the abs of type
        if (abstractBaseEvent instanceof Lifestyle)
            keyChain = "all_lifestyles";
        if (abstractBaseEvent instanceof Reminder)
            keyChain = "all_reminders";
        if (abstractBaseEvent instanceof Notification)
            keyChain = "all_notifications";
        if (abstractBaseEvent instanceof Action)
            keyChain = "all_actions";
        // is the keyChain still null
        if (keyChain == null) return false;

        String all_keys = getSharedPreferenceKey(keyChain) + "," + key;
        sharedPreferencePutString(keyChain, all_keys);

        saveAbstractBaseEvent(abstractBaseEvent);
        return true;
    }

    public void saveAbstractBaseEvent(AbstractBaseEvent abstractBaseEvent){
        if (abstractBaseEvent == null) return;

        String key = abstractBaseEvent.getKey();
        String gsonString = gsonObject.toJson(abstractBaseEvent);
        sharedPreferencePutString(key, gsonString);
    }

    public String getNewReminderKey(){
        int key = sharedPreferences.getInt("reminderIndex", -1);

        if (key != -1) {
            sharedPreferencePutInt("reminderIndex", ++key);
            return ("Reminder_" + key);
        }
        return null;
    }

    public String getNewNotificationKey(){
        int key = sharedPreferences.getInt("notificationIndex", -1);

        if (key != -1) {
            sharedPreferencePutInt("notificationIndex", ++key);
            return ("Notification_" + key);
        }
        return null;
    }
    public String getNewActionKey(){
        int key = sharedPreferences.getInt("actionIndex", -1);

        if (key != -1) {
            sharedPreferencePutInt("actionIndex", ++key);
            return ("Action_" + key);
        }
        return null;
    }

    public String getSharedPreferenceKey(String key){
        return sharedPreferences.getString(key, null);
    }
    private void sharedPreferencePutString(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value).commit();
    }
    private void sharedPreferencePutInt(String key, int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value).commit();
    }
    private void loadPreferences(){
        // for the very first time the app is run and preferences haven't been inited yet.
        if (sharedPreferences.getInt("actionIndex", -1) == -1) {
//            sharedPreferences.getLong();
            initializeFirstRun();
        }
    }

    String All_Lifestyles = "Lifestyle_01,Lifestyle_02,Lifestyle_03,Test_Lifestyle_01,Failed_Lifestyle_01";
    String All_Reminders = "Reminder_01,Reminder_02,Reminder_03,Reminder_04,Test_Reminder_01,Failed_Reminder_01";
    String All_Notifications = "Notification_01,Notification_02,Notification_03,Test_Notification_01,Failed_Notification_01";
    String All_Actions = "Test_Action_01,Failed_Action_01";

    String lifestyle_01 = "{\"lifestyleReminders\":[\"Reminder_01\",\"Reminder_02\",\"Reminder_03\",\"Reminder_04\"],\"key\":\"Lifestyle_01\",\"name\":\"Happy Time\",\"enabled\":false}";
    String lifestyle_02 = "{\"lifestyleReminders\":[\"Reminder_01\",\"Reminder_02\",\"Reminder_03\",\"Reminder_04\"],\"key\":\"Lifestyle_02\",\"name\":\"UCSC\",\"enabled\":true}";
    String lifestyle_03 = "{\"lifestyleReminders\":[\"Reminder_01\",\"Reminder_02\",\"Reminder_03\",\"Reminder_04\"],\"key\":\"Lifestyle_03\",\"name\":\"Vacation\",\"enabled\":true}";
    String test_Lifestyle_01 = "{\"lifestyleReminders\":[\"Test_Reminder_01\"],\"key\":\"Test_Lifestyle_01\",\"name\":\"Test Lifestyle 01\",\"enabled\":true}";
    String failed_Lifestyle_01 = "{\"lifestyleReminders\":[\"Failed_Reminder_01\"],\"key\":\"Failed_Lifestyle_01\",\"name\":\"Failed Lifestyle\",\"enabled\":false}";

    String reminder_01 = "{\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"notificationKeys\":[\"Notification_01\",\"Notification_02\",\"Notification_03\"],\"key\":\"Reminder_01\",\"name\":\"Scrum Meeting\",\"enabled\":true}";
    String reminder_02 = "{\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"notificationKeys\":[\"Notification_01\",\"Notification_02\",\"Notification_03\"],\"key\":\"Reminder_02\",\"name\":\"Potty Break\",\"enabled\":true}";
    String reminder_03 = "{\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"notificationKeys\":[\"Notification_01\",\"Notification_02\",\"Notification_03\"],\"key\":\"Reminder_03\",\"name\":\"Time out\",\"enabled\":true}";
    String reminder_04 = "{\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"notificationKeys\":[\"Notification_01\",\"Notification_02\",\"Notification_03\"],\"key\":\"Reminder_04\",\"name\":\"Stuff\",\"enabled\":true}";
    String failed_reminder_01 = "{\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"notificationKeys\":[\"Failed_Notification_01\"],\"key\":\"Failed_Reminder_01\",\"name\":\"Failed Reminder\",\"enabled\":false}";
    String test_reminder_01 = "{\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"notificationKeys\":[\"Test_Notification_01\"],\"key\":\"Test_Reminder_01\",\"name\":\"Test Reminder 01\",\"enabled\":true}";

    String notification_01 = "{\"actionKey\":\"DEFAULT_CHILD_ACTION_KEY\",\"calendar\":{\"year\":2000,\"month\":1,\"dayOfMonth\":1,\"hourOfDay\":23,\"minute\":59,\"second\":59},\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"reminderContainerKey\":\"DEFAULT_PARENT_REMINDER_KEY\",\"repeatDays\":[],\"repeatDaysEnabled\":false,\"repeatEveryBlankDays\":0,\"repeatEveryBlankDaysEnabled\":false,\"key\":\"Notification_01\",\"name\":\"Notification 1\",\"enabled\":true}";
    String notification_02 = "{\"actionKey\":\"DEFAULT_CHILD_ACTION_KEY\",\"calendar\":{\"year\":2000,\"month\":1,\"dayOfMonth\":1,\"hourOfDay\":23,\"minute\":59,\"second\":59},\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"reminderContainerKey\":\"DEFAULT_PARENT_REMINDER_KEY\",\"repeatDays\":[],\"repeatDaysEnabled\":false,\"repeatEveryBlankDays\":0,\"repeatEveryBlankDaysEnabled\":false,\"key\":\"Notification_02\",\"name\":\"Notification 2\",\"enabled\":true}";
    String notification_03 = "{\"actionKey\":\"DEFAULT_CHILD_ACTION_KEY\",\"calendar\":{\"year\":2000,\"month\":1,\"dayOfMonth\":1,\"hourOfDay\":23,\"minute\":59,\"second\":59},\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"reminderContainerKey\":\"DEFAULT_PARENT_REMINDER_KEY\",\"repeatDays\":[],\"repeatDaysEnabled\":false,\"repeatEveryBlankDays\":0,\"repeatEveryBlankDaysEnabled\":false,\"key\":\"Notification_03\",\"name\":\"Notification 3\",\"enabled\":false}";
    String test_notification_01 = "{\"actionKey\":\"DEFAULT_CHILD_ACTION_KEY\",\"calendar\":{\"year\":2000,\"month\":1,\"dayOfMonth\":1,\"hourOfDay\":23,\"minute\":59,\"second\":59},\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"reminderContainerKey\":\"DEFAULT_PARENT_REMINDER_KEY\",\"repeatDays\":[],\"repeatDaysEnabled\":false,\"repeatEveryBlankDays\":0,\"repeatEveryBlankDaysEnabled\":false,\"key\":\"Test_Notification_01\",\"name\":\"Test Notification\",\"enabled\":true}";
    String failed_notification_01 = "{\"actionKey\":\"DEFAULT_CHILD_ACTION_KEY\",\"calendar\":{\"year\":2000,\"month\":1,\"dayOfMonth\":1,\"hourOfDay\":23,\"minute\":59,\"second\":59},\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"reminderContainerKey\":\"DEFAULT_PARENT_REMINDER_KEY\",\"repeatDays\":[],\"repeatDaysEnabled\":false,\"repeatEveryBlankDays\":0,\"repeatEveryBlankDaysEnabled\":false,\"key\":\"Failed_Notification_01\",\"name\":\"Failed Notification\",\"enabled\":false}";

    String test_action_01 = "{\"cameraLight\":false,\"ringtoneDuration\":5000,\"vibrateDuration\":500,\"notificationBar\":true,\"notificationSound\":false,\"ringtoneSound\":false,\"vibrate\":false,\"key\":\"Test_Action_01\",\"name\":\"Test Action\",\"enabled\":true}";
    String failed_action_01 = "{\"cameraLight\":false,\"ringtoneDuration\":5000,\"vibrateDuration\":500,\"notificationBar\":true,\"notificationSound\":false,\"ringtoneSound\":false,\"vibrate\":false,\"key\":\"Failed_Action_01\",\"name\":\"Failed Action\",\"enabled\":false}";
}