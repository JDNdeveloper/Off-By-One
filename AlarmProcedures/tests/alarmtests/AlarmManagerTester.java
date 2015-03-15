package alarmtests;

import alarmfiles.AlarmManager;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jayden Navarro on 3/13/2015.
 */
public class AlarmManagerTester {

    private final static String TAG = "AlarmManagerTester: ";

    private final Random random = new Random();

    @Test
    public void setAlarmRepeatDays() {
        System.out.println(TAG + "testing setAlarm()");

        Calendar rightNow = Calendar.getInstance();
        Calendar correctCalendar = Calendar.getInstance();



        AlarmManager alarmManager = new AlarmManager();

        //Mon Tue Fri testing
        ArrayList<Integer> repeatDays = new ArrayList<Integer>(Arrays.asList(Calendar.MONDAY, Calendar.TUESDAY, Calendar.FRIDAY));

        alarmManager.setRepeatDays(repeatDays);
        alarmManager.setRepeatDaysEnabled(true);

        correctCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        alarmManager.setCalendar((Calendar) correctCalendar.clone());

        rightNow.setTimeInMillis(correctCalendar.getTimeInMillis());

        rightNow.set(Calendar.HOUR_OF_DAY, 3);
        rightNow.set(Calendar.MINUTE, 20);

        //correctCalendar.set(Calendar.HOUR_OF_DAY, 3);
        //correctCalendar.set(Calendar.MINUTE, 20);

        for (int day = 0; day < 10000; day++) {
            //alarmManager.setCalendar(correctCalendar);

            Calendar randomDayOffsetCalendar = getOffsetCalendar(alarmManager.getCalendar(), -5000, 5000, 1);

            alarmManager.setCalendar(randomDayOffsetCalendar);

            //rightNow.setTimeInMillis(correctCalendar.getTimeInMillis());

            alarmManager.setAlarm(rightNow);

            assertEquals(buildCalendarString(correctCalendar, alarmManager.getCalendar()), correctCalendar, alarmManager.getCalendar());

            int currentDayOfWeek = rightNow.get(Calendar.DAY_OF_WEEK);
            if (currentDayOfWeek == Calendar.MONDAY || currentDayOfWeek == Calendar.TUESDAY || currentDayOfWeek == Calendar.FRIDAY)
                assertTrue(TAG + "set to incorrect date", setToProperDay(correctCalendar));

            addDaysToCalendar(rightNow, 1);
        }
        //alarmManager.setAlarm();

        //assertTrue(TAG + "set alarm failed", alarmManager.getCalendar().getTimeInMillis() == correctCalendar.getTimeInMillis());
    }

    @Test
    public void setAlarmRepeatEveryBlankDays() {
        Calendar rightNow = Calendar.getInstance();
        Calendar correctCalendar = Calendar.getInstance();

        AlarmManager alarmManager = new AlarmManager();

        //repeat every 1, 2, 3, 4, 5, 6, 7 days testing
        alarmManager = new AlarmManager();

        alarmManager.setRepeatEveryBlankDaysEnabled(true);


        for (int repeatInterval = 1; repeatInterval <= 7; repeatInterval++) {
            alarmManager.setRepeatEveryBlankDays(repeatInterval);

            correctCalendar = Calendar.getInstance();
            alarmManager.setCalendar((Calendar) correctCalendar.clone());

            String initialDates = "\n\n";

            initialDates += "Expected First Date: " + correctCalendar.get(Calendar.DAY_OF_YEAR);
            initialDates += "\nActual First Date: " + alarmManager.getCalendar().get(Calendar.DAY_OF_YEAR);

            //initialDates += "\n\n";

            for (int day = 0; day < 10000; day++) {
                Calendar randomDayOffsetCalendar = getOffsetCalendar(alarmManager.getCalendar(), -50, 50, alarmManager.getRepeatEveryBlankDays());

                alarmManager.setCalendar(randomDayOffsetCalendar);

                rightNow.setTimeInMillis(correctCalendar.getTimeInMillis());

                rightNow.set(Calendar.HOUR_OF_DAY, 3);
                rightNow.set(Calendar.MINUTE, 20);

                alarmManager.setAlarm(rightNow);

                assertEquals(initialDates + "\n\nRepeat every: " + alarmManager.getRepeatEveryBlankDays() + "\nDay: " + day +
                        buildCalendarString(correctCalendar, alarmManager.getCalendar()), correctCalendar, alarmManager.getCalendar());

                addDaysToCalendar(correctCalendar, alarmManager.getRepeatEveryBlankDays());
            }
        }



    }

