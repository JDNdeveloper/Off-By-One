package com.jdndeveloper.lifereminders.EventActivities;

import android.app.Dialog;
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
import android.widget.Switch;
import android.widget.Toast;

import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.MainActivity;
import com.jdndeveloper.lifereminders.R;
import com.jdndeveloper.lifereminders.adapter.NotificationAdapter;
import com.jdndeveloper.lifereminders.adapter.ReminderAdapter;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class ReminderActivity extends ActionBarActivity {

    private Reminder passedReminder;
    ImageButton buttonlistner;
    public int distanceFromRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        setupActionBar();
        changeStatusBarColor(R.color.rem_action_status_bar);

        // Josh - below is how to retrieve the passed lifestyle
        passedReminder = (Reminder) getIntent().getSerializableExtra("Reminder");
        Toast.makeText(this, passedReminder.getName(), Toast.LENGTH_SHORT).show();
        distanceFromRoot = (int) getIntent().getSerializableExtra("distanceFromRoot");

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
                //change enabled state of Lifestyle
                /*if (isChecked) {
                    //text += " is enabled";
                    passedReminder.setEnabled(true);
                    Storage.getInstance().replaceAbstractBaseEvent(passedReminder);
                } else {
                    //text +=AQ " is disabled";
                    passedReminder.setEnabled(false);
                    Storage.getInstance().replaceAbstractBaseEvent(passedReminder);
                }*/
            }
        });

        updateListAdapter();
        buttonclick();
    }

    public void updateListAdapter(){
        final ListView listView = (ListView) findViewById(R.id.reminderListView);
        final List<Notification> notificationArray = new ArrayList<>();
        //abstractBaseEvents = passedLifestyle.getReminders();
        //Storage.getInstance().getReminder()
        for(String r : passedReminder.getNotificationKeys()){
            Log.e("Reminder Activity",r);
            notificationArray.add(Storage.getInstance().getNotification(r));
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
                notificationIntent.putExtra("Notification", notification);
                notificationIntent.putExtra("distanceFromRoot",distanceFromRoot+1);
                startActivity(notificationIntent);
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
        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
        Log.e("Notification Activity","newNotification");
        Intent notificationIntent = new Intent(getApplicationContext(), NotificationActivity.class);
        Notification notification = Storage.getInstance().getNewNotification();
        notification.setName("");
        notification.setReminderContainerKey(passedReminder.getKey());
        notification.setLifestyleContainerKey(passedReminder.getLifestyleContainerKey());
        passedReminder.addNotification(notification.getKey());
        Storage.getInstance().commitAbstractBaseEvent(notification);
        Storage.getInstance().replaceAbstractBaseEvent(passedReminder);
        notificationIntent.putExtra("Notification", notification);
        notificationIntent.putExtra("distanceFromRoot",distanceFromRoot+1);
        startActivity(notificationIntent);
    }

    @Override
    public Intent getSupportParentActivityIntent(){
        Log.e("Reminder Activity","return up " + Integer.toString(distanceFromRoot));
        switch(distanceFromRoot){
            case 0:
                Intent returnMain = new Intent(getApplicationContext(), MainActivity.class);

                return returnMain;
            case 1:
                Intent returnLifestyle = new Intent(getApplicationContext(), LifestyleActivity.class);
                returnLifestyle.putExtra("Lifestyle",Storage.getInstance().getLifestyle(passedReminder.getLifestyleContainerKey()));

                return returnLifestyle;

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
}
