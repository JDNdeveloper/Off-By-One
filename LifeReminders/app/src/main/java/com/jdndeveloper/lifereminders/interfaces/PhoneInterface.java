package com.jdndeveloper.lifereminders.interfaces;

/**
 * Created by jgemig on 1/31/2015.
 * This interface is for phone operations
 */
public interface PhoneInterface {
    public void vibratePhone(long durationMilli);
    public void playDefaultNotificationSound();
    public void playDefaultRingtoneSound(long durationMilli);
    public void sendMessageToNotificationBar(String title, String message, int requestID
            , String remKey);
    public void flashCameraLight();
}
