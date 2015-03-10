package com.jdndeveloper.lifereminders.EventTypes;

import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.storage.Storage;

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
    public List<String> relativeOption;

    private static int tempCount = 0;

    //default constructor
    public Option() {
        this.name = "Default Option" + tempCount;
        this.enabled = true;
        this.relativeOption = new ArrayList<>();
        tempCount++;
    }
    //custom constructor
    public Option(String name, String key, boolean enabled) {
        this.name = name;
        this.key = key;
        this.enabled = enabled;


    }





    public void addRelative(String key) {
        relativeOption.add(key);
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
        if(state == true){
            for(int i = 0; i< relativeOption.size();++i){

                Option o = Storage.getInstance().getOption(this.relativeOption.get(i));

                o.setEnabled(false);
                
                Storage.getInstance().saveOption(o);
            }
        }
    }
}
