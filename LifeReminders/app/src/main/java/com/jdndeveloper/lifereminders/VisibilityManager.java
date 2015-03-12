package com.jdndeveloper.lifereminders;

/**
 * Created by Jayden Navarro on 3/11/2015.
 */
public class VisibilityManager {
    private static boolean mIsVisible = false;
    private static boolean mIsNotificationActivityVisible = false;

    public static void setIsVisible(boolean visible) {
        mIsVisible = visible;
    }

    public static boolean getIsVisible() {
        return mIsVisible;
    }

    public static void setIsNotificationActivityVisible(boolean visible) {
        mIsNotificationActivityVisible = visible;
    }

    public static boolean getIsNotificationActivityVisible() {
        return mIsNotificationActivityVisible;
    }
}
