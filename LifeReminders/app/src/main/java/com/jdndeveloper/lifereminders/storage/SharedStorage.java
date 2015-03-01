package com.jdndeveloper.lifereminders.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventTypes.AbstractBaseEvent;
import com.jdndeveloper.lifereminders.EventTypes.Action;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;

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

    private final int SHARED_STORAGE_VERSION = 2;

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

        sharedPreferencePutInt("version", SHARED_STORAGE_VERSION);

        toastSaved("initializedFirstRun");
    }

    private boolean checkKeyChains(String key){
        if (getSharedPreferenceKey("all_lifestyles").contains(key)) return true;
        if (getSharedPreferenceKey("all_reminders").contains(key)) return true;
        if (getSharedPreferenceKey("all_notifications").contains(key)) return true;
        if (getSharedPreferenceKey("all_actions").contains(key)) return true;
        return false;
    }
    private List<String> toArrayList(String string){
        return new ArrayList<String>(Arrays.asList(string.split("\\,")));
    }
    private boolean addToKeychain(String keyChain, String key){
        String all_keys = getSharedPreferenceKey(keyChain);
        if (all_keys == null) return false;
        if (all_keys.contains(key)) return false;

        if (all_keys != "")
            all_keys += "," + key;
        else
            all_keys = key;

        sharedPreferencePutString(keyChain, all_keys);
        return true;
    }
    private boolean deleteFromKeychain(String keyChain, String key){
        // get the key chain
        String all_keys = getSharedPreferenceKey(keyChain);
        // if the key chain is null - something is wrong
        if (all_keys == null) return false;
        // if the key chain doesn't contain the key - wrong key
        if (all_keys.contains(key) == false) return false;
        // get a list of the keys in the key chain
        List<String>  keyArray = toArrayList(all_keys);
        // iterate through and delete the first matching key - there should only be one
        for (int index = 0; index < keyArray.size(); index++){
            if (keyArray.get(index).contentEquals(key)) {
                keyArray.remove(index);
                break;
            }
        }
        // rebuild the key chain string
        // if size is zero, put in a "" for an empty keychain
        // using a null here will break the app as null means something bad
        if (keyArray.size() == 0) {
            Log.e("SharedStorage","deleteFromKeychain from keychain:" + keyChain);
            all_keys = "";
        }
        else if (keyArray.size() == 1)
            all_keys = keyArray.get(0);
        else {
            all_keys = keyArray.get(0);
            for (int index = 1; index < keyArray.size(); index++){
                all_keys += "," + keyArray.get(index);
            }
        }
        // if it still contains the key - something is wrong
        if (all_keys.contains(key)) return false;
        // otherwise we are good, update the key chain
        sharedPreferencePutString(keyChain, all_keys);
        // notify success
        return true;
    }
    protected boolean commitNewAbstractBaseEvent(AbstractBaseEvent abstractBaseEvent){
        // check for null
        if (abstractBaseEvent == null) return false;
        // get key
        String key = abstractBaseEvent.getKey();
        // check for null key
        if (key == null) return false;
        // check to see if key exists
        if (getSharedPreferenceKey(key) != null) return false;
        // verify key is not already in a keychain
        if (checkKeyChains(key) == true) return false;
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

        if (addToKeychain(keyChain, key) == false) return false;

        return saveAbstractBaseEvent(abstractBaseEvent);
    }

    protected boolean saveAbstractBaseEvent(AbstractBaseEvent abstractBaseEvent){
        // check that null wasn't passed
        if (abstractBaseEvent == null) return false;

        String key = abstractBaseEvent.getKey();
        // verify the key isn't null
        if (key == null) return false;
        // verify the key is in a keychain
        if (checkKeyChains(key) == false) return false;

        String gsonString = gsonObject.toJson(abstractBaseEvent);
        sharedPreferencePutString(key, gsonString);
        return true;
    }
    protected boolean deleteAbstractBaseEvent(AbstractBaseEvent abstractBaseEvent){
        // check that null wasn't passed
        if (abstractBaseEvent == null) return false;
        // get the key
        String key = abstractBaseEvent.getKey();
        // protect the failure keys, we don't want to delete these
        if (key.contentEquals(Constants.LIFESTYLE_FAILED_KEY)) return true;
        if (key.contentEquals(Constants.REMINDER_FAILED_KEY)) return true;
        if (key.contentEquals(Constants.NOTIFICATION_FAILED_KEY)) return true;
        if (key.contentEquals(Constants.ACTION_FAILED_KEY)) return true;
        // log the deletes - for tracking
        Log.e("SharedStorage", "deleteAbstractBaseEvent - " + key);
        Log.e("SharedStorage", "deleteAbstractBaseEvent - " + abstractBaseEvent.getName());
        // verify the key isn't null
        if (key == null) return false;
        // verify the key is in a keychain
        if (checkKeyChains(key) == false) return false;

        if (abstractBaseEvent instanceof Lifestyle){

        }
        else if (abstractBaseEvent instanceof Reminder){

        }
        else if (abstractBaseEvent instanceof Notification){
            // cast to notification
            Notification notification = (Notification) abstractBaseEvent;
            // get the parent reminder
            Reminder reminder = Storage.getInstance().getReminder(notification.getReminderContainerKey());
            // get the action inside
            Action action = Storage.getInstance().getAction(notification.getActionKey());
            // delete the action, bail on fail
            if (deleteAbstractBaseEvent(action) == false) return false;
            // delete the notification from the key chain, bail on fail
            if (deleteFromKeychain("all_notifications", key) == false) return false;
            // remove the key
            sharedPreferenceDeleteKey(key);
            List<String> notificationKeys = reminder.getNotificationKeys();
            for (int index = 0; index < notificationKeys.size(); index++){
                if (notificationKeys.get(index).contentEquals(key) == true){
                    notificationKeys.remove(index);
                    break;
                }
            }
            // remove the key
            sharedPreferenceDeleteKey(key);
            if (saveAbstractBaseEvent(reminder) == false) return false;
        }
        else if (abstractBaseEvent instanceof Action) {
            // delete the action from the key chain, bail on fail
            if (deleteFromKeychain("all_actions", key) == false) return false;
            // remove the key
            sharedPreferenceDeleteKey(key);
        }
        else
            return false;

        return true;
    }
    protected String getNewLifestyleKey(){
        int key = sharedPreferences.getInt("lifestyleIndex", -1);

        if (key != -1) {
            sharedPreferencePutInt("lifestyleIndex", ++key);
            return ("Lifestyle_" + key);
        }
        return null;
    }

    protected String getNewReminderKey(){
        int key = sharedPreferences.getInt("reminderIndex", -1);

        if (key != -1) {
            sharedPreferencePutInt("reminderIndex", ++key);
            return ("Reminder_" + key);
        }
        return null;
    }

    protected String getNewNotificationKey(){
        int key = sharedPreferences.getInt("notificationIndex", -1);

        if (key != -1) {
            sharedPreferencePutInt("notificationIndex", ++key);
            return ("Notification_" + key);
        }
        return null;
    }
    protected String getNewActionKey(){
        int key = sharedPreferences.getInt("actionIndex", -1);

        if (key != -1) {
            sharedPreferencePutInt("actionIndex", ++key);
            return ("Action_" + key);
        }
        return null;
    }

    protected String getSharedPreferenceKey(String key){
        return sharedPreferences.getString(key, null);
    }
    private void sharedPreferenceDeleteKey(String key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key).commit();
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
        if (sharedPreferences.getInt("version", -1) != SHARED_STORAGE_VERSION) {
//            sharedPreferences.getLong();
            initializeFirstRun();
        }
    }

    private String All_Lifestyles = "Lifestyle_01,Lifestyle_02,Lifestyle_03,Test_Lifestyle_01,Failed_Lifestyle_01";
    private String All_Reminders = "Reminder_01,Reminder_02,Reminder_03,Reminder_04,Test_Reminder_01,Failed_Reminder_01";
    private String All_Notifications = "Notification_01,Notification_02,Notification_03,Test_Notification_01,Failed_Notification_01";
    private String All_Actions = "Test_Action_01,Failed_Action_01";

