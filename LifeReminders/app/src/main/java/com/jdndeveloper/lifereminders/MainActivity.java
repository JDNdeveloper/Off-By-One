package com.jdndeveloper.lifereminders;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.support.v4.widget.DrawerLayout;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jdndeveloper.lifereminders.EventActivities.LifestyleActivity;
import com.jdndeveloper.lifereminders.EventActivities.NotificationActivity;
import com.jdndeveloper.lifereminders.EventActivities.ReminderActivity;
import com.jdndeveloper.lifereminders.EventTypes.AbstractBaseEvent;
import com.jdndeveloper.lifereminders.EventTypes.Action;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Option;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.Tests.GsonTester;
import com.jdndeveloper.lifereminders.Tests.NotificationStorageTester;
import com.jdndeveloper.lifereminders.Tests.NotificationTester;
import com.jdndeveloper.lifereminders.Tests.SprintPresentationTester;
import com.jdndeveloper.lifereminders.adapter.LifestyleAdapter;
import com.jdndeveloper.lifereminders.adapter.NotificationAdapter;
import com.jdndeveloper.lifereminders.adapter.ReminderAdapter;
import com.jdndeveloper.lifereminders.interfaces.StorageInterface;
import com.jdndeveloper.lifereminders.storage.SharedStorage;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /*Need to start an activity from a static context*/
    public static Context context;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    //Allow the activity to know what fragment it is on.
    public static int FragmentLocation = -1; //needs to be initialized or app crashes

    //private boolean reminderCalled = false;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    ImageButton buttonlistner;
    ImageButton settingslistner;
    ImageButton tempButtonIB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // we initialize shared preferences
        SharedStorage.initializeInstance(this);

        int flags = getIntent().getFlags();
        if ((flags & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0) {
            // The activity was launched from history
            // remove extras here so reminder isn't triggered
            Intent newIntent = getIntent();
            newIntent.removeExtra("Reminder");
            setIntent(newIntent);
        }

        if (FragmentLocation == -1)
            FragmentLocation = getProperFragmentLocation();

        Reminder notifReminder = (Reminder) getIntent().getSerializableExtra("Reminder");

        if (notifReminder != null) {
            FragmentLocation = 2; //if coming from a notification, go to the all reminders page
            //getIntent().removeExtra("Reminder");
            //setIntent(getIntent());
        }

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.selectItem(FragmentLocation-1);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        onNavigationDrawerItemSelected(FragmentLocation-1);

        //Needed to go to other Activities from a static context
        context = this.getApplicationContext();

        //code for the Sprint 1 presentation!
        SprintPresentationTester.runTest(this);

        buttonclick();
        settingsbuttonclick();
        // execute Gson test

        //GsonTester.test();

        //modifies tester notifications by running tests
//        NotificationStorageTester.runTest();

        //goes to correct reminder if launched from a notification
        if (notifReminder != null) {
            Log.i("MainActivity", notifReminder.getKey());
            //reminderCalled = true;
            if (!notifReminder.getKey().equals(Constants.REMINDER_FAILED_KEY)
                    && isValidReminder(notifReminder)) {
                loadReminder(notifReminder);
            }
        }

        NotificationTester.runTest(this); //runs Notification unit test

        // this iterates through all the lifestyles and deletes them all, with the exception of
        // the failure keys. feel free to un-comment. this shows delete functions properly.
        // you will need to clear cache after commenting it out and recompiling - john
//        StorageInterface storageInterface = Storage.getInstance();
//        List<Lifestyle> lifestyles = storageInterface.getAllLifestyles();
//        for (Lifestyle lifestyle : lifestyles)
//            if (storageInterface.deleteAbstractBaseEvent(lifestyle) ==  false)
//                Log.e("MainActivity", "onCreate delete lifestyle " + lifestyle.getKey() + " failed.");
    }

    public boolean isValidReminder(Reminder rem) {
        List<Reminder> allReminders = Storage.getInstance().getAllReminders();
        for (Reminder iteratorRem : allReminders) {
            if (iteratorRem.getKey().equals(rem.getKey()))
                return true;
        }
        return false;
    }

    public int getProperFragmentLocation() {
        Option option1 = Storage.getInstance().getOption(Constants.OPTION_TEST_KEY1);
        Option option2 = Storage.getInstance().getOption(Constants.OPTION_TEST_KEY2);
        Option option3 = Storage.getInstance().getOption(Constants.OPTION_TEST_KEY3);


        int properFragmentLocation = 1;

        if (option1.isEnabled()) properFragmentLocation = 1;
        else if (option2.isEnabled()) properFragmentLocation = 2;
        else if (option3.isEnabled()) properFragmentLocation = 3;

        return properFragmentLocation;
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

    public void buttonclick() {
        buttonlistner = (ImageButton) findViewById(R.id.imageplusbutton);

        buttonlistner.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonclickplus(v);
            }
        });
    }

    private void loadLifestyle(Lifestyle lifestyle) {
        FragmentLocation = 1;
        Intent lifestyleIntent = new Intent(context, LifestyleActivity.class);
        lifestyleIntent.putExtra("Lifestyle", lifestyle);
        startActivity(lifestyleIntent);
    }

    private void loadReminder(Reminder reminder) {
        FragmentLocation = 2;
        Intent reminderIntent = new Intent(context, ReminderActivity.class);
        reminderIntent.putExtra("Reminder", reminder);
        startActivity(reminderIntent);
    }

    public void buttonclickplus(View v) {
        Log.e("MainActivity", Integer.toString(FragmentLocation));

        switch (FragmentLocation) {
            //Go To Lifestyle Activity

            case 1:
                Log.e("MainActivity","newLifestyle");

                Lifestyle lifestyle = Storage.getInstance().getNewLifeStyle();
                lifestyle.setName("");
                Storage.getInstance().commitAbstractBaseEvent(lifestyle);
                loadLifestyle(lifestyle);
                break;
            //Go To Reminder Activity
            case 2:
                Log.e("MainActivity","newReminder");

                Reminder reminder = Storage.getInstance().getNewReminder();
                reminder.setName("");
                Storage.getInstance().commitAbstractBaseEvent(reminder);
                loadReminder(reminder);
                break;
            //Go To Notification Activity
            case 3:
                Log.e("MainActivity", "newNotification");
                //FragmentLocation = 3; redundant
                createNewNotification(v);
                break;
            default:
                Log.e("MainActivity","newFailed");
                break;
        }
    }
    public void createNewNotification(View v){
        registerForContextMenu(v);
        openContextMenu(v);
        unregisterForContextMenu(v);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        Log.e("MainActivity", "Context Menu");
        menu.setHeaderTitle("Select the type of new Notification");
        menu.add(0, v.getId(),0,"One Time Alarm");
        menu.add(0, v.getId(),1,"Weekly Alarm");
        menu.add(0, v.getId(),2,"Repeatable Every X Days");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.e("MainActivity", "Context Item Selected");
        if (item.getGroupId() == 0) {
            typeOfNotification = item.getOrder();
            Log.e("MainActivity", "Before Time Picker Here");
            DialogFragment newFragment = new TimePickerFragment();
            newFragment.show(getFragmentManager(),"time picker");
            Log.e("MainActivity", "After Time Picker Here");
            return true;
        }
        return false;
    }

    public void settingsbuttonclick() {
        settingslistner = (ImageButton) findViewById(R.id.settings);

        settingslistner.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonclicksettings(v);
            }
        });
    }

    public void buttonclicksettings(View v) {
        //Toast.makeText(MainActivity.this,
        //        "Settings is clicked!", Toast.LENGTH_LONG).show();

        //ALEX! Uncomment below code to link settings button to open your settings activity

        Intent settingsIntent = new Intent(context, SettingsActivity.class);
        startActivity(settingsIntent);

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();

        ActionBar bar = getSupportActionBar();

        //sets Action Bar and Nav Bar background color based on what is selected in nav bar
        if (position == 0) {
            bar.setBackgroundDrawable(new ColorDrawable(
                    getResources().getColor(R.color.life_action_background)));
            //Change status bar color
            changeStatusBarColor(R.color.life_action_status_bar);
            //navBarLayout.setBackgroundColor(
            //        getResources().getColor(R.color.life_action_background));
        } else if (position == 1) {
            bar.setBackgroundDrawable(new ColorDrawable(
                    getResources().getColor(R.color.rem_action_background)));
            //Change status bar color
            changeStatusBarColor(R.color.rem_action_status_bar);
            //navBarLayout.setBackgroundColor(
            //        getResources().getColor(R.color.rem_action_background));
        } else if (position == 2) {
            bar.setBackgroundDrawable(new ColorDrawable(
                    getResources().getColor(R.color.notif_action_background)));
            //Change status bar color
            changeStatusBarColor(R.color.notif_action_status_bar);
            //navBarLayout.setBackgroundColor(
            //        getResources().getColor(R.color.notif_action_background));
            //End of background setting
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = "All Lifestyles";
                break;
            case 2:
                mTitle = "All Reminders";
                break;
            case 3:
                mTitle = "All Notifications";
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();

        Intent newIntent = getIntent();
        newIntent.removeExtra("Reminder");
        setIntent(newIntent);
        //FragmentLocation = -1; does very bad things!!
    }
    public static List<? extends AbstractBaseEvent> abstractBaseEvents;
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {}

        public static ListView adapterListView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            final ListView listView = (ListView) rootView.findViewById(R.id.listView);
            adapterListView = listView;

            //final List<? extends AbstractBaseEvent> abstractBaseEvents;

            switch (getArguments().getInt(ARG_SECTION_NUMBER, -1)){
                case 1:
                    abstractBaseEvents = Storage.getInstance().getAllLifestyles();
                    FragmentLocation = 1;
                    listView.setAdapter(new LifestyleAdapter(getActivity(),
                            android.R.layout.simple_list_item_activated_1,
                            R.layout.lifestyle_row, abstractBaseEvents
                    ));
                    rootView.setBackgroundColor(getResources().getColor(R.color.life_background));
                    break;
                case 2:
                    abstractBaseEvents = Storage.getInstance().getAllReminders();
                    FragmentLocation = 2;
                    listView.setAdapter(new ReminderAdapter(getActivity(),
                            android.R.layout.simple_list_item_activated_1,
                            R.layout.reminder_row, abstractBaseEvents
                    ));
                    rootView.setBackgroundColor(getResources().getColor(R.color.rem_background));
                    break;
                case 3:
                    abstractBaseEvents = Storage.getInstance().getAllNotifications();
                    FragmentLocation = 3;
                    listView.setAdapter(new NotificationAdapter(getActivity(),
                            android.R.layout.simple_list_item_activated_1,
                            R.layout.notification_row, abstractBaseEvents
                    ));
                    rootView.setBackgroundColor(getResources().getColor(R.color.notif_background));
                    break;
                default:
                    return rootView;
            }

            //http://www.colourlovers.com/palette/3643955/Cleaning_In_Sydney
/*            if (abstractBaseEvents.get(0) instanceof Lifestyle) {
                listView.setAdapter(new LifestyleAdapter(getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        R.layout.lifestyle_row, abstractBaseEvents
                ));
                rootView.setBackgroundColor(getResources().getColor(R.color.life_background));
            }
            if (abstractBaseEvents.get(0) instanceof Reminder){
                listView.setAdapter(new ReminderAdapter(getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        R.layout.reminder_row, abstractBaseEvents
                ));
                rootView.setBackgroundColor(getResources().getColor(R.color.rem_background));
            }

            if (abstractBaseEvents.get(0) instanceof Notification){
                listView.setAdapter(new NotificationAdapter(getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        R.layout.notification_row, abstractBaseEvents
                ));
                rootView.setBackgroundColor(getResources().getColor(R.color.notif_background));
            }
*/
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("MainActivity", "Selecting item "
                            + ((AbstractBaseEvent) abstractBaseEvents.get(position)).getKey());
                    Gson gsonObject = new Gson();
                    switch (getArguments().getInt(ARG_SECTION_NUMBER, -1)) {
                        //Go To Lifestyle Activity
                        case 1:
                            Intent lifestyleIntent = new Intent(context, LifestyleActivity.class);
                            Lifestyle lifestyle = (Lifestyle) abstractBaseEvents.get(position);

                            //Printout GSON string of the lifestyle
                            String lifestyleGson = gsonObject.toJson(lifestyle);
                            Log.e("GSONCollector", "Lifestyle GSON: [" + lifestyleGson + "]");

                            lifestyleIntent.putExtra("Lifestyle", lifestyle);
                            startActivity(lifestyleIntent);
                            break;
                        //Go To Reminder Activity
                        case 2:
                            Intent reminderIntent = new Intent(context, ReminderActivity.class);
                            Reminder reminder = (Reminder) abstractBaseEvents.get(position);

                            //Printout GSON string of the reminder
                            String reminderGson = gsonObject.toJson(reminder);
                            Log.e("GSONCollector", "Reminder GSON: [" + reminderGson + "]");

                            reminderIntent.putExtra("Reminder", reminder);
                            startActivity(reminderIntent);
                            break;
                        //Go To Notification Activity
                        case 3:
                            Intent notificationIntent = new Intent(context, NotificationActivity.class);
                            Notification notification = (Notification) abstractBaseEvents.get(position);

                            //Printout GSON string of the reminder
                            String notificationGson = gsonObject.toJson(notification);
                            Log.e("GSONCollector", "Notification GSON: [" + notificationGson + "]");

                            //Printout GSON string of the action
                            Action action = Storage.getInstance().getAction(notification.getActionKey());
                            String actionGson = gsonObject.toJson(action);
                            Log.e("GSONCollector", "Action GSON: [" + actionGson + "]");

                            notificationIntent.putExtra("Notification", notification);
                            startActivity(notificationIntent);
                            break;
                        default:
                            Toast.makeText(context,"Not a Proper Type of Abstract BAse Event",Toast.LENGTH_SHORT);
                            break;
                    }
                }
            });


            //long press to delete code
            final Context c = getActivity();

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                    final AbstractBaseEvent abe = (AbstractBaseEvent) parent.getAdapter().getItem(position);

                    String tempType = "";
                    String tempName = "";
                    if (abe instanceof Lifestyle) {
                        tempType = "Lifestyle";
                        tempName = abe.getName();
                    }
                    if (abe instanceof Reminder) {
                        tempType = "Reminder";
                        tempName = abe.getName();
                    }
                    if (abe instanceof Notification) {
                        tempType = "Notification";
                        tempName = " this notification";
                    }
                    final String type = tempType;
                    final String name = tempName;
                    new AlertDialog.Builder(c)
                            .setTitle("Delete " + type)
                            .setMessage("Are you sure you want to delete " + name + "?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    boolean stat = Storage.getInstance().deleteAbstractBaseEvent(abe);
                                    Log.e("MainActivity", "Deletion: " + stat);
                                    Log.e("MainActivity", "Being Deleted: " + abe.getKey());
                                    if (abe instanceof Reminder) {
                                        Log.e("MainActivity", "Reminder's parent lifestyle: "
                                                + ((Reminder) abe).getLifestyleContainerKey());
                                    } else if (abe instanceof Notification) {
                                        Log.e("MainActivity", "Notification's parent lifestyle: "
                                                + ((Notification) abe).getReminderContainerKey());
                                    } else {
                                        Log.e("MainActivity", "Lifestyle has not container");
                                    }
//                                    //if (stat) {
//                                        reloadAdapter(listView, rootView);
//                                    //}
// THIS IS TEMPORARILY COMMENTED OUT SO THAT EVERYONE CAN SEE THAT IT WORKS. HOWEVER, IT SHOULD NOT BE BECAUSE
// DELETE CHECKS PARENT CONTAINERS EXISTENCE AND WILL - DOES HERE - FAIL TO DELETE THE ITEM. YOU NEED TO MAKE
// SURE THE PARENTS OF OBJECTS CREATED ARE LINKED. FX, WHEN YOU CREATE A REMINDER, IT NEEDS TO BE ASSOCIATED WITH
// A LIFESTYLE. OTHERWISE, DELETE WILL FAIL AND NOT DELETE THE REMINDER - JOHN
                                    if (stat) {
                                        // remove item from list that backs array adapter
                                        //abstractBaseEvents.remove(position);
                                        // tell the array adapter to reload
                                        //((ArrayAdapter) parent.getAdapter()).notifyDataSetChanged();
                                        MainActivity.refreshAdapter(getActivity());
                                    }
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .show();

                    return true; //not very descriptive but best I could do currently
                }
            });

            return rootView;
        }

        /*
        private void refreshAdapter() {
            ListView theView = PlaceholderFragment.adapterListView;
            if (theView == null) return;
            switch (FragmentLocation){
                case 1:
                    abstractBaseEvents = Storage.getInstance().getAllLifestyles();
                    theView.setAdapter(new LifestyleAdapter(getActivity(),
                            android.R.layout.simple_list_item_activated_1,
                            R.layout.lifestyle_row, abstractBaseEvents
                    ));
                    break;
                case 2:
                    abstractBaseEvents = Storage.getInstance().getAllReminders();
                    theView.setAdapter(new ReminderAdapter(getActivity(),
                            android.R.layout.simple_list_item_activated_1,
                            R.layout.reminder_row, abstractBaseEvents
                    ));
                    break;
                case 3:
                    abstractBaseEvents = Storage.getInstance().getAllNotifications();
                    theView.setAdapter(new NotificationAdapter(getActivity(),
                            android.R.layout.simple_list_item_activated_1,
                            R.layout.notification_row, abstractBaseEvents
                    ));
                    break;
                default:
                    return;
            }
            ((ArrayAdapter) PlaceholderFragment.adapterListView.getAdapter()).notifyDataSetChanged();
        }*/

