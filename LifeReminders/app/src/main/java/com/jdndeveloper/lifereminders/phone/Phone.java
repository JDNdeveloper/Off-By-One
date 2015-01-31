package com.jdndeveloper.lifereminders.phone;

import com.jdndeveloper.lifereminders.interfaces.PhoneInterface;

/**
 * Created by jgemig on 1/31/2015.
 * This class is a singleton and specifically addresses phone related functions.
 * To use it, you need to do, fx:
 *      Phone.getInstance().vibratePhone(1000);
 * Or:
 *      Phone.getInstance().playDefaultNotificationSound();
 */
public class Phone implements PhoneInterface{
    private static Phone ourInstance = new Phone();

    public static PhoneInterface getInstance() {
        return ourInstance;
    }

    private Phone() {}

    // see this class for vibration specifics
    // http://developer.android.com/reference/android/os/Vibrator.html

    // see this link for sounds
    // http://stackoverflow.com/questions/4441334/how-to-play-an-android-notification-sound
    // http://stackoverflow.com/questions/2618182/how-to-play-ringtone-alarm-sound-in-android

    // see this link for camera flash
    // http://stackoverflow.com/questions/6068803/how-to-turn-on-camera-flash-light-programmatically-in-android

    @Override
    public void vibratePhone(long durationMilli) {

    }

    @Override
    public void playDefaultNotificationSound() {

    }

    @Override
    public void playDefaultRingtoneSound() {

    }

    @Override
    public void sendMessageToNotificationBar(String message) {

    }

    @Override
    public void flashCameraLight() {

    }
}
