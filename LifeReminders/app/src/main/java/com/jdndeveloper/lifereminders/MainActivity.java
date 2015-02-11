package com.jdndeveloper.lifereminders;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jdndeveloper.lifereminders.EventTypes.AbstractBaseEvent;
import com.jdndeveloper.lifereminders.EventTypes.Action;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.adapter.LifestyleAdapter;
import com.jdndeveloper.lifereminders.adapter.NotificationAdapter;
import com.jdndeveloper.lifereminders.adapter.ReminderAdapter;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

////////////////////////////////////////////////////////////////////////////////////////////////////
//        http://mvnrepository.com/artifact/com.google.code.gson/gson
//        http://stackoverflow.com/questions/16608135/android-studio-add-jar-as-library
//        https://www.youtube.com/watch?v=1MyBO9z7ojk
//        http://stackoverflow.com/questions/16608135/android-studio-add-jar-as-library
////////////////////////////////////////////////////////////////////////////////////////////////////

        Log.e("MainActivity", "onCreate json test begin");

        Lifestyle lifestyle = new Lifestyle();
        Lifestyle lifestyle2 = Storage.getInstance().getNewLifeStyle();

        Reminder reminder = new Reminder();
        Reminder reminder2 = Storage.getInstance().getNewReminder();

        Notification notification = new Notification();
        Notification notification2 = Storage.getInstance().getNewNotification();

        Action action = new Action();
        Action action2 = Storage.getInstance().getNewAction();

        Gson gsonObject = new Gson();

        Log.e("MainActivity", "onCreate gson test lifestyle [" + gsonObject.toJson(lifestyle) + "]");
        Log.e("MainActivity", "onCreate gson test lifestyle2 [" + gsonObject.toJson(lifestyle2) + "]");

        Log.e("MainActivity", "onCreate gson test reminder [" + gsonObject.toJson(reminder) + "]");
        Log.e("MainActivity", "onCreate gson test reminder2 [" + gsonObject.toJson(reminder2) + "]");

        Log.e("MainActivity", "onCreate gson test notification [" + gsonObject.toJson(notification) + "]");
        Log.e("MainActivity", "onCreate gson test notification2 [" + gsonObject.toJson(notification2) + "]");

        Log.e("MainActivity", "onCreate gson test action [" + gsonObject.toJson(action) + "]");
        Log.e("MainActivity", "onCreate gson test action2 [" + gsonObject.toJson(action2) + "]");

        Log.e("MainActivity", "onCreate json test end");

////////////////////////////////////////////////////////////////////////////////////////////////////

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

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
                mTitle = "Lifestyles";
                break;
            case 2:
                mTitle = "Reminders";
                break;
            case 3:
                mTitle = "Notifications";
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
