package com.jdndeveloper.lifereminders.EventActivities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
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

import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventTypes.AbstractBaseEvent;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.MainActivity;
import com.jdndeveloper.lifereminders.R;

import com.jdndeveloper.lifereminders.R;
import com.jdndeveloper.lifereminders.adapter.ReminderAdapter;
import com.jdndeveloper.lifereminders.interfaces.StorageInterface;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.ArrayList;
import java.util.List;




public class LifestyleActivity extends ActionBarActivity {

    private Lifestyle passedLifestyle;
    ImageButton buttonlistner;

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_style);
        setupActionBar();
        changeStatusBarColor(R.color.life_action_status_bar);

        //MainActivity.FragmentLocation = 1;

        passedLifestyle = (Lifestyle) getIntent().getSerializableExtra("Lifestyle");
        Log.i("LifestyleActivity", "Passed Lifestyle: " + passedLifestyle.getKey());

        //Create listener for name change
        final EditText editText = (EditText) findViewById(R.id.lifestyleName);
        editText.setText(passedLifestyle.getName());
        editText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                passedLifestyle.setName(editText.getText().toString());
                if(!Storage.getInstance().replaceAbstractBaseEvent(passedLifestyle)){
                    Toast.makeText(getApplicationContext(), "Lifestyle Name Was Not Saved Properly", Toast.LENGTH_SHORT).show();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });


        //Create listener for enables/disabled switch
        Switch theSwitch = (Switch) findViewById(R.id.lifestyleEnabled);
        theSwitch.setOnCheckedChangeListener(null);
        theSwitch.setChecked(passedLifestyle.isEnabled());

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            theSwitch.setElevation(100);
        }

        theSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //change enabled state of Lifestyle
                passedLifestyle.setEnabled(!passedLifestyle.isEnabled());
                Storage.getInstance().replaceAbstractBaseEvent(passedLifestyle);
                updateListAdapter();
            }
        });


        updateListAdapter();

        buttonclick();




    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //MainActivity.FragmentLocation = 1;
    }

    public void updateListAdapter(){
        ListView listView = (ListView) findViewById(R.id.lifestyleListView);
        final List<Reminder> reminderArray = new ArrayList<>();
        for(String r : passedLifestyle.getReminders()){
            Log.e("Lifestyle Activity",r);
            if(!Storage.getInstance().getReminder(r).getKey().equals(Constants.REMINDER_FAILED_KEY)) reminderArray.add(Storage.getInstance().getReminder(r));
        }
        listView.setAdapter(new ReminderAdapter(this,
                android.R.layout.simple_list_item_2,
                R.layout.reminder_row, reminderArray
        ));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Log.e("LifestyleActivity", "position " + Integer.toString(position));
                    Intent reminderIntent = new Intent(getApplicationContext(), ReminderActivity.class);
                    Reminder reminder = reminderArray.get(position);
                    reminderIntent.putExtra("Reminder", reminder);
                    startActivity(reminderIntent);
                }catch (IndexOutOfBoundsException e){
                    Log.e("Lifestyle Activity","IndexOutOfBounds Exception");
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final AbstractBaseEvent abe = (AbstractBaseEvent) parent.getAdapter().getItem(position);



                String type = "Reminder";
                String name = abe.getName();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
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
                Log.e("Lifestyle Activity","newReminder");
                Intent reminderIntent = new Intent(getApplicationContext(), ReminderActivity.class);
                Reminder reminder = Storage.getInstance().getNewReminder();
                reminder.setName("");
                reminder.setLifestyleContainerKey(passedLifestyle.getKey());
                passedLifestyle.addReminder(reminder.getKey());
                if(!Storage.getInstance().commitAbstractBaseEvent(reminder) ||
                        !Storage.getInstance().replaceAbstractBaseEvent(passedLifestyle)){
                    Toast.makeText(getApplicationContext(), "Reminder Was Not Created/Saved Properly", Toast.LENGTH_SHORT).show();
                    //return;
                }
                reminderIntent.putExtra("Reminder", reminder);
                startActivity(reminderIntent);
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        updateListAdapter();
    }

    @Override
    public Intent getSupportParentActivityIntent(){
        Log.e("Lifestyle Activity","return up");
        //needs to change
        Intent returnMain = new Intent(getApplicationContext(), MainActivity.class);
        return returnMain;
        //return super.getSupportParentActivityIntent();
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