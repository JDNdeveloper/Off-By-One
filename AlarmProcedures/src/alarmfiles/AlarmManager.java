package alarmfiles;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Jayden Navarro on 3/13/2015.
 */
public class AlarmManager {

    private Calendar calendar;

    private ArrayList<Integer> repeatDays; //Uses Calendar static day values

    private boolean repeatDaysEnabled;

    private int repeatEveryBlankDays; //1 means daily, 2 means once every two days, ...
    private boolean repeatEveryBlankDaysEnabled;

    public AlarmManager() {
        repeatDays = new ArrayList<Integer>(7);
        repeatDaysEnabled = false;

        repeatEveryBlankDays = 0;
        repeatEveryBlankDaysEnabled = false;

        Calendar newCalendar = Calendar.getInstance();
        newCalendar.set(Calendar.SECOND, 0);
        newCalendar.set(Calendar.MINUTE, 59);
        newCalendar.set(Calendar.HOUR_OF_DAY, 23);
        newCalendar.set(Calendar.DAY_OF_MONTH, 1);
        newCalendar.set(Calendar.MONTH, 1);
        newCalendar.set(Calendar.YEAR, 2000);

        calendar = newCalendar;
    }

    //BELOW COMMENTED OUT CODE IS THE OLD CODE THAT FAILED

    /*
    //sets an alarm for the current scheduled time of the notification
    public void setAlarm(Calendar rightNow) {

        long current = calendar.getTimeInMillis();
        if (this.isRepeatDaysEnabled()) {
            calendar.set(Calendar.DATE, rightNow.get(Calendar.DATE) - 1);
        } else if (this.isRepeatEveryBlankDaysEnabled()) {
            while (calendar.getTimeInMillis() > rightNow.getTimeInMillis()) {
                calendar.add(Calendar.DATE, -getRepeatEveryBlankDays());
            }
        }
        while (calendar.getTimeInMillis() < rightNow.getTimeInMillis()) {
            if (this.isRepeatEveryBlankDaysEnabled() || this.isRepeatDaysEnabled()) {
                if (repeatDaysEnabled) {
                    do {
                        calendar.add(Calendar.DATE, 1);
                    } while (!repeatDays.contains(calendar.get(Calendar.DAY_OF_WEEK)));
                } else if (repeatEveryBlankDaysEnabled) {
                    calendar.add(Calendar.DATE, repeatEveryBlankDays);
                }
                if (current == calendar.getTimeInMillis()) // meaning nothing has changed
                    break;
                current = calendar.getTimeInMillis();
            } else {
                return;
            }
        }
    }

    public Calendar makeNextNotificationTime(Calendar rightNow) {
        if (repeatDaysEnabled) {
            do {
                calendar.add(Calendar.DATE, 1);
            } while (!repeatDays.contains(calendar.get(Calendar.DAY_OF_WEEK)));
        } else if (repeatEveryBlankDaysEnabled) {
            calendar.add(Calendar.DATE, repeatEveryBlankDays);
        }

        this.setAlarm(rightNow);
        return calendar;
    }
    */

    //Below code is the code that passes

    //tester passes in fake rightNow time in order to properly test functionality
    public void setAlarm(Calendar rightNow) {

        if (this.isRepeatDaysEnabled()) {
            setupRepeatDays(rightNow);

        } else if (this.isRepeatEveryBlankDaysEnabled()) {
            setupRepeatEveryBlankDays(rightNow);
        }

        while (calendar.getTimeInMillis() < rightNow.getTimeInMillis()) {
            if (repeatDaysEnabled) {
                makeNextRepeatDays();
            } else if (repeatEveryBlankDaysEnabled) {
                makeNextRepeatEveryBlankDays();
            } else {
                return;
            }
        }

        setAndroidAlarm();
    }




    public void makeNextNotificationTime(Calendar rightNow) {

        if (repeatDaysEnabled) {
            makeNextRepeatDays();
        } else if (repeatEveryBlankDaysEnabled) {
            makeNextRepeatEveryBlankDays();
        } else {
            return;
        }


        setAndroidAlarm();
        //setAlarm(rightNow);
    }



    public void setupRepeatEveryBlankDays(Calendar rightNow) {
        while (calendar.getTimeInMillis() > rightNow.getTimeInMillis()) {
            //subtracts in one day increments
            for (int i = 0; i < repeatEveryBlankDays; i++) {
                calendar.add(Calendar.DATE, -1); //sets one day back
            }
        }
    }

    public void setupRepeatDays(Calendar rightNow) {

        Calendar newCalendar = (Calendar) rightNow.clone();

        newCalendar.add(Calendar.DATE, -1); // sets one day back

        newCalendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        newCalendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));

        calendar = (Calendar) newCalendar.clone();
    }

    private void makeNextRepeatDays() {
        do {
            calendar.add(Calendar.DATE, 1);
        } while (!repeatDays.contains(calendar.get(Calendar.DAY_OF_WEEK)));
    }

    private void makeNextRepeatEveryBlankDays() {
        for (int i = 0; i < repeatEveryBlankDays; i++) {
            calendar.add(Calendar.DATE, 1);
        }
    }


    private void setAndroidAlarm() {
        calendar.set(Calendar.SECOND, 0);
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    //public ArrayList<Integer> getRepeatDays() {
      //  return repeatDays;
    //}

    public void setRepeatDays(ArrayList<Integer> repeatDays) {
        this.repeatDays = repeatDays;
    }

    public boolean isRepeatDaysEnabled() {
        return repeatDaysEnabled;
    }

    public void setRepeatDaysEnabled(boolean repeatDaysEnabled) {
        this.repeatDaysEnabled = repeatDaysEnabled;
    }

    public int getRepeatEveryBlankDays() {
        return repeatEveryBlankDays;
    }

    public void setRepeatEveryBlankDays(int repeatEveryBlankDays) {
        this.repeatEveryBlankDays = repeatEveryBlankDays;
    }

    public boolean isRepeatEveryBlankDaysEnabled() {
        return repeatEveryBlankDaysEnabled;
    }

    public void setRepeatEveryBlankDaysEnabled(boolean repeatEveryBlankDaysEnabled) {
        this.repeatEveryBlankDaysEnabled = repeatEveryBlankDaysEnabled;
    }
}
