package com.jdndeveloper.lifereminders.phone;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.jdndeveloper.lifereminders.MainActivity;
import com.jdndeveloper.lifereminders.R;
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
    private static Context context;

    private static Phone ourInstance = new Phone();

    public static PhoneInterface getInstance(Context newContext) {
        context = newContext;
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
    public void sendMessageToNotificationBar(String title, String message) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_action_notificationicon)
                        .setContentTitle(title)
                        .setContentText(message);


        mBuilder.setAutoCancel(true);

        //sets large icon to app logo
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.app_logo);
        mBuilder.setLargeIcon(icon);

        Intent resultIntent = new Intent(context, MainActivity.class);
        // Because clicking the notification opens a new ("special") activity, there's
        // no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT //can be changed later
                );


        mBuilder.setContentIntent(resultPendingIntent);

        // Sets an ID for the notification
        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    @Override
    public void flashCameraLight() {
    }
}