// Not needed and breaks app - john
/*        private void reloadAdapter(ListView listView, View rootView) {
            List<? extends AbstractBaseEvent> abstractBaseEvents = null;
            switch (getArguments().getInt(ARG_SECTION_NUMBER, -1)){
                case 1:
                    abstractBaseEvents = Storage.getInstance().getAllLifestyles();
                    FragmentLocation = 1;
                    break;
                case 2:
                    abstractBaseEvents = Storage.getInstance().getAllReminders();
                    FragmentLocation = 2;
                    break;
                case 3:
                    abstractBaseEvents = Storage.getInstance().getAllNotifications();
                    FragmentLocation = 3;
                    break;
                default:
                    return;
            }

            if (abstractBaseEvents.get(0) instanceof Lifestyle) {
                listView.setAdapter(new LifestyleAdapter(getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        R.layout.lifestyle_row, abstractBaseEvents
                ));
                rootView.setBackgroundColor(getResources().getColor(R.color.life_background));
            }
            if (abstractBaseEvents.get(0) instanceof Reminder){
                listView.setAdapter(new ReminderAdapter(getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        R.layout.reminder_row, abstractBaseEvents
                ));
                rootView.setBackgroundColor(getResources().getColor(R.color.rem_background));
            }

            if (abstractBaseEvents.get(0) instanceof Notification){
                listView.setAdapter(new NotificationAdapter(getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        R.layout.notification_row, abstractBaseEvents
                ));
                rootView.setBackgroundColor(getResources().getColor(R.color.notif_background));
            }
        }
*/
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

