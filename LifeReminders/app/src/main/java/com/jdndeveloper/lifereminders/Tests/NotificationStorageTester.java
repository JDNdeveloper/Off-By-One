package com.jdndeveloper.lifereminders.Tests;

import com.jdndeveloper.lifereminders.EventTypes.Action;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.Calendar;

/**
 * Created by Jayden Navarro on 2/23/2015.
 */
public class NotificationStorageTester {

    public static void runTest() {
        testLifestyles();

        testReminders();

        testNotifications();
    }

    private static void testLifestyles() {
        Lifestyle l1 = Storage.getInstance().getLifestyle("Lifestyle_01");
        l1.setName("Fitness");
        Storage.getInstance().replaceAbstractBaseEvent(l1);
    }

    private static void testReminders() {
        Reminder r1 = Storage.getInstance().getReminder("Reminder_02");
        r1.setName("Take Vitamins");
        r1.setLifestyleContainerKey("Lifestyle_01");
        Storage.getInstance().replaceAbstractBaseEvent(r1);

        Reminder r2 = Storage.getInstance().getReminder("Reminder_03");
        r2.setName("Pickup kids from school");
        r2.setLifestyleContainerKey("Lifestyle_02");
        Storage.getInstance().replaceAbstractBaseEvent(r2);

        Reminder r3 = Storage.getInstance().getReminder("Reminder_04");
        r3.setName("Take out the trash");
        r3.setLifestyleContainerKey("Lifestyle_03");
        Storage.getInstance().replaceAbstractBaseEvent(r3);
    }

    private static void testNotifications() {
        Notification n1 = Storage.getInstance().getNotification("Notification_01");
        Notification n2 = Storage.getInstance().getNotification("Notification_02");
        Notification n3 = Storage.getInstance().getNotification("Notification_03");

        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.MONTH, 3);
        c1.set(Calendar.DAY_OF_MONTH, 2);
        c1.set(Calendar.HOUR_OF_DAY, 7);
        c1.set(Calendar.MINUTE, 18);

        n1.setTime(c1);
        n1.setRepeatDaysEnabled(true);
        n1.setRepeatDay(1, true); // sets repeat on Sunday
        n1.setRepeatDay(2, true); // sets repeat on Monday
        n1.setRepeatDay(3, true); // sets repeat on Tuesday
        n1.setRepeatDay(4, true); // sets repeat on Wednesday
        n1.setRepeatDay(5, true); // sets repeat on Thursday
        n1.setRepeatDay(6, true); // sets repeat on Friday
        n1.setRepeatDay(7, true); // sets repeat on Friday

        Action a1 = Storage.getInstance().getAction("Test_Action_01");
        a1.setNotificationSound(true);
        a1.setVibrate(true);

        Storage.getInstance().replaceAbstractBaseEvent(a1);

        n1.setActionKey(a1.getKey());

        n1.setReminderContainerKey("Reminder_01");

        n1.setEnabled(false);

        Storage.getInstance().replaceAbstractBaseEvent(n1);

        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.MONTH, 4);
        c2.set(Calendar.DAY_OF_MONTH, 6);
        c2.set(Calendar.HOUR_OF_DAY, 13);
        c2.set(Calendar.MINUTE, 45);

        n2.setTime(c2);

        n2.setReminderContainerKey("Reminder_04");

        Storage.getInstance().replaceAbstractBaseEvent(n2);

        Calendar c3 = Calendar.getInstance();
        c3.set(Calendar.MONTH, 6);
        c3.set(Calendar.DAY_OF_MONTH, 6);
        c3.set(Calendar.HOUR_OF_DAY, 15);
        c3.set(Calendar.MINUTE, 23);

        n3.setTime(c3);

        Action a3 = Storage.getInstance().getAction("Failed_Action_01");
        a3.setVibrate(true);

        Storage.getInstance().replaceAbstractBaseEvent(a3);

        n3.setActionKey(a3.getKey());

        n3.setRepeatEveryBlankDaysEnabled(true);

        n3.setRepeatEveryBlankDays(3);

        n3.setReminderContainerKey("Reminder_03");

        Storage.getInstance().replaceAbstractBaseEvent(n3);
    }
}
