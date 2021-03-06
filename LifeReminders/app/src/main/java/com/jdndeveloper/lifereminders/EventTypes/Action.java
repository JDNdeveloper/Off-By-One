package com.jdndeveloper.lifereminders.EventTypes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.MainActivity;
import com.jdndeveloper.lifereminders.R;
import com.jdndeveloper.lifereminders.interfaces.PhoneInterface;
import com.jdndeveloper.lifereminders.phone.Phone;

import java.util.Scanner;

/**
 * Created by Jayden Navarro on 1/30/2015.
 */
public class Action extends AbstractBaseEvent {
    private boolean notificationBar;

    private boolean vibrate;
    private long vibrateDuration;

    private boolean notificationSound;
    private boolean ringtoneSound;
    private long ringtoneDuration;
    private boolean cameraLight;


    public Action() {
        super("DEFAULT ACTION NAME", "DEFAULT_ACTION_KEY", true);

        notificationBar = true;

        vibrate = false;
        vibrateDuration = Constants.VIBRATION_MEDIUM;
        ringtoneDuration = Constants.RINGTONE_MEDIUM;
        notificationSound = false;
        ringtoneSound = false;
        cameraLight = false;
    }

    public void sendCorrectNotification(Context context, String title, String text, int requestID
            , String remKey) {
        PhoneInterface phone = Phone.getInstance(context);


        if (notificationBar) {
            phone.sendMessageToNotificationBar(title, text, requestID, remKey);
        }

        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        switch (am.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                Log.i("Action","Silent mode");
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                Log.i("Action","Vibrate mode");
                if (vibrate || notificationSound || ringtoneSound) {
                    phone.vibratePhone(vibrateDuration);
                }
                break;
            case AudioManager.RINGER_MODE_NORMAL:
                Log.i("Action","Normal mode");
                if (vibrate) phone.vibratePhone(vibrateDuration);
                if (notificationSound) phone.playDefaultNotificationSound();
                if (ringtoneSound) phone.playDefaultRingtoneSound(ringtoneDuration);
                break;
        }

        if (cameraLight) phone.flashCameraLight();
    }

    //Setters and getters

    public boolean isNotificationBar() {
        return notificationBar;
    }

    public void setNotificationBar(boolean notificationBar) {
        this.notificationBar = notificationBar;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public long getVibrateDuration() {
        return vibrateDuration;
    }

    public void setVibrateDuration(long vibrateDuration) {
        this.vibrateDuration = vibrateDuration;
    }

    public boolean isNotificationSound() {
        return notificationSound;
    }

    public void setNotificationSound(boolean notificationSound) {
        this.notificationSound = notificationSound;
    }

    //public boolean isRingtoneSound() {
    //    return ringtoneSound;
    //}

   // public void setRingtoneSound(boolean ringtoneSound) {
  //      this.ringtoneSound = ringtoneSound;
   // }

    public boolean isCameraLight() {
        return cameraLight;
    }

    public void setCameraLight(boolean cameraLight) {
        this.cameraLight = cameraLight;
    }

    @Override
    public void clean() {
        // fill with whatever needs to be cleaned/removed on object deletion from storage
        Log.e("Action", "clean() called");
    }
}