// Not needed and breaks app - john
/*    public void reloadAdapter(){
        final ListView listView = (ListView) findViewById(R.id.listView);
        //List<? extends AbstractBaseEvent> abstractBaseEvents = null;
        Log.e("MainActivity","ReloadAdapter");
        switch (FragmentLocation){
            case 1:
                abstractBaseEvents = Storage.getInstance().getAllLifestyles();
                break;
            case 2:
                abstractBaseEvents = Storage.getInstance().getAllReminders();
                break;
            case 3:
                abstractBaseEvents = Storage.getInstance().getAllNotifications();
                break;
            default:
                return;
        }

        if (abstractBaseEvents.get(0) instanceof Lifestyle) {
            listView.setAdapter(new LifestyleAdapter(this,
                    android.R.layout.simple_list_item_activated_1,
                    R.layout.lifestyle_row, abstractBaseEvents
            ));
        }
        if (abstractBaseEvents.get(0) instanceof Reminder){
            listView.setAdapter(new ReminderAdapter(this,
                    android.R.layout.simple_list_item_activated_1,
                    R.layout.reminder_row, abstractBaseEvents
            ));
        }

        if (abstractBaseEvents.get(0) instanceof Notification){
            listView.setAdapter(new NotificationAdapter(this,
                    android.R.layout.simple_list_item_activated_1,
                    R.layout.notification_row, abstractBaseEvents
            ));
        }
    }
*/
    @Override
    public void onResume(){
        super.onResume();
        Log.e("Main Activity","onResume");
        VisibilityManager.setIsVisible(true);

        //setContentView(R.layout.activity_main);


        //refreshes the adapter after an event is edited
        refreshAdapter(this);

    }

    //don't delete this John!! It doesn't break the app and it is necessary
    public static  void refreshAdapter(Activity activity) {
        ListView theView = PlaceholderFragment.adapterListView;
        if (theView == null) return;
        switch (FragmentLocation){
            case 1:
                abstractBaseEvents = Storage.getInstance().getAllLifestyles();
                theView.setAdapter(new LifestyleAdapter(activity,
                        android.R.layout.simple_list_item_activated_1,
                        R.layout.lifestyle_row, abstractBaseEvents
                ));
                break;
            case 2:
                abstractBaseEvents = Storage.getInstance().getAllReminders();
                theView.setAdapter(new ReminderAdapter(activity,
                        android.R.layout.simple_list_item_activated_1,
                        R.layout.reminder_row, abstractBaseEvents
                ));
                break;
            case 3:
                abstractBaseEvents = Storage.getInstance().getAllNotifications();
                theView.setAdapter(new NotificationAdapter(activity,
                        android.R.layout.simple_list_item_activated_1,
                        R.layout.notification_row, abstractBaseEvents
                ));
                break;
            default:
                return;
        }
        ((ArrayAdapter) PlaceholderFragment.adapterListView.getAdapter()).notifyDataSetChanged();
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
            //callcount = 0; Josh, should this be here? - jayden
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);


            Log.e("Main Activity","OnCreateDialog Time");
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (!view.isShown()) return;

            // Do something with the time chosen by the user
            Log.e("Main Activity", "onTimeSet Outside");
            if(callcount == 0) {
                callcount++;
                newMinute = minute;
                newHour = hourOfDay;

                switch (typeOfNotification) {
                    case 0:
                        Log.e("Main Activity", "onTimeSet Inside");
                        DialogFragment newFragment2 = new DatePickerFragment();
                        newFragment2.show(getFragmentManager(), "Date Picker");
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
            if (!view.isShown()) return;

            // Do something with the date chosen by the user
            newYear = year;
            newMonth = month;
            newDay = day;

            Notification notification = Storage.getInstance().getNewNotification();
            Calendar c = Calendar.getInstance();

            notification.setRepeatDaysEnabled(false);
            notification.setRepeatEveryBlankDaysEnabled(false);
            c.set(newYear, newMonth, newDay, newHour, newMinute);

            notification.setTime(c);

            finishCreatingNewNotification(notification, getActivity());
        }
    }

    /*Selecting Days of the week*/
    public static class DaysOfWeekFragment extends DialogFragment{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            callcount = 0;
            final boolean[] validDays = new boolean[7];
            for(int i = 0; i < validDays.length;i++){
                validDays[i] =false;
            }
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
                            finishCreatingNewNotification(notification, getActivity());
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
                        notification.setTime(c);
                        finishCreatingNewNotification(notification, getActivity());

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

            return builder.create();
        }

    }

    public static void finishCreatingNewNotification(Notification notification, Activity activity){
        callcount = 0;
        Action action = Storage.getInstance().getNewAction();
        notification.setActionKey(action.getKey());
        notification.setAlarm(context);
        if(!Storage.getInstance().commitAbstractBaseEvent(action) || !Storage.getInstance().commitAbstractBaseEvent(notification)){
            Toast.makeText(context,"Failed To Properly Save Notification",Toast.LENGTH_SHORT);
            //return;
        }
        Intent notificationIntent = new Intent(context, NotificationActivity.class);
        //notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationIntent.putExtra("Notification", notification);
        activity.startActivity(notificationIntent);
    }

    private static boolean isInFront;

    @Override
    public void onPause() {
        super.onPause();
        VisibilityManager.setIsVisible(false);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        Log.e("MainActivity", "Got to onKeyDown");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        } else {
            return super.onKeyDown(keyCode, event);
        }
        return true;
    }


}
