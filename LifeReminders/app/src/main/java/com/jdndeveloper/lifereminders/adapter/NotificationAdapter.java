package com.jdndeveloper.lifereminders.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventTypes.Action;
import com.jdndeveloper.lifereminders.EventTypes.Notification;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.R;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jgemig on 2/5/2015.
 */
public class NotificationAdapter extends ArrayAdapter{

    private final LayoutInflater inflater;
    private final List<Notification> notifications;
    private final int rowResId;

    private final String[] DAYS_IN_WEEK = {"", "Sun", "Mon", "Tue",
            "Wed", "Thu", "Fri", "Sat"};

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


        if (Storage.getInstance().
                getReminder(reminderKey) != null) {
            if (Storage.getInstance().getReminder(reminderKey).getKey()
                    != Constants.REMINDER_FAILED_KEY
                    // the following code shouldn't be necessary, but currently storage isn't
                    // returning failed key object like it should when it isn't found
                    // remove below code marked REMOVE after John fixes this
                    && !Storage.getInstance().getReminder(reminderKey).getName() //REMOVE, storage
                    .equals("Failed Reminder") ) {                               //REMOVE, storage
                containerReminder.setText(Storage.getInstance().getReminder(reminderKey).getName());
                //containerReminder.setText("TEST 123 hello HOW IS IT GOING??");
            }else {
                containerReminder.setText("Unsorted");
                containerReminder.setTextColor(Color.parseColor("#808080"));
            }
        } else {
            containerReminder.setText("Unsorted");
            containerReminder.setTextColor(Color.parseColor("#808080"));
        }

        theSwitch.setChecked(notifications.get(position).isEnabled());

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            theSwitch.setElevation(100);
        }

        theSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //change enabled state of Lifestyle
                //String text = notifications.get(position).getName();
                if (isChecked) {
                    //text += " is enabled";
                    notifications.get(position).setEnabled(true);
                    Storage.getInstance().replaceAbstractBaseEvent(notifications.get(position));
                } else {
                    //text += " is disabled";
                    notifications.get(position).setEnabled(false);
                    Storage.getInstance().replaceAbstractBaseEvent(notifications.get(position));
                }
                //Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });

        //Lower text
        TextView repeatText = (TextView) convertView.findViewById(R.id.rowNotificationRepeatText);
        TextView alarmTypeText = (TextView) convertView.findViewById(R.id.rowNotificationAlarmTypeText);

        //repeatText.setText("repeat test");
        //alarmTypeText.setText("alarmtype test");

        String textRepeat = getRepeatText(notifications.get(position));
        if (!textRepeat.equals("")) {
            repeatText.setText(textRepeat);
        } else {
            repeatText.setVisibility(View.INVISIBLE);
        }

        alarmTypeText.setText(getAlarmTypeText(notifications.get(position)));

        return convertView;
    }
    @Override
    public int getCount() {
        return (notifications != null) ? notifications.size() : 0;
    }

    private String getRepeatText(Notification n) {
        //if (!n.isRepeating()) return "";

        Calendar c = n.getTime();
        String text = "";

        if (n.isRepeatDaysEnabled()) {
            text += getWeekDays(n.getRepeatDays());
        } else if (n.isRepeatEveryBlankDaysEnabled()) {
            text += "Repeat every " + n.getRepeatEveryBlankDays() + " days";
        } else {
            text += getCalendarDate(c);
        }

        return text;
    }

    private String getAlarmTypeText(Notification n) {
        Action a = Storage.getInstance().getAction(n.getActionKey());
        String text = "";
        if (a.isNotificationSound()) {
            text += "sound";
        } else if (a.isVibrate()) {
            text += "vibrate";
        } else {
            text += "silent";
        }

        if (a.isNotificationSound() && a.isVibrate())
            text = "sound/vibrate";

        return text;
    }

    private String getCalendarDate(Calendar c) {
        String text = "Date: ";
        text += Integer.toString(c.get(Calendar.MONTH));
        text += "/";
        text += Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        return text;
    }

    private String getWeekDays(ArrayList<Integer> repeatDays) {
        String text = "";
        boolean started = false;
        for (int i = 0; i <= 7; i++) {
            if (repeatDays.contains(i)) {
                if (started) {
                    text += ", ";
                }
                text += DAYS_IN_WEEK[i];
                started = true;
            }
        }
        return text;
    }
}