package com.jdndeveloper.lifereminders.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.R;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.Calendar;
import java.util.List;

/**
 * Created by jgemig on 2/5/2015.
 */
public class NotificationAdapter extends ArrayAdapter{

    private final LayoutInflater inflater;
    private final List<Notification> notifications;
    private final int rowResId;

    public NotificationAdapter(Context context, int listTypeResId, int rowResId, List notification) {
        super(context, listTypeResId, rowResId, notification);

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.notifications = notification;
        this.rowResId = rowResId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(rowResId, parent, false);

        TextView rowTextView = (TextView) convertView.findViewById(R.id.rowNotificationTextView);
        TextView containerReminder = (TextView) convertView.findViewById(R.id.rowNotificationContainerReminder);
        Switch theSwitch = (Switch) convertView.findViewById(R.id.enabledSwitchNotificationRow);

        // You need to null out the listener before you change the state of the checkbox. Otherwise,
        // the previous listener will be fired off. Likewise, all fields must be repopulated because
        // convertView isn't guaranteed to be clean - john - just an fyi

        theSwitch.setOnCheckedChangeListener(null);

        Calendar time = notifications.get(position).getTime();

        String amPM = "amPM FAIL";

        if (time.get(Calendar.AM_PM) == 1) {
            amPM = "PM";
        } else {
            amPM = "AM";
        }

        rowTextView.setText(Integer.toString(time.get(Calendar.HOUR))
                + ":" + Integer.toString(time.get(Calendar.MINUTE))
                + " " + amPM);

        String reminderKey = notifications.get(position).getReminderContainerKey();

        containerReminder.setText(Storage.getInstance().getReminder(reminderKey).getName());
        //containerReminder.setText("TEST 123 hello HOW IS IT GOING??");

        theSwitch.setChecked(notifications.get(position).isEnabled());

        theSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //change enabled state of Lifestyle
                String text = notifications.get(position).getName();
                if (isChecked) {
                    text += " is enabled";
                } else {
                    text += " is disabled";
                }
                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
    @Override
    public int getCount() {
        return (notifications != null) ? notifications.size() : 0;
    }
}