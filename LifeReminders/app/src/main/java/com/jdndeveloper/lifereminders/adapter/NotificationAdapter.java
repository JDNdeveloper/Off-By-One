package com.jdndeveloper.lifereminders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.R;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(rowResId, parent, false);

        TextView rowTextView1 = (TextView) convertView.findViewById(R.id.rowTextView1);
        TextView rowTextView2 = (TextView) convertView.findViewById(R.id.rowTextView2);
        TextView rowTextView3 = (TextView) convertView.findViewById(R.id.rowTextView3);
        rowTextView1.setText(notifications.get(position).getName());
        rowTextView2.setText(notifications.get(position).getKey());
        rowTextView3.setText(Boolean.toString(notifications.get(position).isEnabled()));
        return convertView;
    }
    @Override
    public int getCount() {
        return (notifications != null) ? notifications.size() : 0;
    }
}