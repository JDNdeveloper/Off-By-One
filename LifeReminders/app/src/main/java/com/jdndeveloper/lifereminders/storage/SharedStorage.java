package com.jdndeveloper.lifereminders.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.widget.Toast;


/**
 * Created by jgemig on 2/16/2015.
 */
public class SharedStorage {

    private final Context context;
    private static SharedStorage instance;
    private final SharedPreferences sharedPreferences;

    private SharedStorage(Context context){
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static final SharedStorage getInstance(Context context){
        if (instance == null){
            instance = new SharedStorage(context);
        }
        return instance;
    }

    private void toastSaved(CharSequence entry){
        Toast toast = Toast.makeText(context, entry + " has been saved.", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.getView().setBackgroundColor(0xff303030);
        toast.show();
    }

    private void initializeFirstRun(){
        if (sharedPreferences.getString("userPreset1", null) == null)
            sharedPreferencePutString("userPreset1", "7,16.0,0.008,80,6,1,5,-1,-1,3,1,3,-1,-1,1,8,4,0.002");
  }
    private String getPreferenceString(String key){
        return sharedPreferences.getString(key, null);
    }
    private void sharedPreferencePutString(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value).commit();
    }
    private void loadPreferences(){
        // for the very first time the app is run and preferences haven't been inited yet.
/*        if (sharedPreferences.getString("userPreset1", null) == null ){
            initializeFirstRun();
        }
        else
            loadBaseFromCommand(loadCommandFromSharedPreferences());

*/    }

}
