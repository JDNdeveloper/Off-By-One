package com.jdndeveloper.lifereminders;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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




public class SettingsActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_style);
        setupActionBar();
        changeStatusBarColor(R.color.life_action_status_bar);




        //Create listener for name change







        ListView listView = (ListView) findViewById(R.id.lifestyleListView);
        final List<Option> reminderArray = new ArrayList<>();
        //abstractBaseEvents = passedLifestyle.getReminders();
        //Storage.getInstance().getReminder()
        for(int i = 0; i<5; i++){
            Log.e("SettingsActivity","Adding setting option ");
            reminderArray.add(new Option());
        }
        listView.setAdapter(new SettingsAdapter(this,
                android.R.layout.simple_list_item_2,
                R.layout.reminder_row, reminderArray
        ));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("SettingsActivity", "position " + Integer.toString(position));
                Log.e("SettingsActivity","newActivity Reminder");
                //Intent reminderIntent = new Intent(getApplicationContext(), ReminderActivity.class);
               // Reminder reminder = reminderArray.get(position);
               // reminderIntent.putExtra("Reminder", reminder);
               // startActivity(reminderIntent);
            }
        });

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
        actionBar.setTitle("Lifestyle");
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
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.life_action_background)));
        changeStatusBarColor(R.color.life_action_status_bar);
    }
}