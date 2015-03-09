package com.jdndeveloper.lifereminders;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jdndeveloper.lifereminders.EventActivities.ReminderActivity;
import com.jdndeveloper.lifereminders.EventTypes.AbstractBaseEvent;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Option;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.R;

import com.jdndeveloper.lifereminders.R;
import com.jdndeveloper.lifereminders.adapter.ReminderAdapter;
import com.jdndeveloper.lifereminders.adapter.SettingsAdapter;
import com.jdndeveloper.lifereminders.interfaces.StorageInterface;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;


public class SettingsActivity extends ActionBarActivity {

    //private static View rootView ;
    private ListView settingsListView;
    //private static  View rootView;

    //LayoutInflater inflater,ViewGroup container ,
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupActionBar();

        //rootView = (View) findViewById(R.id.settingsView);
        //final ListView listView = (ListView) rootView.findViewById(R.id.listView);
        settingsListView = (ListView) findViewById(R.id.settingsListView);

        reloadAdapter(this);

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

        switch (id) {
            case android.R.id.home:
                finish();
                return(true);
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
        actionBar.setTitle("Settings");
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
                Log.e("Lifestyle Activity","return to previous activity");
                dialog.show();
                return true;
            }
        });

        //Sets initial action bar background to Lifestyle Action Bar Background
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.settings_action_background)));
        changeStatusBarColor(R.color.settings_action_status_bar);
    }


    public void reloadAdapter(Activity activity) {
        //no getAllOptions() method



        List<Option> options = new ArrayList<Option>();

        //options.add(Storage.getInstance().getOption(Constants.OPTION_TEST_KEY0));
        options.add(Storage.getInstance().getOption(Constants.OPTION_TEST_KEY1));
        options.add(Storage.getInstance().getOption(Constants.OPTION_TEST_KEY2));
        options.add(Storage.getInstance().getOption(Constants.OPTION_TEST_KEY3));
        options.add(Storage.getInstance().getOption(Constants.OPTION_TEST_KEY4));


        options.get(0).addRelative(Constants.OPTION_TEST_KEY2);
        options.get(0).addRelative(Constants.OPTION_TEST_KEY3);

        options.get(1).addRelative(Constants.OPTION_TEST_KEY1);
        options.get(1).addRelative(Constants.OPTION_TEST_KEY3);

        options.get(2).addRelative(Constants.OPTION_TEST_KEY1);
        options.get(2).addRelative(Constants.OPTION_TEST_KEY2);

        //complains about getActivity(),
        settingsListView.setAdapter(new SettingsAdapter(activity,
                android.R.layout.simple_list_item_activated_1,
                R.layout.settings_row, options
        ));




    }





}



