package com.jdndeveloper.lifereminders.EventActivities;

import android.app.Activity;
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
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventTypes.AbstractBaseEvent;
import com.jdndeveloper.lifereminders.EventTypes.Action;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.MainActivity;
import com.jdndeveloper.lifereminders.R;
import com.jdndeveloper.lifereminders.adapter.NotificationAdapter;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReminderActivity extends ActionBarActivity {

    private static Reminder passedReminder;
    ImageButton buttonlistner;
    public int startingPoint;
    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        setupActionBar();
        changeStatusBarColor(R.color.rem_action_status_bar);

        // Josh - below is how to retrieve the passed lifestyle
        passedReminder = (Reminder) getIntent().getSerializableExtra("Reminder");
        //Toast.makeText(this, passedReminder.getName(), Toast.LENGTH_SHORT).show();
        Log.i("ReminderActivity", "Passed Reminder: " + passedReminder.getName());
        //startingPoint = (int) getIntent().getSerializableExtra("startingPoint");
        context = getApplicationContext();
        //Create listener for name change
        final EditText editText = (EditText) findViewById(R.id.reminderName);
        editText.setText(passedReminder.getName());
        editText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                //editText.setText(String.valueOf(i) + " / " + String.valueOf(charCounts));
                Log.e("Reminder Activity", "Editing reminder name to: " + editText.getText().toString());
                passedReminder.setName(editText.getText().toString());
                Storage.getInstance().replaceAbstractBaseEvent(passedReminder);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });


        //Create listener for enables/disabled switch
        Switch theSwitch = (Switch) findViewById(R.id.reminderEnabled);
        theSwitch.setOnCheckedChangeListener(null);
        theSwitch.setChecked(passedReminder.isEnabled());

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            theSwitch.setElevation(100);
        }




        theSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                passedReminder.setEnabled(!passedReminder.isEnabled());
                Storage.getInstance().replaceAbstractBaseEvent(passedReminder);
                updateListAdapter();
            }
        });

        updateListAdapter();
        buttonclick();

    }
    final Context c = this;
    public void updateListAdapter(){
        final ListView listView = (ListView) findViewById(R.id.reminderListView);
        final List<Notification> notificationArray = new ArrayList<>();
        //abstractBaseEvents = passedLifestyle.getReminders();
        //Storage.getInstance().getReminder()
        for(String r : passedReminder.getNotificationKeys()){
            Log.e("Reminder Activity",r);
            if(!Storage.getInstance().getNotification(r).getKey().equals(Constants.NOTIFICATION_FAILED_KEY)) notificationArray.add(Storage.getInstance().getNotification(r));
        }
        listView.setAdapter(new NotificationAdapter(this,
                android.R.layout.simple_list_item_2,
                R.layout.notification_row, notificationArray
        ));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ReminderActivity", "position " + Integer.toString(position));
                Log.e("ReminderActivity","newActivity Notification");

                Intent notificationIntent = new Intent(getApplicationContext(), NotificationActivity.class);
                Notification notification = notificationArray.get(position);
                Log.e("ReminderActivity","Vibrate: " + Boolean.toString(Storage.getInstance().getAction(notification.getActionKey()).isVibrate()) );
                notificationIntent.putExtra("Notification", notification);
                notificationIntent.putExtra("startingPoint",startingPoint);
                startActivity(notificationIntent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final AbstractBaseEvent abe = (AbstractBaseEvent) parent.getAdapter().getItem(position);



                String type = "Notification";
                String name = abe.getName();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
                alertDialogBuilder.setTitle("Delete " + type);
                alertDialogBuilder.setMessage("Are you sure you want to delete " + name + "?");
                alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        boolean stat = Storage.getInstance().deleteAbstractBaseEvent(abe);
                        Log.e("MainActivity", "Deletion: " + stat);
                        if (stat) {
                            updateListAdapter();
                        }
                    }
                });
                alertDialogBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                });
                alertDialogBuilder.show();

                return true;
            }
        });
    }

    public void buttonclick() {
        buttonlistner = (ImageButton) findViewById(R.id.imageplusbutton);

        buttonlistner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonclickplus(v);
            }
        });
    }


    public void buttonclickplus(View v) {
        //do action
        createNewNotification(v);;
    }

    public void createNewNotification(View v){
        registerForContextMenu(v);
        openContextMenu(v);
        unregisterForContextMenu(v);
    }

    @Override
    public void onResume(){
        super.onResume();
        updateListAdapter();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select the type of new Notification");
        menu.add(0, v.getId(),0,"One Time Alarm");
        menu.add(0, v.getId(),1,"Weekly Alarm");
        menu.add(0, v.getId(),2,"Repeatable Every X Days");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getGroupId() == 0) {
            typeOfNotification = item.getOrder();
            DialogFragment newFragment = new TimePickerFragment();
            newFragment.show(getFragmentManager(),"time picker");
            return true;
        }
        return false;
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
        /*int id = item.getItemId();

        //This needs to be changed
        switch (id) {
            case android.R.id.home:
                finish();
        }*/

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
        actionBar.setTitle("Reminder");
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
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.rem_action_background)));
        changeStatusBarColor(R.color.rem_action_status_bar);
    }





    /*This is for adding a new notification*/
    public static int newMinute;
    public static int newHour;
    public static int newDay;
    public static int newMonth;
    public static int newYear;
    public static int typeOfNotification;
    public static int callcount = 0;

    /*Selecting Time*/
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
            if(callcount == 0) {
                callcount++;
                newMinute = minute;
                newHour = hourOfDay;
                switch (typeOfNotification) {
                    case 0:
                        DialogFragment newFragment = new DatePickerFragment();
                        newFragment.show(getFragmentManager(), "Date Picker");
                        break;
                    case 1:
                        DialogFragment newFragment0 = new DaysOfWeekFragment();
                        newFragment0.show(getFragmentManager(), "Days of the Week Picker");
                        break;
                    case 2:
                        DialogFragment newFragment1 = new EveryXDaysFragment();
                        newFragment1.show(getFragmentManager(), "Every X Days Picker");
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /*Selecting Date*/
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            callcount = 0;
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            newYear = year;
            newMonth = month;
            newDay = day;

            Notification notification = Storage.getInstance().getNewNotification();
            Calendar c = Calendar.getInstance();

            notification.setRepeatDaysEnabled(false);
            notification.setRepeatEveryBlankDaysEnabled(false);
            Log.e("ReminderActivity",Integer.toString(newYear));
            Log.e("ReminderActivity",Integer.toString(newMonth));
            Log.e("ReminderActivity",Integer.toString(newDay));
            Log.e("ReminderActivity",Integer.toString(newHour));
            Log.e("ReminderActivity",Integer.toString(newMinute));
            c.set(newYear, newMonth, newDay, newHour, newMinute,0);

            notification.setTime(c);

            notification.setReminderContainerKey(passedReminder.getKey());
            notification.setLifestyleContainerKey(passedReminder.getLifestyleContainerKey());
            passedReminder.addNotification(notification.getKey());
            Action action = Storage.getInstance().getNewAction();
            notification.setActionKey(action.getKey());
            Storage.getInstance().commitAbstractBaseEvent(action);
            Storage.getInstance().commitAbstractBaseEvent(notification);
            Storage.getInstance().replaceAbstractBaseEvent(passedReminder);
            notification.setAlarm(context);
            Intent notificationIntent = new Intent(context, NotificationActivity.class);
            notificationIntent.putExtra("Notification", notification);

            startActivity(notificationIntent);
        }
    }

    /*@Override
    public Intent getSupportParentActivityIntent(){
        switch(startingPoint){
            case 0:
                Intent returnLifestyle = new Intent(getApplicationContext(), LifestyleActivity.class);
                returnLifestyle.putExtra("Lifestyle",Storage.getInstance().getLifestyle(passedReminder.getLifestyleContainerKey()));
                returnLifestyle.putExtra("startingPoint",startingPoint);
                return returnLifestyle;
            case 1:
                Intent returnMain = new Intent(getApplicationContext(), MainActivity.class);
                returnMain.putExtra("startingPoint",startingPoint);
                return returnMain;

        }
        return super.getSupportParentActivityIntent();
    }*?

    /*Selecting Days of the week*/
    public static class DaysOfWeekFragment extends DialogFragment{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            callcount = 0;
            final ArrayList mSelectedItems = new ArrayList();  // Where we track the selected items
            final boolean[] validDays = new boolean[7];
            for(int i = 0; i < validDays.length;i++){
                validDays[i] =false;
            }
            final int[] vDays = new int[7];
            String[] Days = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Set the dialog title
            builder.setTitle("Pick Days of the Week")
                    // Specify the list array, the items to be selected by default (null for none),
                    // and the listener through which to receive callbacks when items are selected
                    .setMultiChoiceItems(Days, null,
                            new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which,
                                                    boolean isChecked) {
                                    if (isChecked) {
                                        // If the user checked the item, add it to the selected items
                                        mSelectedItems.add(which);
                                        validDays[which] = true;
                                        vDays[which ] = 1;

                                    } else if (mSelectedItems.contains(which)) {
                                        // Else, if the item is already in the array, remove it
                                        mSelectedItems.remove(Integer.valueOf(which));
                                        validDays[which] = false;
                                        vDays[which ] = 0;
                                    }
                                }
                            })
                            // Set the action buttons
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK, so save the mSelectedItems results somewhere
                            // or return them to the component that opened the dialog
                            Notification notification = Storage.getInstance().getNewNotification();
                            Calendar c = Calendar.getInstance();
                            notification.setRepeatDaysEnabled(true);
                            notification.setRepeatEveryBlankDaysEnabled(false);
                            c.set(Calendar.HOUR_OF_DAY, newHour);
                            c.set(Calendar.MINUTE, newMinute);
                            c.set(Calendar.SECOND,0);
                            for (int i = 0; i < validDays.length;i++) {
                                if(validDays[i]) notification.setRepeatDay(i+1, true);
                            }


                            notification.setTime(c);

                            notification.setReminderContainerKey(passedReminder.getKey());
                            notification.setLifestyleContainerKey(passedReminder.getLifestyleContainerKey());
                            passedReminder.addNotification(notification.getKey());
                            Action action = Storage.getInstance().getNewAction();
                            notification.setActionKey(action.getKey());
                            Storage.getInstance().commitAbstractBaseEvent(action);
                            Storage.getInstance().commitAbstractBaseEvent(notification);
                            Storage.getInstance().replaceAbstractBaseEvent(passedReminder);

                            Intent notificationIntent = new Intent(context, NotificationActivity.class);
                            notificationIntent.putExtra("Notification", notification);

                            notification.setAlarm(context);
                            startActivity(notificationIntent);
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

    /*Selecting How often it reoccurs*/
    public static class EveryXDaysFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            callcount = 0;
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

                        Notification notification = Storage.getInstance().getNewNotification();


                        Calendar c = Calendar.getInstance();
                        notification.setRepeatDaysEnabled(false);
                        notification.setRepeatEveryBlankDaysEnabled(true);

                        notification.setRepeatEveryBlankDays(intDays);


                        c.set(Calendar.HOUR_OF_DAY, newHour);
                        c.set(Calendar.MINUTE, newMinute);
                        c.set(Calendar.SECOND,0);


                        notification.setTime(c);
                        notification.setAlarm(context);
                        notification.setName("");
                        notification.setReminderContainerKey(passedReminder.getKey());
                        notification.setLifestyleContainerKey(passedReminder.getLifestyleContainerKey());
                        passedReminder.addNotification(notification.getKey());

                        Action action = Storage.getInstance().getNewAction();
                        notification.setActionKey(action.getKey());
                        Storage.getInstance().commitAbstractBaseEvent(action);


                        Storage.getInstance().commitAbstractBaseEvent(notification);
                        Storage.getInstance().replaceAbstractBaseEvent(passedReminder);

                        Intent notificationIntent = new Intent(context, NotificationActivity.class);
                        notificationIntent.putExtra("Notification", notification);



                        startActivity(notificationIntent);
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
}
