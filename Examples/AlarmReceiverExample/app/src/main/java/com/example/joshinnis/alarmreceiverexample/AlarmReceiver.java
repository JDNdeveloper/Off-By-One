package com.example.joshinnis.alarmreceiverexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Josh Innis on 1/27/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    /*When the BR receives an intent, go to MyAlarmService*/
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, AlarmService.class);
        context.startService(service);
    }
}
