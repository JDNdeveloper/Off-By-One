package com.jdndeveloper.lifereminders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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

    public ReminderAdapter(Context context, int listTypeResId, int rowResId, List reminder) {
        super(context, listTypeResId, rowResId, reminder);

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.reminders = reminder;
        this.rowResId = rowResId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(rowResId, parent, false);

        TextView rowTextView = (TextView) convertView.findViewById(R.id.rowReminderTextView);
        Switch theSwitch = (Switch) convertView.findViewById(R.id.enabledSwitchReminderRow);

        // You need to null out the listener before you change the state of the checkbox. Otherwise,
        // the previous listener will be fired off. Likewise, all fields must be repopulated because
        // convertView isn't guaranteed to be clean - john - just an fyi

        theSwitch.setOnCheckedChangeListener(null);
        rowTextView.setText(reminders.get(position).getName());
        theSwitch.setChecked(reminders.get(position).isEnabled());

        theSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //change enabled state of Lifestyle
                //String text = reminders.get(position).getName();
                if (isChecked) {
                    //text += " is enabled";
                    reminders.get(position).setEnabled(true);
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
    @Override
    public int getCount() {
        return (reminders != null) ? reminders.size() : 0;
    }
}