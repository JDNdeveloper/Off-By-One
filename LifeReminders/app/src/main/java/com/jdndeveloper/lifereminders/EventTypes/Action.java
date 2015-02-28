package com.jdndeveloper.lifereminders.EventTypes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

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

        if (vibrate) phone.vibratePhone(vibrateDuration);

        if (notificationSound) phone.playDefaultNotificationSound();

        if (ringtoneSound) phone.playDefaultRingtoneSound(ringtoneDuration);

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

}
