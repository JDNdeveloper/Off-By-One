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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.MainActivity;
import com.jdndeveloper.lifereminders.R;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationActivity extends ActionBarActivity {

    private Notification passednotification;
    public int startingPoint;
    public static String[] test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setupActionBar();
        changeStatusBarColor(R.color.notif_action_status_bar);

        // Josh - below is how to retrieve the passed lifestyle
        passednotification = (Notification) getIntent().getSerializableExtra("Notification");
        Toast.makeText(this, passednotification.getName(), Toast.LENGTH_SHORT).show();
        //startingPoint = (int) getIntent().getSerializableExtra("startingPoint");



        TextView tv = (TextView) findViewById(R.id.specificNotificationData);
        if(passednotification.isRepeatDaysEnabled() == true && passednotification.isRepeatEveryBlankDaysEnabled() == false){
            Button changeDate = (Button) findViewById(R.id.changeDate);
            changeDate.setVisibility(View.INVISIBLE);
            Button repeatXDays = (Button) findViewById(R.id.repeatableXDays);
            repeatXDays.setVisibility(View.INVISIBLE);
            TextView textView = (TextView) findViewById(R.id.specificNotificationData);

            String[] Days = {"","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
            String text = "Days Alarm Enabled For:";
            ArrayList list = passednotification.getRepeatDays();
            //Log.e("Notification Activity",Integer.toString((int)list.get(1)));
            for(int i = 0; i < list.size(); ++i){
                text += "\n" + Days[(int)list.get(i)];
            }
            tv.setText(text);
        }else if(passednotification.isRepeatDaysEnabled() == false && passednotification.isRepeatEveryBlankDaysEnabled() == true){
            Button changeDate = (Button) findViewById(R.id.changeDate);
            changeDate.setVisibility(View.INVISIBLE);
            Button repeatEveryWeek = (Button) findViewById(R.id.repeatableEveryWeek);
            repeatEveryWeek.setVisibility(View.INVISIBLE);

            String text = "Alarm set to go off every " + Integer.toString(passednotification.getRepeatEveryBlankDays()) + " days\n";
            String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
            text += "Next Alarm Goes Off On: ";
            text += months[(passednotification.getTime().get(Calendar.MONTH))] + " ";
            text += Integer.toString(passednotification.getTime().get(Calendar.DAY_OF_MONTH));
            text += ", ";
            text += Integer.toString(passednotification.getTime().get(Calendar.YEAR));
            tv.setText(text);
        }else if(passednotification.isRepeatDaysEnabled() == false && passednotification.isRepeatEveryBlankDaysEnabled() == false){
            Button repeatEveryWeek = (Button) findViewById(R.id.repeatableEveryWeek);
            repeatEveryWeek.setVisibility(View.INVISIBLE);
            Button repeatXDays = (Button) findViewById(R.id.repeatableXDays);
            repeatXDays.setVisibility(View.INVISIBLE);
            String text = "";
            String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
            text += months[(passednotification.getTime().get(Calendar.MONTH))] + " ";
            text += Integer.toString(passednotification.getTime().get(Calendar.DAY_OF_MONTH));
            text += ", ";
            text += Integer.toString(passednotification.getTime().get(Calendar.YEAR));
            tv.setText(text);
            tv.setGravity(Gravity.CENTER);
        }else{
            //Do not remove
            Toast.makeText(getApplicationContext(),"The Notification Is Not Setup Properly",Toast.LENGTH_SHORT).show();
        }

    }

    public static class EveryXDaysFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle("Title");
            builder.setMessage("Message");

            // Set an EditText view to get user input
            final EditText input = new EditText(getActivity());
            builder.setView(input);

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString();
                    // Do something with value!
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

    public static class DaysOfWeekFragment extends DialogFragment{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final ArrayList mSelectedItems = new ArrayList();  // Where we track the selected items
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Set the dialog title
            builder.setTitle("pick toppings")
                    // Specify the list array, the items to be selected by default (null for none),
                    // and the listener through which to receive callbacks when items are selected
                    .setMultiChoiceItems(test, null,
                            new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which,
                                                    boolean isChecked) {
                                    if (isChecked) {
                                        // If the user checked the item, add it to the selected items
                                        mSelectedItems.add(which);
                                    } else if (mSelectedItems.contains(which)) {
                                        // Else, if the item is already in the array, remove it
                                        mSelectedItems.remove(Integer.valueOf(which));
                                    }
                                }
                            })
                            // Set the action buttons
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK, so save the mSelectedItems results somewhere
                            // or return them to the component that opened the dialog

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

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
        }
    }
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(),"time picker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
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
        }

        return super.onOptionsItemSelected(item);
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
