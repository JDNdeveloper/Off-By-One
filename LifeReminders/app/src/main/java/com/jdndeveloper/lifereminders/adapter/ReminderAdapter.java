package com.jdndeveloper.lifereminders.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.R;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.List;

/**
 * Created by jgemig on 2/5/2015.
 */
public class ReminderAdapter extends ArrayAdapter{

    private final LayoutInflater inflater;
    private final List<Reminder> reminders;
    private final int rowResId;
    private boolean displayContainer;

    public ReminderAdapter(Context context, int listTypeResId, int rowResId, List reminder, boolean tempDisplayContainer) {
        super(context, listTypeResId, rowResId, reminder);

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.reminders = reminder;
        this.rowResId = rowResId;
        this.displayContainer = tempDisplayContainer;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(rowResId, parent, false);

        TextView rowTextView = (TextView) convertView.findViewById(R.id.rowReminderTextView);
        TextView rowContainerLifestyleTextView = (TextView)
                convertView.findViewById(R.id.rowReminderContainerLifestyle);
        Switch theSwitch = (Switch) convertView.findViewById(R.id.enabledSwitchReminderRow);

        // You need to null out the listener before you change the state of the checkbox. Otherwise,
        // the previous listener will be fired off. Likewise, all fields must be repopulated because
        // convertView isn't guaranteed to be clean - john - just an fyi

        theSwitch.setOnCheckedChangeListener(null);

        rowContainerLifestyleTextView.setVisibility(View.VISIBLE);

        rowTextView.setText(reminders.get(position).getName());
        theSwitch.setChecked(reminders.get(position).isEnabled());

        setEverythingEnabled(theSwitch, rowTextView, rowContainerLifestyleTextView);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            theSwitch.setElevation(100);
        }


        //sets lifestyle container name
        //rowContainerLifestyleTextView.setText("BROKEN!!! BROKEN!!!"); //REMOVE
        //UNCOMMENT BELOW AFTER STORAGE IS FIXED lines 70-71 in Storage.java are BROKEN

        if (!Storage.getInstance().
                getLifestyle(reminders.get(position).getLifestyleContainerKey()).getKey()
                .equals(Constants.LIFESTYLE_FAILED_KEY)) {
            rowContainerLifestyleTextView.setText(Storage.getInstance().
                    getLifestyle(reminders.get(position).getLifestyleContainerKey()).getName());
        } else {
            //rowContainerLifestyleTextView.setText("FAILED LIFESTYLE");
            //rowContainerLifestyleTextView.setVisibility(View.GONE);
            rowContainerLifestyleTextView.setText("Unsorted");
            //to use R.color.gray (which we want) would need context :/
            rowContainerLifestyleTextView.setTextColor(Color.parseColor("#3b6a8e"));
        }

        if (!Storage.getInstance().getLifestyle(reminders.get(position)
                .getLifestyleContainerKey()).isEnabled())
            setEverythingDisabled(theSwitch, rowTextView, rowContainerLifestyleTextView);


        if (!displayContainer)
            rowContainerLifestyleTextView.setVisibility(View.GONE);

        theSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //change enabled state of Lifestyle
                //String text = reminders.get(position).getName();
                if (isChecked) {
                    //text += " is enabled";
                    reminders.get(position).setEnabled(true);
                    reminders.get(position).setAlarms(getContext());
                    Storage.getInstance().replaceAbstractBaseEvent(reminders.get(position));
                } else {
                    //text += " is disabled";
                    reminders.get(position).setEnabled(false);
                    Storage.getInstance().replaceAbstractBaseEvent(reminders.get(position));
                }
                //Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    public void setEverythingEnabled(Switch s, TextView t, TextView r) {
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            s.setThumbResource(R.drawable.switch_thumb);
            s.refreshDrawableState();
        }
        t.setTextColor(Color.parseColor("#ff202020"));
        r.setTextColor(Color.parseColor("#9e0022"));
    }

    public void setEverythingDisabled(Switch s, TextView t, TextView r) {
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            s.setThumbResource(R.drawable.apptheme_switch_thumb_disabled_holo_light);
            s.refreshDrawableState();
        }
        t.setTextColor(Color.parseColor("#808080"));
        r.setTextColor(Color.parseColor("#808080"));
    }


    @Override
    public int getCount() {
        return (reminders != null) ? reminders.size() : 0;
    }
}