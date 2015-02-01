package com.jdndeveloper.lifereminders.EventTypes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.jdndeveloper.lifereminders.MainActivity;
import com.jdndeveloper.lifereminders.R;
import com.jdndeveloper.lifereminders.interfaces.PhoneInterface;
import com.jdndeveloper.lifereminders.phone.Phone;

/**
 * Created by Jayden Navarro on 1/30/2015.
 */
public class Action {
    private boolean notificationBar;

    private boolean vibrate;
    private long vibrateDuration;

    private boolean notificationSound;
    private boolean ringtoneSound;
    private boolean cameraLight;


    public Action() {
        notificationBar = true;

        vibrate = false;
        vibrateDuration = 100;
        notificationSound = false;
        ringtoneSound = false;
        cameraLight = false;
    }

    public void sendCorrectNotification(Context context, String title, String text) {
        PhoneInterface phone = Phone.getInstance(context);

        if (notificationBar) {
            phone.sendMessageToNotificationBar(title, text);
        }

        if (vibrate) phone.vibratePhone(vibrateDuration);

        if (notificationSound) phone.playDefaultNotificationSound();

        if (ringtoneSound) phone.playDefaultRingtoneSound();

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

    public boolean isRingtoneSound() {
        return ringtoneSound;
    }

    public void setRingtoneSound(boolean ringtoneSound) {
        this.ringtoneSound = ringtoneSound;
    }

    public boolean isCameraLight() {
        return cameraLight;
    }

    public void setCameraLight(boolean cameraLight) {
        this.cameraLight = cameraLight;
    }

}
