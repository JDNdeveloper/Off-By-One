package com.jdndeveloper.lifereminders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdndeveloper.lifereminders.EventActivities.LifestyleActivity;
import com.jdndeveloper.lifereminders.EventTypes.AbstractBaseEvent;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.Tests.GsonTester;
import com.jdndeveloper.lifereminders.adapter.LifestyleAdapter;
import com.jdndeveloper.lifereminders.adapter.NotificationAdapter;
import com.jdndeveloper.lifereminders.adapter.ReminderAdapter;
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

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    Button buttonlistner;

    ImageButton tempButtonIB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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

        //what we're going to do is schedule an alarm to go off in 1 minute, and push a notification
        Notification testNotif = Storage.getInstance().getNotification(Constants.NOTIFICATION_TEST_KEY);
        //Log.e("MainActivity", "onCreate name [" + testNotif.getName() + "]");
        //Log.e("MainActivity", "onCreate key [" + testNotif.getKey() + "]");
        testNotif.setLifestyleContainerKey(Constants.LIFESTYLE_TEST_KEY);
        //Log.e("MainActivity", "onCreate lifestyle container key [" + testNotif.getLifestyleContainerKey() + "]");
        testNotif.setReminderContainerKey(Constants.REMINDER_TEST_KEY);
        //Log.e("MainActivity", "onCreate reminder container key [" + testNotif.getReminderContainerKey() + "]");
        testNotif.setActionKey(Constants.ACTION_TEST_KEY);
        //Log.e("MainActivity", "onCreate name [" + testNotif.getActionKey() + "]");

        Calendar cal = Calendar.getInstance();

        //cal.add(Calendar.MINUTE, 1);
        cal.add(Calendar.SECOND, 10);
        //cal.set(Calendar.SECOND, 0);


        testNotif.setTime(cal);
        testNotif.setAlarm(this);

        //End of Sprint 1 Presentation Plan

        buttonclick();
        // execute Gson test

        GsonTester.test();

    }

    public void buttonclick() {
        buttonlistner = (Button) findViewById(R.id.tempbuttonadd);

        buttonlistner.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.v("blah", "blah blah");
                Toast.makeText(MainActivity.this,
                        "ImageButton is clicked!", Toast.LENGTH_LONG).show();
            }
        });
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
                    break;
                case 2:
                    abstractBaseEvents = Storage.getInstance().getAllReminders();
                    break;
                case 3:
                    abstractBaseEvents = Storage.getInstance().getAllNotifications();
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
                            Intent myIntent = new Intent(context, LifestyleActivity.class);
                            startActivity(myIntent);
                            break;
                        //Go To Reminder Activity
                        case 2:
                            break;
                        //Go To Notification Activity
                        case 3:
                            break;
                        default:
                            break;
                    }
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
