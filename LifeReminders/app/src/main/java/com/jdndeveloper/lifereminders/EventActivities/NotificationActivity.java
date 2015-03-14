package com.jdndeveloper.lifereminders.EventActivities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventTypes.Action;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.MainActivity;
import com.jdndeveloper.lifereminders.R;
import com.jdndeveloper.lifereminders.VisibilityManager;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationActivity extends ActionBarActivity {

    private static Notification passednotification;
    //public Notification passednotification0;
    public int startingPoint;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setupActionBar();
        changeStatusBarColor(R.color.notif_action_status_bar);
        context = getApplicationContext();

        //MainActivity.FragmentLocation = 3;

        passednotification = (Notification) getIntent().getSerializableExtra("Notification");

        //grabs the most recent version of the notification from storage
        passednotification = Storage.getInstance().getNotification(passednotification.getKey());

        Log.i("NotificationActivity", "Passed Notification: " + passednotification.getKey());
        Log.i("NotificationActivity", "Passed Notification: " + passednotification.getActionKey());

        TextView tv = (TextView) findViewById(R.id.specificNotificationData);
        if (passednotification.isRepeatDaysEnabled() == true && passednotification.isRepeatEveryBlankDaysEnabled() == false) {
            Button changeDate = (Button) findViewById(R.id.changeDate);
            changeDate.setVisibility(View.INVISIBLE);
            Button repeatXDays = (Button) findViewById(R.id.repeatableXDays);
            repeatXDays.setVisibility(View.INVISIBLE);
            Button changeDateEveryXDays = (Button) findViewById(R.id.changeDateEveryXDays);
            changeDateEveryXDays.setVisibility(View.INVISIBLE);

            String[] Days = {"", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
            String text = "Days Alarm Enabled For:";
            ArrayList list = passednotification.getRepeatDays();
            for (int i = 0; i < list.size(); ++i) {
                text += "\n" + Days[(int) list.get(i)];
            }
            tv.setText(text);
        } else if (passednotification.isRepeatDaysEnabled() == false && passednotification.isRepeatEveryBlankDaysEnabled() == true) {
            Button changeDate = (Button) findViewById(R.id.changeDate);
            changeDate.setVisibility(View.INVISIBLE);
            Button repeatEveryWeek = (Button) findViewById(R.id.repeatableEveryWeek);
            repeatEveryWeek.setVisibility(View.INVISIBLE);

            String text = "Alarm set to go off every " + Integer.toString(passednotification.getRepeatEveryBlankDays()) + " days\n";
            String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            text += "Next Alarm Goes Off On: ";
            text += months[(passednotification.getTime().get(Calendar.MONTH))] + " ";
            text += Integer.toString(passednotification.getTime().get(Calendar.DAY_OF_MONTH));
            text += ", ";
            text += Integer.toString(passednotification.getTime().get(Calendar.YEAR));
            tv.setText(text);
        } else if (passednotification.isRepeatDaysEnabled() == false && passednotification.isRepeatEveryBlankDaysEnabled() == false) {
            Button repeatEveryWeek = (Button) findViewById(R.id.repeatableEveryWeek);
            repeatEveryWeek.setVisibility(View.INVISIBLE);
            Button repeatXDays = (Button) findViewById(R.id.repeatableXDays);
            repeatXDays.setVisibility(View.INVISIBLE);
            Button changeDateEveryXDays = (Button) findViewById(R.id.changeDateEveryXDays);
            changeDateEveryXDays.setVisibility(View.INVISIBLE);
            String text = "";
            String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            text += months[(passednotification.getTime().get(Calendar.MONTH))] + " ";
            text += Integer.toString(passednotification.getTime().get(Calendar.DAY_OF_MONTH));
            text += ", ";
            text += Integer.toString(passednotification.getTime().get(Calendar.YEAR));
            tv.setText(text);
            tv.setGravity(Gravity.CENTER);
        } else {
            //Do not remove
            Toast.makeText(getApplicationContext(), "The Notification Is Not Setup Properly", Toast.LENGTH_SHORT).show();
        }


        changeTime();


        //Create listener for enables/disabled switch
        Switch theSwitch = (Switch) findViewById(R.id.notificationEnabled);
        theSwitch.setOnCheckedChangeListener(null);
        theSwitch.setChecked(passednotification.isEnabled());

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            theSwitch.setElevation(100);
        }

        if(Storage.getInstance().getLifestyle(passednotification.getLifestyleContainerKey()).isEnabled() &&
                Storage.getInstance().getReminder(passednotification.getReminderContainerKey()).isEnabled() ){
            if (android.os.Build.VERSION.SDK_INT >= 16) {
                theSwitch.setThumbResource(R.drawable.switch_thumb);
                theSwitch.refreshDrawableState();
            }
        }else {
            if (android.os.Build.VERSION.SDK_INT >= 16) {
                theSwitch.setThumbResource(R.drawable.apptheme_switch_thumb_disabled_holo_light);
                theSwitch.refreshDrawableState();
            }
        }

        theSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                passednotification.setEnabled(!passednotification.isEnabled());
                Storage.getInstance().replaceAbstractBaseEvent(passednotification);
            }
        });

        Action a = Storage.getInstance().getAction(passednotification.getActionKey());

        ToggleButton vibration = (ToggleButton) findViewById(R.id.vibrate);
        vibration.setChecked(a.isVibrate());

        ToggleButton sound = (ToggleButton) findViewById(R.id.sound);
        sound.setChecked(a.isNotificationSound());

        //passednotification.setAlarm(context);
        //Storage.getInstance().replaceAbstractBaseEvent(passednotification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //MainActivity.FragmentLocation = 3;
    }

    public void changeVibrate(View v){
        Log.e("NotificationActivity","Action Key: " + passednotification.getActionKey());

        Action a = Storage.getInstance().getAction(passednotification.getActionKey());
        a.setVibrate(!a.isVibrate());
        if (!Storage.getInstance().replaceAbstractBaseEvent(a)) Log.e("Notification Activity","Save Action Failed");

    }

    public void changeSound(View v){
        Log.e("NotificationActivity","Action Key: " + passednotification.getActionKey());

        Action a = Storage.getInstance().getAction(passednotification.getActionKey());
        a.setNotificationSound(!a.isNotificationSound());
        if (!Storage.getInstance().replaceAbstractBaseEvent(a)) Log.e("Notification Activity","Save Action Failed");
    }

        public void changeTime() {
            TextView timeText = (TextView) findViewById(R.id.notificationTime);
            Calendar time = passednotification.getTime();

            String amPM = "amPM FAIL";

            if (time.get(Calendar.AM_PM) == 1) {
                amPM = "PM";
            } else {
                amPM = "AM";
            }

            String minutes = Integer.toString(time.get(Calendar.MINUTE));
            if (minutes.length() == 1) {
                minutes = "0" + minutes;
            }

            String hour = Integer.toString(time.get(Calendar.HOUR));
            if (hour.equals("0")) {
                hour = "12";
            }

            timeText.setText(hour
                    + ":" + minutes
                    + " " + amPM);
        }

    @Override
    public Intent getSupportParentActivityIntent(){
        switch(startingPoint){
            case 0:
            case 1:
                Intent returnReminder = new Intent(getApplicationContext(), ReminderActivity.class);
                returnReminder.putExtra("Reminder",Storage.getInstance().getReminder(passednotification.getReminderContainerKey()));
                returnReminder.putExtra("startingPoint",startingPoint);
                return returnReminder;
            case 2:
                Intent returnMain = new Intent(getApplicationContext(), MainActivity.class);
                returnMain.putExtra("startingPoint",startingPoint);
                return returnMain;


        }
        return super.getSupportParentActivityIntent();
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = passednotification.getTime().get(Calendar.HOUR_OF_DAY);
            int minute = passednotification.getTime().get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (!view.isShown()) return;

            // Do something with the time chosen by the user
            TextView timeText = (TextView) getActivity().findViewById(R.id.notificationTime);
            Calendar time = passednotification.getTime();
            time.set(Calendar.HOUR_OF_DAY,hourOfDay);
            time.set(Calendar.MINUTE,minute);

            //passednotification.removeAlarm(context);
            passednotification.setTime(time);
            //Storage.getInstance().replaceAbstractBaseEvent(passednotification);
            passednotification.setAlarm(context);
            Storage.getInstance().replaceAbstractBaseEvent(passednotification);
            String amPM = "amPM FAIL";

            if (time.get(Calendar.AM_PM) == 1) {
                amPM = "PM";
            } else {
                amPM = "AM";
            }

            String minutes = Integer.toString(time.get(Calendar.MINUTE));
            if (minutes.length() == 1) {
                minutes = "0" + minutes;
            }

            String hour = Integer.toString(time.get(Calendar.HOUR));

            if (hour.equals("0")) {
                hour = "12";
            }

            timeText.setText(hour
                    + ":" + minutes
                    + " " + amPM);

            TextView tv = (TextView) getActivity().findViewById(R.id.specificNotificationData);
            if (passednotification.isRepeatDaysEnabled() == true && passednotification.isRepeatEveryBlankDaysEnabled() == false) {
                String[] Days = {"", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
                String text = "Days Alarm Enabled For:";
                ArrayList list = passednotification.getRepeatDays();
                for (int i = 0; i < list.size(); ++i) {
                    text += "\n" + Days[(int) list.get(i)];
                }
                tv.setText(text);
            } else if (passednotification.isRepeatDaysEnabled() == false && passednotification.isRepeatEveryBlankDaysEnabled() == true) {
                String text = "Alarm set to go off every " + Integer.toString(passednotification.getRepeatEveryBlankDays()) + " days\n";
                String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                text += "Next Alarm Goes Off On: ";
                text += months[(passednotification.getTime().get(Calendar.MONTH))] + " ";
                text += Integer.toString(passednotification.getTime().get(Calendar.DAY_OF_MONTH));
                text += ", ";
                text += Integer.toString(passednotification.getTime().get(Calendar.YEAR));
                tv.setText(text);
            } else if (passednotification.isRepeatDaysEnabled() == false && passednotification.isRepeatEveryBlankDaysEnabled() == false) {
                String text = "";
                String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                text += months[(passednotification.getTime().get(Calendar.MONTH))] + " ";
                text += Integer.toString(passednotification.getTime().get(Calendar.DAY_OF_MONTH));
                text += ", ";
                text += Integer.toString(passednotification.getTime().get(Calendar.YEAR));
                tv.setText(text);
                tv.setGravity(Gravity.CENTER);
            }

        }
    }
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(),"time picker");
    }

    /*Selecting How often it reoccurs*/
    public static class EveryXDaysFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle("Select Interval");
            builder.setMessage("This Notification Will Repeat Every _ Days");

            // Set an EditText view to get user input
            final EditText input = new EditText(getActivity());
            builder.setView(input);

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString();
                    try {
                        int intDays = Integer.parseInt(value);
                        if (intDays <= 0) {
                            //This Toast will stay in final product
                            Toast.makeText(context,"Invalid Number",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        passednotification.setRepeatEveryBlankDays(intDays);
                        Storage.getInstance().replaceAbstractBaseEvent(passednotification);
                        String text = "Alarm set to go off every " + Integer.toString(passednotification.getRepeatEveryBlankDays()) + " days\n";
                        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                        text += "Next Alarm Goes Off On: ";
                        text += months[(passednotification.getTime().get(Calendar.MONTH))] + " ";
                        text += Integer.toString(passednotification.getTime().get(Calendar.DAY_OF_MONTH));
                        text += ", ";
                        text += Integer.toString(passednotification.getTime().get(Calendar.YEAR));
                        TextView tv = (TextView) getActivity().findViewById(R.id.specificNotificationData);
                        tv.setText(text);

                        TextView timeText = (TextView) getActivity().findViewById(R.id.notificationTime);
                        Calendar time = passednotification.getTime();
                        String amPM = "amPM FAIL";

                        if (time.get(Calendar.AM_PM) == 1) {
                            amPM = "PM";
                        } else {
                            amPM = "AM";
                        }

                        String minutes = Integer.toString(time.get(Calendar.MINUTE));
                        if (minutes.length() == 1) {
                            minutes = "0" + minutes;
                        }

                        timeText.setText(Integer.toString(time.get(Calendar.HOUR))
                                + ":" + minutes
                                + " " + amPM);
                    }catch(NumberFormatException e){
                        //This Toast will stay in final product
                        Toast.makeText(context,"Not a Number",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });

            //alert.show();
            return builder.create();
        }

    }

    public void setRepeatableEveryXDays(View v){
        DialogFragment newFragment = new EveryXDaysFragment();
        newFragment.show(getFragmentManager(), "Every X Days");
    }


    /*Selecting Days of the week*/
    public static class DaysOfWeekFragment extends DialogFragment{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final boolean[] validDays = new boolean[7];
            for(int i = 0; i < validDays.length;i++){
                validDays[i] =false;
            }
            String[] Days = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

            final boolean[] preValidDays = new boolean[7];
            ArrayList<Integer> repeatDays = passednotification.getRepeatDays();
            for(int i = 0; i < repeatDays.size(); i++){
                preValidDays[repeatDays.get(i).intValue() - 1] = true;
                validDays[repeatDays.get(i).intValue() - 1] = true;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Set the dialog title
            builder.setTitle("Pick Days of the Week")
                    // Specify the list array, the items to be selected by default (null for none),
                    // and the listener through which to receive callbacks when items are selected
                    .setMultiChoiceItems(Days, preValidDays,
                            new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which,
                                                    boolean isChecked) {
                                    if (isChecked) {
                                        // If the user checked the item, add it to the selected items
                                        validDays[which] = true;
                                    } else {
                                        // Else, if the item is already in the array, remove it
                                        validDays[which] = false;
                                    }
                                }
                            })
                            // Set the action buttons
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK, so save the mSelectedItems results somewhere
                            // or return them to the component that opened the dialog


                            for(int i = 0; i < validDays.length;i++){
                                passednotification.setRepeatDay(i+1, false);
                            }
                            for (int i = 0; i < validDays.length;i++) {
                                if(validDays[i]) passednotification.setRepeatDay(i+1, true);
                            }
                            Storage.getInstance().replaceAbstractBaseEvent(passednotification);
                            String[] Days = {"", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
                            String text = "Days Alarm Enabled For:";
                            ArrayList list = passednotification.getRepeatDays();
                            //Log.e("Notification Activity",Integer.toString((int)list.get(1)));
                            for (int i = 0; i < list.size(); ++i) {
                                text += "\n" + Days[(int) list.get(i)];
                            }
                            TextView tv = (TextView) getActivity().findViewById(R.id.specificNotificationData);
                            tv.setText(text);

                            passednotification.setAlarm(context);
                            TextView timeText = (TextView) getActivity().findViewById(R.id.notificationTime);
                            Calendar time = passednotification.getTime();
                            String amPM = "amPM FAIL";

                            if (time.get(Calendar.AM_PM) == 1) {
                                amPM = "PM";
                            } else {
                                amPM = "AM";
                            }

                            String minutes = Integer.toString(time.get(Calendar.MINUTE));
                            if (minutes.length() == 1) {
                                minutes = "0" + minutes;
                            }

                            timeText.setText(Integer.toString(time.get(Calendar.HOUR))
                                    + ":" + minutes
                                    + " " + amPM);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //
                        }
                    });

            return builder.create();
        }
    }

    public void setRepeatableDays(View v){
        DialogFragment newFragment = new DaysOfWeekFragment();
        newFragment.show(getFragmentManager(), "Days Of Week");
    }


    @Override
    public void onPause() {
        super.onPause();
        VisibilityManager.setIsVisible(false);
        VisibilityManager.setIsNotificationActivityVisible(false);
    }

    @Override
    public void onResume(){
        super.onResume();
        VisibilityManager.setIsVisible(true);
        VisibilityManager.setIsNotificationActivityVisible(true);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = passednotification.getTime();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            if (!view.isShown()) return;

            // Do something with the date chosen by the user
            Calendar date = passednotification.getTime();
            date.set(Calendar.YEAR,year);
            date.set(Calendar.MONTH,month);
            date.set(Calendar.DAY_OF_MONTH,day);
            passednotification.setTime(date);


            Storage.getInstance().replaceAbstractBaseEvent(passednotification);
            passednotification.setAlarm(context);
            if(oneTime) {
                String text = "";
                String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                text += months[(passednotification.getTime().get(Calendar.MONTH))] + " ";
                text += Integer.toString(passednotification.getTime().get(Calendar.DAY_OF_MONTH));
                text += ", ";
                text += Integer.toString(passednotification.getTime().get(Calendar.YEAR));
                TextView tv = (TextView) getActivity().findViewById(R.id.specificNotificationData);
                tv.setText(text);
                tv.setGravity(Gravity.CENTER);
            }else{
                String text = "Alarm set to go off every " + Integer.toString(passednotification.getRepeatEveryBlankDays()) + " days\n";
                String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                text += "Next Alarm Goes Off On: ";
                text += months[(passednotification.getTime().get(Calendar.MONTH))] + " ";
                text += Integer.toString(passednotification.getTime().get(Calendar.DAY_OF_MONTH));
                text += ", ";
                text += Integer.toString(passednotification.getTime().get(Calendar.YEAR));
                TextView tv = (TextView) getActivity().findViewById(R.id.specificNotificationData);
                tv.setText(text);
            }
            
            TextView timeText = (TextView) getActivity().findViewById(R.id.notificationTime);
            Calendar time = passednotification.getTime();
            String amPM = "amPM FAIL";

            if (time.get(Calendar.AM_PM) == 1) {
                amPM = "PM";
            } else {
                amPM = "AM";
            }

            String minutes = Integer.toString(time.get(Calendar.MINUTE));
            if (minutes.length() == 1) {
                minutes = "0" + minutes;
            }

            timeText.setText(Integer.toString(time.get(Calendar.HOUR))
                    + ":" + minutes
                    + " " + amPM);
        }
    }

    public static boolean oneTime;

    public void showDatePickerDialog(View v) {
        oneTime = true;
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void changeDateEveryXDays(View v) {
        oneTime = false;
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_life_style, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //This needs to be changed
        switch (id) {
            case android.R.id.home:
                finish();
                if (Constants.ANIMATION_ENABLED)
                    overridePendingTransition(R.anim.finish_enter_animation, R.anim.finish_exit_animation);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        Log.e("NotificationActivity", "Got to onKeyDown");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            if (Constants.ANIMATION_ENABLED)
                overridePendingTransition(R.anim.finish_enter_animation, R.anim.finish_exit_animation);
        } else {
            return super.onKeyDown(keyCode, event);
        }
        return true;
    }

    //Changes status bar color if using API 21 or above
    public void changeStatusBarColor(int colorID) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(colorID));
        }
    }

    //sets up correct action bar
    public void setupActionBar() {
        //Creates ActionBar object
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Notification");
        //Places logo in top right of ActionBar
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageButton buttonView = new ImageButton(actionBar.getThemedContext());
        buttonView.setScaleType(ImageView.ScaleType.CENTER);
        buttonView.setAdjustViewBounds(true);
        buttonView.setMaxHeight(150); //scales the logo
        buttonView.setImageResource(R.drawable.app_logo);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 10;
        buttonView.setLayoutParams(layoutParams);
        buttonView.setBackgroundColor(Color.TRANSPARENT);
        actionBar.setCustomView(buttonView);
        //End of placing logo

        final Context c = this;

        //Long click on logo opens about page
        buttonView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(c);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.about);

                dialog.show();
                return true;
            }
        });

        //Sets initial action bar background to Lifestyle Action Bar Background
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.notif_action_background)));
        changeStatusBarColor(R.color.notif_action_background);
    }

}
