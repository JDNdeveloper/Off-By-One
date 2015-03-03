package com.jdndeveloper.lifereminders.AlarmReceiverAndService;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventActivities.NotificationActivity;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.MainActivity;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayden Navarro on 3/2/2015.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        List<Lifestyle> lifestyles = Storage.getInstance().getAllLifestyles();
        for (Lifestyle life : lifestyles) {
            life.setAlarms(context);
            //Log.e("BootReceiver", life.getName()); logs can't happen here :'(
        }

        //COMMENT OUT BELOW CODE, JUST FOR TESTING TO SEE IF RECEIVER WORKS
        //Intent mainIntent = new Intent(context, MainActivity.class);
        //context.startActivity(mainIntent);
    }
}