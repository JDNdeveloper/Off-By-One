package com.jdndeveloper.lifereminders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.R;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(rowResId, parent, false);

        TextView rowTextView = (TextView) convertView.findViewById(R.id.rowTextView);
        rowTextView.setText(reminders.get(position).getName());
        return convertView;
    }
    @Override
    public int getCount() {
        return (reminders != null) ? reminders.size() : 0;
    }
}