    public int randInt(int min, int max) {

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = random.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    @Test
    public void setupRepeatDays() {
        System.out.println(TAG + "testing makeNextRepeatDays()");

        Calendar rightNow = Calendar.getInstance();
        Calendar correctCalendar = Calendar.getInstance();

        AlarmManager alarmManager = new AlarmManager();


    }

    @Test
    public void setupRepeatEveryBlankDays() {
        System.out.println(TAG + "testing makeNextRepeatDays()");

        Calendar rightNow = Calendar.getInstance();
        Calendar correctCalendar = Calendar.getInstance();

        AlarmManager alarmManager = new AlarmManager();

        alarmManager.setRepeatEveryBlankDaysEnabled(true);



        for (int repeatInterval = 1; repeatInterval <= 7; repeatInterval++) {
            alarmManager.setRepeatEveryBlankDays(repeatInterval);

            correctCalendar = Calendar.getInstance();
            alarmManager.setCalendar((Calendar) correctCalendar.clone());

            for (int day = 0; day < 10000; day++) {
                Calendar randomDayOffsetCalendar = getOffsetCalendar(alarmManager.getCalendar(), -50, 50, alarmManager.getRepeatEveryBlankDays());

                alarmManager.setCalendar(randomDayOffsetCalendar);

                rightNow.setTimeInMillis(correctCalendar.getTimeInMillis());

                rightNow.set(Calendar.HOUR_OF_DAY, 3);
                rightNow.set(Calendar.MINUTE, 20);

                alarmManager.setupRepeatEveryBlankDays(rightNow);

                assertTrue("\n\nRepeat every: " + alarmManager.getRepeatEveryBlankDays() + "\nDay: " + day +
                        buildCalendarString(correctCalendar, alarmManager.getCalendar()), correctCalendar.getTimeInMillis() > alarmManager.getCalendar().getTimeInMillis());

                addDaysToCalendar(correctCalendar, alarmManager.getRepeatEveryBlankDays());
            }
        }



        /*
        alarmManager.setRepeatEveryBlankDays(3);

        correctCalendar = Calendar.getInstance();
        alarmManager.setCalendar((Calendar) correctCalendar.clone());

        Calendar randomDayOffsetCalendar = (Calendar) alarmManager.getCalendar().clone();

        //sets day to plus or minus 20
        int randomDayOffset = randInt(-5000, 5000);

        addDaysToCalendar(randomDayOffsetCalendar, randomDayOffset);

        alarmManager.setCalendar(randomDayOffsetCalendar);

        //addDaysToCalendar(correctCalendar, alarmManager.getRepeatEveryBlankDays());

        rightNow.setTimeInMillis(correctCalendar.getTimeInMillis());

        rightNow.set(Calendar.HOUR_OF_DAY, 3);
        rightNow.set(Calendar.MINUTE, 20);

        alarmManager.setupRepeatEveryBlankDays(rightNow);

        System.out.println(buildCalendarString(rightNow, alarmManager.getCalendar()));

        correctCalendar.set(Calendar.DATE, 11);

        assertEquals(buildCalendarString(correctCalendar, alarmManager.getCalendar()),
                correctCalendar, alarmManager.getCalendar());
        */
    }

    @Test
    public void makeNextRepeatDays() {
        System.out.println(TAG + "testing makeNextRepeatDays()");

        Calendar rightNow = Calendar.getInstance();
        Calendar correctCalendar = Calendar.getInstance();

        AlarmManager alarmManager = new AlarmManager();

        //Mon Tue Fri testing
        ArrayList<Integer> repeatDays = new ArrayList<Integer>(Arrays.asList(Calendar.MONDAY, Calendar.TUESDAY, Calendar.FRIDAY));

        alarmManager.setRepeatDays(repeatDays);
        alarmManager.setRepeatDaysEnabled(true);

        correctCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        alarmManager.setCalendar((Calendar) correctCalendar.clone());

        for (int day = 0; day < 10000; day++) {

            assertTrue(TAG + "set to incorrect date", setToProperDay(correctCalendar));

            rightNow.setTimeInMillis(correctCalendar.getTimeInMillis());

            rightNow.set(Calendar.HOUR_OF_DAY, 3);
            rightNow.set(Calendar.MINUTE, 20);

            alarmManager.makeNextNotificationTime(rightNow);

            assertEquals(buildCalendarString(correctCalendar, alarmManager.getCalendar()), correctCalendar, alarmManager.getCalendar());
        }
    }

    @Test
    public void makeNextRepeatEveryBlankDays() {
        System.out.println(TAG + "testing makeNextRepeatEveryBlankDays()");

        Calendar rightNow = Calendar.getInstance();
        Calendar correctCalendar = Calendar.getInstance();

        AlarmManager alarmManager = new AlarmManager();

        //repeat every 1, 2, 3, 4, 5, 6, 7 days testing
        alarmManager = new AlarmManager();

        alarmManager.setRepeatEveryBlankDaysEnabled(true);

        correctCalendar = Calendar.getInstance();
        alarmManager.setCalendar((Calendar) correctCalendar.clone());

        for (int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++) {
            alarmManager.setRepeatEveryBlankDays(dayOfWeek);
            for (int day = 0; day < 10000; day++) {

                for (int i = 0; i < alarmManager.getRepeatEveryBlankDays(); i++) {
                    correctCalendar.add(Calendar.DATE, 1);
                }


                rightNow.setTimeInMillis(correctCalendar.getTimeInMillis());

                rightNow.set(Calendar.HOUR_OF_DAY, 3);
                rightNow.set(Calendar.MINUTE, 20);

                alarmManager.makeNextNotificationTime(rightNow);

                assertEquals(buildCalendarString(correctCalendar, alarmManager.getCalendar()), correctCalendar, alarmManager.getCalendar());
            }
        }
    }


    //Above two tests do the same thing
    /*
    @Test
    public void makeNextNotificationTime() {
        System.out.println(TAG + "testing makeNextNotificationTime()");

        Calendar rightNow = Calendar.getInstance();
        Calendar correctCalendar = Calendar.getInstance();

        AlarmManager alarmManager = new AlarmManager();

        //Mon Tue Fri testing
        ArrayList<Integer> repeatDays = new ArrayList<Integer>(Arrays.asList(Calendar.MONDAY, Calendar.TUESDAY, Calendar.FRIDAY));

        alarmManager.setRepeatDays(repeatDays);
        alarmManager.setRepeatDaysEnabled(true);

        correctCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        alarmManager.setCalendar((Calendar) correctCalendar.clone());

        for (int day = 0; day < 10000; day++) {

            assertTrue(TAG + "set to incorrect date", setToProperDay(correctCalendar));

            rightNow.setTimeInMillis(correctCalendar.getTimeInMillis());

            rightNow.set(Calendar.HOUR_OF_DAY, 3);
            rightNow.set(Calendar.MINUTE, 20);

            alarmManager.makeNextNotificationTime(rightNow);

            assertEquals(buildCalendarString(correctCalendar, alarmManager.getCalendar()), correctCalendar, alarmManager.getCalendar());
        }

        //repeat every 1, 2, 3, 4, 5, 6, 7 days testing
        alarmManager = new AlarmManager();

        alarmManager.setRepeatEveryBlankDaysEnabled(true);

        correctCalendar = Calendar.getInstance();
        alarmManager.setCalendar((Calendar) correctCalendar.clone());

        for (int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++) {
            alarmManager.setRepeatEveryBlankDays(dayOfWeek);

            correctCalendar = Calendar.getInstance();
            alarmManager.setCalendar((Calendar) correctCalendar.clone());

            for (int day = 0; day < 10000; day++) {
                addDaysToCalendar(correctCalendar, alarmManager.getRepeatEveryBlankDays());

                rightNow.setTimeInMillis(correctCalendar.getTimeInMillis());

                rightNow.set(Calendar.HOUR_OF_DAY, 3);
                rightNow.set(Calendar.MINUTE, 20);

                alarmManager.makeNextNotificationTime(rightNow);

                assertEquals(buildCalendarString(correctCalendar, alarmManager.getCalendar()), correctCalendar, alarmManager.getCalendar());
            }
        }
    }
    */

    public boolean setToProperDay(Calendar calendar) {
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                calendar.add(Calendar.DATE, 1);
                //calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                break;
            case Calendar.TUESDAY:
                addDaysToCalendar(calendar, 3);
                //calendar.add(Calendar.DATE, 3);
                //calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                break;
            case Calendar.FRIDAY:
                addDaysToCalendar(calendar, 3);
                //calendar.add(Calendar.DATE, 3);
                //calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
            default:
                return false;
        }

        return true;
    }

