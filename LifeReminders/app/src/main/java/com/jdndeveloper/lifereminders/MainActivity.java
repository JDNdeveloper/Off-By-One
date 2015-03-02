package com.jdndeveloper.lifereminders;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.util.Log;
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
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdndeveloper.lifereminders.EventActivities.LifestyleActivity;
import com.jdndeveloper.lifereminders.EventActivities.NotificationActivity;
import com.jdndeveloper.lifereminders.EventActivities.ReminderActivity;
import com.jdndeveloper.lifereminders.EventTypes.AbstractBaseEvent;
import com.jdndeveloper.lifereminders.EventTypes.Action;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
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
    public static int FragmentLocation;

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

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        //Needed to go to other Activities from a static context
        context = this.getApplicationContext();

        //code for the Sprint 1 presentation!
        SprintPresentationTester.runTest(this);

        buttonclick();
        settingsbuttonclick();
        // execute Gson test

        //GsonTester.test();

        //modifies tester notifications by running tests
        NotificationStorageTester.runTest();

        //goes to correct reminder if launched from a notification
        Reminder notifReminder = (Reminder) getIntent().getSerializableExtra("Reminder");
        if (notifReminder != null) {
            Log.i("MainActivity", notifReminder.getKey());
            loadReminder(notifReminder);
        }


        /*this is Josh- DO NOT DELETE unless I give you explicit permission to do so*/
        /*try {
            FragmentLocation = (int) getIntent().getSerializableExtra("startingPoint");
            onNavigationDrawerItemSelected(FragmentLocation);
            Log.e("Main Activity","Fragment Location " + Integer.toString(FragmentLocation));
            switch (FragmentLocation){
                case 0:
                    changeStatusBarColor(R.color.life_action_status_bar);
                    break;
                case 1:
                    changeStatusBarColor(R.color.rem_action_status_bar);
                    break;
                case 2:
                    changeStatusBarColor(R.color.notif_action_status_bar);
                    break;
                default:
                    break;
            }
            restoreActionBar();
        }catch (NullPointerException e){
            Log.e("Main Activity","not returning from other activity");
        }*/





        // test delete for action
        //StorageInterface storageInterface = Storage.getInstance();
        //Action action = storageInterface.getNewAction();
        //Log.e("MainActivity","onCreate commit new action - " + storageInterface.commitAbstractBaseEvent(action));
        //Log.e("MainActivity","onCreate delete new action - " + storageInterface.deleteAbstractBaseEvent(action));
        //Log.e("MainActivity","onCreate delete new action - " + storageInterface.deleteAbstractBaseEvent(action));
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

    private void loadNotification(Notification notification) {
        FragmentLocation = 3;
        Intent notificationIntent = new Intent(context, NotificationActivity.class);
        notificationIntent.putExtra("Notification", notification);
        startActivity(notificationIntent);
    }

    public void buttonclickplus(View v) {
        switch (FragmentLocation) {
            //Go To Lifestyle Activity
            case 1:
                Log.e("Main Activity","newLifestyle");

                Lifestyle lifestyle = Storage.getInstance().getNewLifeStyle();
                lifestyle.setName("");
                Storage.getInstance().commitAbstractBaseEvent(lifestyle);
                loadLifestyle(lifestyle);
                break;
            //Go To Reminder Activity
            case 2:
                Log.e("Main Activity","newReminder");

                Reminder reminder = Storage.getInstance().getNewReminder();
                reminder.setName("");
                Storage.getInstance().commitAbstractBaseEvent(reminder);
                loadReminder(reminder);
                break;
            //Go To Notification Activity
            case 3:
                Log.e("Main Activity", "newNotification");

                Notification notification = Storage.getInstance().getNewNotification();
                notification.setName("");
                Action action = Storage.getInstance().getNewAction();
                notification.setActionKey(action.getKey());
                Storage.getInstance().commitAbstractBaseEvent(notification);
                Storage.getInstance().commitAbstractBaseEvent(action);
                loadNotification(notification);
                break;
            default:
                Log.e("Main Activity","newFailed");
                break;
        }
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        FragmentLocation = 1;
    }

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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ListView listView = (ListView) rootView.findViewById(R.id.listView);

            final List<? extends AbstractBaseEvent> abstractBaseEvents;

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
                    return rootView;
            }
            //http://www.colourlovers.com/palette/3643955/Cleaning_In_Sydney
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

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("MainActivity", "onCreateView onItem "
                            + ((AbstractBaseEvent) abstractBaseEvents.get(position)).getKey());
                    switch (getArguments().getInt(ARG_SECTION_NUMBER, -1)) {
                        //Go To Lifestyle Activity
                        case 1:
                            Log.e("Main Activity","newActivity Lifestyle");
                            Intent lifestyleIntent = new Intent(context, LifestyleActivity.class);
                            Lifestyle lifestyle = (Lifestyle) abstractBaseEvents.get(position);
                            lifestyleIntent.putExtra("Lifestyle", lifestyle);
                            lifestyleIntent.putExtra("distanceFromRoot",0);
                            lifestyleIntent.putExtra("startingPoint",0);
                            startActivity(lifestyleIntent);
                            break;
                        //Go To Reminder Activity
                        case 2:
                            Log.e("Main Activity","newActivity Reminder");
                            Intent reminderIntent = new Intent(context, ReminderActivity.class);
                            Reminder reminder = (Reminder) abstractBaseEvents.get(position);
                            reminderIntent.putExtra("Reminder", reminder);
                            reminderIntent.putExtra("distanceFromRoot",0);
                            reminderIntent.putExtra("startingPoint",1);
                            startActivity(reminderIntent);
                            break;
                        //Go To Notification Activity
                        case 3:
                            Log.e("Main Activity","newActivity Notification");
                            Intent notificationIntent = new Intent(context, NotificationActivity.class);
                            Notification notification = (Notification) abstractBaseEvents.get(position);
                            notificationIntent.putExtra("Notification", notification);
                            notificationIntent.putExtra("distanceFromRoot",0);
                            notificationIntent.putExtra("startingPoint",2);
                            startActivity(notificationIntent);
                            break;
                        default:
                            Log.e("Main Activity","newActivity Failed");
                            break;
                    }
                }
            });


            //long press to delete code
            final Context c = getActivity();

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
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
                                    Storage.getInstance().deleteAbstractBaseEvent(abe);
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

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
