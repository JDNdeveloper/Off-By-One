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

    private final int NUMBER_OF_DAYS = 10000;
    private final int LOWER_OFFSET = -50;
    private final int UPPER_OFFSET = 50;

    private final Random random = new Random();

    private enum Procedure {SET_REPEAT_DAYS, SET_REPEAT_BLANK, SETUP_REPEAT_DAYS, SETUP_REPEAT_BLANK, MAKE_REPEAT_DAYS, MAKE_REPEAT_BLANK}

    private enum TestEnum {HELLO, HI, HOLA}

    @Test
    public void setAlarmRepeatDays() {
        AlarmManager alarmManager = new AlarmManager();
        Calendar rightNow = Calendar.getInstance();
        Calendar correctCalendar = Calendar.getInstance();
        ArrayList<Integer> repeatDays = new ArrayList<Integer>();

        runRepeatDaysSetup(alarmManager, rightNow, correctCalendar, repeatDays);

        simulateDays(NUMBER_OF_DAYS, Procedure.SET_REPEAT_DAYS, alarmManager, rightNow, correctCalendar);
    }

    @Test
    public void setAlarmRepeatEveryBlankDays() {
        AlarmManager alarmManager = new AlarmManager();
        Calendar rightNow = Calendar.getInstance();
        Calendar correctCalendar = Calendar.getInstance();

        runRepeatBlankSetup(alarmManager, rightNow, correctCalendar);

        simulateSevenDays(alarmManager, Procedure.SET_REPEAT_BLANK, rightNow, correctCalendar);
    }


    @Test
    //still needs to be implemented
    public void setupRepeatDays() {
        System.out.println(TAG + "testing makeNextRepeatDays()");

        Calendar rightNow = Calendar.getInstance();
        Calendar correctCalendar = Calendar.getInstance();

        AlarmManager alarmManager = new AlarmManager();


    }

    @Test
    public void setupRepeatEveryBlankDays() {
        AlarmManager alarmManager = new AlarmManager();
        Calendar rightNow = Calendar.getInstance();
        Calendar correctCalendar = Calendar.getInstance();

        runRepeatBlankSetup(alarmManager, rightNow, correctCalendar);

        simulateSevenDays(alarmManager, Procedure.SETUP_REPEAT_BLANK, rightNow, correctCalendar);
    }

    @Test
    public void makeNextRepeatDays() {
        AlarmManager alarmManager = new AlarmManager();
        Calendar rightNow = Calendar.getInstance();
        Calendar correctCalendar = Calendar.getInstance();
        ArrayList<Integer> repeatDays = new ArrayList<Integer>();

        runRepeatDaysSetup(alarmManager, rightNow, correctCalendar, repeatDays);

        simulateDays(NUMBER_OF_DAYS, Procedure.MAKE_REPEAT_DAYS, alarmManager, rightNow, correctCalendar);
    }

    @Test
    public void makeNextRepeatEveryBlankDays() {
        AlarmManager alarmManager = new AlarmManager();
        Calendar rightNow = Calendar.getInstance();
        Calendar correctCalendar = Calendar.getInstance();

        runRepeatBlankSetup(alarmManager, rightNow, correctCalendar);

        simulateSevenDays(alarmManager, Procedure.MAKE_REPEAT_BLANK, rightNow, correctCalendar);
    }

    public void runRepeatDaysSetup(AlarmManager alarmManager, Calendar rightNow, Calendar correctCalendar,
                                   ArrayList<Integer> repeatDays) {

        repeatDays = new ArrayList<Integer>(Arrays.asList(Calendar.MONDAY, Calendar.TUESDAY, Calendar.FRIDAY));

        alarmManager.setRepeatDays(repeatDays);
        alarmManager.setRepeatDaysEnabled(true);

        correctCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        alarmManager.setCalendar((Calendar) correctCalendar.clone());

        rightNow.setTimeInMillis(correctCalendar.getTimeInMillis());
    }

    public void runRepeatBlankSetup(AlarmManager alarmManager, Calendar rightNow, Calendar correctCalendar) {

        alarmManager.setRepeatEveryBlankDaysEnabled(true);
    }

    //START of Seven Day Simulation for Repeat Blank

    public void simulateSevenDays(AlarmManager alarmManager, Procedure correctProcedure, Calendar rightNow, Calendar correctCalendar) {

        for (int repeatInterval = 1; repeatInterval <= 7; repeatInterval++) {
            alarmManager.setRepeatEveryBlankDays(repeatInterval);

            correctCalendar = Calendar.getInstance();
            rightNow = Calendar.getInstance();
            alarmManager.setCalendar((Calendar) correctCalendar.clone());

            simulateDays(NUMBER_OF_DAYS, correctProcedure, alarmManager, rightNow, correctCalendar);
        }
    }

    //END of Seven Day Simulation for Repeat Blank


    //START of day simulation methods

    public boolean simulateDays(int numberOfDays, Procedure correctProcedure, AlarmManager alarmManager,
                                Calendar rightNow, Calendar correctCalendar) {

        //sets up the rightNow hour and minute
        rightNow.set(Calendar.HOUR_OF_DAY, 3);
        rightNow.set(Calendar.MINUTE, 20);

        Calendar randomDayOffsetCalendar = null;

        for (int day = 1; day <= numberOfDays; day++) {
            assertTrue("setupDay: " + day, setupDay(correctProcedure, alarmManager, randomDayOffsetCalendar)); //setup offset and other things

            assertTrue("runDay: " + day, runDay(correctProcedure, alarmManager, rightNow, correctCalendar)); //run procedure and check to see if it matches the correctCalendar

            assertTrue("finishDay: " + day, finishDay(correctProcedure, alarmManager, rightNow, correctCalendar, day)); //increment the day (rightNow) and also move correctCalendar to next correct day
        }

        return true;
    }

    //start of setup day code

    public boolean setupDay(Procedure correctProcedure, AlarmManager alarmManager, Calendar randomDayOffsetCalendar) {
        switch(correctProcedure) {
            case SET_REPEAT_DAYS:
                setupDayRepeatDays(alarmManager, randomDayOffsetCalendar);

                break;
            case SET_REPEAT_BLANK:
                setupDayRepeatBlank(alarmManager, randomDayOffsetCalendar);

                break;
            case SETUP_REPEAT_DAYS:
                //not yet implemented

                break;
            case SETUP_REPEAT_BLANK:
                setupDayRepeatBlank(alarmManager, randomDayOffsetCalendar);

                break;
            case MAKE_REPEAT_DAYS:
                //no setup required

                break;
            case MAKE_REPEAT_BLANK:
                //no setup required

                break;
            default:
                return false;
        }

        return true;
    }

    public void setupDayRepeatDays(AlarmManager alarmManager, Calendar randomDayOffsetCalendar) {
        randomDayOffsetCalendar = getOffsetCalendar(alarmManager.getCalendar(), LOWER_OFFSET, UPPER_OFFSET, 1);

        alarmManager.setCalendar(randomDayOffsetCalendar);
    }

    public void setupDayRepeatBlank(AlarmManager alarmManager, Calendar randomDayOffsetCalendar) {
        randomDayOffsetCalendar = getOffsetCalendar(alarmManager.getCalendar(), LOWER_OFFSET, UPPER_OFFSET, alarmManager.getRepeatEveryBlankDays());

        alarmManager.setCalendar(randomDayOffsetCalendar);
    }

    //end of setup day code

    public boolean runDay(Procedure correctProcedure, AlarmManager alarmManager, Calendar rightNow, Calendar correctCalendar) {
        runCorrectProcedure(correctProcedure, alarmManager, rightNow);

        switch (correctProcedure) {
            case SETUP_REPEAT_DAYS:
                //not yet implemented
                break;
            case SETUP_REPEAT_BLANK:
                assertTrue(correctProcedure.name() + "\n\n" + buildCalendarString(correctCalendar, alarmManager.getCalendar()),
                        correctCalendar.getTimeInMillis() > alarmManager.getCalendar().getTimeInMillis());
                break;
            default:
                assertEquals(correctProcedure.name() + "\n\n" + buildCalendarString(correctCalendar, alarmManager.getCalendar()),
                        correctCalendar, alarmManager.getCalendar());
        }

        return true;
    }

    //start of finish day code

    public boolean finishDay(Procedure correctProcedure, AlarmManager alarmManager, Calendar rightNow, Calendar correctCalendar, int day) {
        int currentDayOfWeek = rightNow.get(Calendar.DAY_OF_WEEK);

        switch (correctProcedure) {
            case SET_REPEAT_DAYS:
                finishDayRepeatDays(currentDayOfWeek, correctCalendar);

                break;
            case SET_REPEAT_BLANK:
                finishDayRepeatBlank(day, alarmManager, correctCalendar);

                break;
            case SETUP_REPEAT_DAYS:
                //not yet implemented

                break;
            case SETUP_REPEAT_BLANK:
                finishDayRepeatBlank(day, alarmManager, correctCalendar);

                break;
            case MAKE_REPEAT_DAYS:
                finishDayRepeatDays(currentDayOfWeek, correctCalendar);

                break;
            case MAKE_REPEAT_BLANK:
                finishDayRepeatBlank(day, alarmManager, correctCalendar);

                break;
        }

        addDaysToCalendar(rightNow, 1);

        return true;
    }

    public void finishDayRepeatDays(int currentDayOfWeek, Calendar correctCalendar) {
        if (currentDayOfWeek == Calendar.MONDAY || currentDayOfWeek == Calendar.TUESDAY || currentDayOfWeek == Calendar.FRIDAY)
            assertTrue(TAG + "set to incorrect date", setToProperDay(correctCalendar));
    }

    public void finishDayRepeatBlank(int day, AlarmManager alarmManager, Calendar correctCalendar) {
        //if the current day is when the alarm is supposed to go off, set correct day to the next proper day
        if ((day-1) % alarmManager.getRepeatEveryBlankDays() == 0) {
            addDaysToCalendar(correctCalendar, alarmManager.getRepeatEveryBlankDays());
        }
    }

    //end of finish day code

    public boolean runCorrectProcedure(Procedure correctProcedure, AlarmManager alarmManager, Calendar rightNow) {
        switch (correctProcedure) {
            case SET_REPEAT_DAYS:
                alarmManager.setAlarm(rightNow);
                break;
            case SET_REPEAT_BLANK:
                alarmManager.setAlarm(rightNow);
                break;
            case SETUP_REPEAT_DAYS:
                alarmManager.setupRepeatDays(rightNow);
                break;
            case SETUP_REPEAT_BLANK:
                alarmManager.setupRepeatEveryBlankDays(rightNow);
                break;
            case MAKE_REPEAT_DAYS:
                alarmManager.makeNextNotificationTime(rightNow);
                break;
            case MAKE_REPEAT_BLANK:
                alarmManager.makeNextNotificationTime(rightNow);
                break;
            default:
                return false;
        }

        return true;
    }

    //END of day simulation methods

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

    @Test
    public void enumTester() {
        TestEnum testEnum = TestEnum.HOLA;
        assertTrue("HOLA not called properly", switchEnumTest(testEnum) == 3);

        testEnum = TestEnum.HI;
        assertTrue("HI not called properly", switchEnumTest(testEnum) == 2);

        testEnum = testEnum.HELLO;
        assertTrue("HELLO not called properly", switchEnumTest(testEnum) == 1);
    }

    public int switchEnumTest(TestEnum properEnum) {
        switch(properEnum) {
            case HELLO:
                return 1;
            case HI:
                return 2;
            case HOLA:
                return 3;
            default:
                return -1;
        }
    }


    public int randInt(int min, int max) {

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = random.nextInt((max - min) + 1) + min;

        return randomNum;
    }

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


    public void TRASH() {
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
    }

}