    private void addDaysToCalendar(Calendar calendar, int numberOfDays) {
        for (int i = 0; i < numberOfDays; i++) {
            calendar.add(Calendar.DATE, 1);
        }
    }

    @Test
    public void calendarTester() {

        assertEquals(TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)*3, TimeUnit.MILLISECONDS.convert(3, TimeUnit.DAYS));

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_YEAR, 73);

        calendar.set(Calendar.YEAR, 2015);

        int properDayOfWeek = Calendar.TUESDAY;
        int properDate = 29;
        int properYear = 2042;
        int properMonth = Calendar.JULY;

        for (int day = 0; day < 9999; day++) {
            calendar.add(Calendar.DATE, 1);
        }

        System.out.println(calendar);

        assertEquals(TAG + "Year: ",
                properYear, calendar.get(Calendar.YEAR));

        assertEquals(TAG + "Month: ",
                properMonth, calendar.get(Calendar.MONTH));

        assertEquals(TAG + "Date: ",
                properDate, calendar.get(Calendar.DATE));

        assertEquals(TAG + "Day of Week: ",
                properDayOfWeek, calendar.get(Calendar.DAY_OF_WEEK));
    }

    public String buildCalendarString(Calendar expectedCalendar, Calendar actualCalendar) {
        String calendarString = "\n\n";

        calendarString += "Expected Date: " + expectedCalendar.get(Calendar.DATE) + "\n";
        calendarString += "Actual Date: " + actualCalendar.get(Calendar.DATE) + "\n";

        calendarString += "\n";

        calendarString += "Expected Month: " + expectedCalendar.get(Calendar.MONTH) + "\n";
        calendarString += "Actual Month: " + actualCalendar.get(Calendar.MONTH) + "\n";

        calendarString += "\n";

        calendarString += "Expected Year: " + expectedCalendar.get(Calendar.YEAR) + "\n";
        calendarString += "Actual Year: " + actualCalendar.get(Calendar.YEAR) + "\n";

        calendarString += "\n";

        calendarString += "Expected Hour: " + expectedCalendar.get(Calendar.HOUR_OF_DAY) + "\n";
        calendarString += "Actual Hour: " + actualCalendar.get(Calendar.HOUR_OF_DAY) + "\n";

        calendarString += "\n";

        calendarString += "Expected Minute: " + expectedCalendar.get(Calendar.MINUTE) + "\n";
        calendarString += "Actual Minute: " + actualCalendar.get(Calendar.MINUTE) + "\n";

        calendarString += "\n";

        //calendarString += "Date: " + calendar.get(Calendar.DATE) + "\n";

        return calendarString;
    }


    public Calendar getOffsetCalendar(Calendar originalCalendar, int lowerBound, int upperBound, int multiplier) {
        Calendar offsetCalendar = (Calendar) originalCalendar.clone();

        int randomDayOffset = randInt(lowerBound, upperBound);

        addDaysToCalendar(offsetCalendar, randomDayOffset*multiplier);

        return offsetCalendar;
    }

}
