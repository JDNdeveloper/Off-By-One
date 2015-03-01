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

import com.jdndeveloper.lifereminders.Constants;
import com.jdndeveloper.lifereminders.EventTypes.Option;
import com.jdndeveloper.lifereminders.EventTypes.Reminder;
import com.jdndeveloper.lifereminders.R;
import com.jdndeveloper.lifereminders.storage.Storage;

import java.util.List;

/**
 * Created by jgemig on 2/5/2015.
 */
public class SettingsAdapter extends ArrayAdapter{

    private final LayoutInflater inflater;
    private final List<Option> options;
    private final int rowResId;

    public SettingsAdapter(Context context, int listTypeResId, int rowResId, List option) {
        super(context, listTypeResId, rowResId, option);

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.options = option;
        this.rowResId = rowResId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(rowResId, parent, false);

        TextView rowTextView = (TextView) convertView.findViewById(R.id.rowLifestyleTextView);
        Switch theSwitch = (Switch) convertView.findViewById(R.id.enabledSwitchLifestyleRow);

        // You need to null out the listener before you change the state of the checkbox. Otherwise,
        // the previous listener will be fired off. Likewise, all fields must be repopulated because
        // convertView isn't guaranteed to be clean - john - just an fyi

        theSwitch.setOnCheckedChangeListener(null);
        rowTextView.setText(options.get(position).getName());
        theSwitch.setChecked(options.get(position).isEnabled());

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            theSwitch.setElevation(100);
        }

        theSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //change enabled state of Lifestyle
                //String text = reminders.get(position).getName();
                if (isChecked) {
                    //text += " is enabled";
                    options.get(position).setEnabled(true);
                    //store in storage
                } else {
                    //text += " is disabled";
                    options.get(position).setEnabled(false);
                    //store in storage
                }
                //Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    public void setEverythingDisabled(Switch s, TextView t, TextView r) {
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            s.setThumbResource(R.drawable.apptheme_switch_thumb_disabled_holo_light);
        }
        t.setTextColor(Color.parseColor("#808080"));
        r.setTextColor(Color.parseColor("#808080"));
    }


    @Override
    public int getCount() {
        return (options != null) ? options.size() : 0;
    }
}