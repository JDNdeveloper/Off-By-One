package com.jdndeveloper.lifereminders.EventTypes;

import com.jdndeveloper.lifereminders.Constants;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;



/*
 * Created by  agonza26  on 2/28/2015/
 */


public class Option {


    // these need to be overridden by concrete class and are purposely defined to catch bugs
    private String name = "ABSTRACT NAME"; //notification doesn't use this
    private String key = "ABSTRACT_KEY";
    private boolean enabled = false;















    private int op;
    //default constructor
    public Option() {
        this.name = "Default Option";
        this.enabled = true;
    }
    //custom constructor
    public Option(String name, String key, boolean enabled, int op) {
        this.name = name;
        this.key = key;
        this.enabled = enabled;
        //temporary implementation of options, op specifies which type of option,
        //until better settings implemetation
        this.op=op;

    }


    public void doOP() {
        //does whatever operation we want,
        switch(this.op){
            case 0:
                break;
            case 1:
                break;
            case 2:
            default:
                Log.e("Option","options failed");
                break;
        }
    }





    public String getName() { //notification class doesn't use this
        return name;
    }
    public void setName(String newName) { //notification class doesn't use this
        name = newName;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String newKey) { //should only be called by storage!
        key = newKey;
    }

    public boolean isEnabled() { //true if enabled, false if disabled
        return enabled;
    }
    public void setEnabled(boolean state) { //true if enabled, false if disabled
        enabled = state;
    }




}