//    private String lifestyle_01 = "{\"lifestyleReminders\":[\"Reminder_01\",\"Reminder_02\",\"Reminder_03\",\"Reminder_04\"],\"key\":\"Lifestyle_01\",\"name\":\"Happy Time\",\"enabled\":false}";
    private String lifestyle_01 = "{\"lifestyleReminders\":[\"Reminder_01\"],\"key\":\"Lifestyle_01\",\"name\":\"Happy Time\",\"enabled\":false}";
//    private String lifestyle_02 = "{\"lifestyleReminders\":[\"Reminder_01\",\"Reminder_02\",\"Reminder_03\",\"Reminder_04\"],\"key\":\"Lifestyle_02\",\"name\":\"UCSC\",\"enabled\":true}";
    private String lifestyle_02 = "{\"lifestyleReminders\":[\"Reminder_02\"],\"key\":\"Lifestyle_02\",\"name\":\"UCSC\",\"enabled\":true}";
//    private String lifestyle_03 = "{\"lifestyleReminders\":[\"Reminder_01\",\"Reminder_02\",\"Reminder_03\",\"Reminder_04\"],\"key\":\"Lifestyle_03\",\"name\":\"Vacation\",\"enabled\":true}";
    private String lifestyle_03 = "{\"lifestyleReminders\":[\"Reminder_03\",\"Reminder_04\"],\"key\":\"Lifestyle_03\",\"name\":\"Vacation\",\"enabled\":true}";
    private String test_Lifestyle_01 = "{\"lifestyleReminders\":[\"Test_Reminder_01\"],\"key\":\"Test_Lifestyle_01\",\"name\":\"Test Lifestyle 01\",\"enabled\":true}";
    private String failed_Lifestyle_01 = "{\"lifestyleReminders\":[\"Failed_Reminder_01\"],\"key\":\"Failed_Lifestyle_01\",\"name\":\"Failed Lifestyle\",\"enabled\":true}";

    private String reminder_01 = "{\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"notificationKeys\":[\"Notification_01\",\"Notification_02\",\"Notification_03\"],\"key\":\"Reminder_01\",\"name\":\"Scrum Meeting\",\"enabled\":true}";
    private String reminder_02 = "{\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"notificationKeys\":[\"Notification_01\",\"Notification_02\",\"Notification_03\"],\"key\":\"Reminder_02\",\"name\":\"Potty Break\",\"enabled\":true}";
    private String reminder_03 = "{\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"notificationKeys\":[\"Notification_01\",\"Notification_02\",\"Notification_03\"],\"key\":\"Reminder_03\",\"name\":\"Time out\",\"enabled\":true}";
    private String reminder_04 = "{\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"notificationKeys\":[\"Notification_01\",\"Notification_02\",\"Notification_03\"],\"key\":\"Reminder_04\",\"name\":\"Stuff\",\"enabled\":true}";
    private String failed_reminder_01 = "{\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"notificationKeys\":[\"Failed_Notification_01\"],\"key\":\"Failed_Reminder_01\",\"name\":\"Failed Reminder\",\"enabled\":true}";
    private String test_reminder_01 = "{\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"notificationKeys\":[\"Test_Notification_01\"],\"key\":\"Test_Reminder_01\",\"name\":\"Test Reminder 01\",\"enabled\":true}";

    private String notification_01 = "{\"actionKey\":\"DEFAULT_CHILD_ACTION_KEY\",\"calendar\":{\"year\":2000,\"month\":1,\"dayOfMonth\":1,\"hourOfDay\":23,\"minute\":59,\"second\":59},\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"reminderContainerKey\":\"DEFAULT_PARENT_REMINDER_KEY\",\"repeatDays\":[],\"repeatDaysEnabled\":false,\"repeatEveryBlankDays\":0,\"repeatEveryBlankDaysEnabled\":false,\"key\":\"Notification_01\",\"name\":\"Notification 1\",\"enabled\":true}";
    private String notification_02 = "{\"actionKey\":\"DEFAULT_CHILD_ACTION_KEY\",\"calendar\":{\"year\":2000,\"month\":1,\"dayOfMonth\":1,\"hourOfDay\":23,\"minute\":59,\"second\":59},\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"reminderContainerKey\":\"DEFAULT_PARENT_REMINDER_KEY\",\"repeatDays\":[],\"repeatDaysEnabled\":false,\"repeatEveryBlankDays\":0,\"repeatEveryBlankDaysEnabled\":false,\"key\":\"Notification_02\",\"name\":\"Notification 2\",\"enabled\":true}";
    private String notification_03 = "{\"actionKey\":\"DEFAULT_CHILD_ACTION_KEY\",\"calendar\":{\"year\":2000,\"month\":1,\"dayOfMonth\":1,\"hourOfDay\":23,\"minute\":59,\"second\":59},\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"reminderContainerKey\":\"DEFAULT_PARENT_REMINDER_KEY\",\"repeatDays\":[],\"repeatDaysEnabled\":false,\"repeatEveryBlankDays\":0,\"repeatEveryBlankDaysEnabled\":false,\"key\":\"Notification_03\",\"name\":\"Notification 3\",\"enabled\":false}";
    private String test_notification_01 = "{\"actionKey\":\"DEFAULT_CHILD_ACTION_KEY\",\"calendar\":{\"year\":2000,\"month\":1,\"dayOfMonth\":1,\"hourOfDay\":23,\"minute\":59,\"second\":59},\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"reminderContainerKey\":\"DEFAULT_PARENT_REMINDER_KEY\",\"repeatDays\":[],\"repeatDaysEnabled\":false,\"repeatEveryBlankDays\":0,\"repeatEveryBlankDaysEnabled\":false,\"key\":\"Test_Notification_01\",\"name\":\"Test Notification\",\"enabled\":true}";
    private String failed_notification_01 = "{\"actionKey\":\"DEFAULT_CHILD_ACTION_KEY\",\"calendar\":{\"year\":2000,\"month\":1,\"dayOfMonth\":1,\"hourOfDay\":23,\"minute\":59,\"second\":59},\"lifestyleContainerKey\":\"DEFAULT_PARENT_LIFESTYLE_KEY\",\"reminderContainerKey\":\"DEFAULT_PARENT_REMINDER_KEY\",\"repeatDays\":[],\"repeatDaysEnabled\":false,\"repeatEveryBlankDays\":0,\"repeatEveryBlankDaysEnabled\":false,\"key\":\"Failed_Notification_01\",\"name\":\"Failed Notification\",\"enabled\":false}";

    private String test_action_01 = "{\"cameraLight\":false,\"ringtoneDuration\":5000,\"vibrateDuration\":500,\"notificationBar\":true,\"notificationSound\":false,\"ringtoneSound\":false,\"vibrate\":false,\"key\":\"Test_Action_01\",\"name\":\"Test Action\",\"enabled\":true}";
    private String failed_action_01 = "{\"cameraLight\":false,\"ringtoneDuration\":5000,\"vibrateDuration\":500,\"notificationBar\":true,\"notificationSound\":false,\"ringtoneSound\":false,\"vibrate\":false,\"key\":\"Failed_Action_01\",\"name\":\"Failed Action\",\"enabled\":false}";
}