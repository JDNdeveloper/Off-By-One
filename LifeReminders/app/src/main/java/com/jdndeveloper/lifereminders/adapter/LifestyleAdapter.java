package com.jdndeveloper.lifereminders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jdndeveloper.lifereminders.EventTypes.Lifestyle;
import com.jdndeveloper.lifereminders.R;

import java.util.List;

/**
 * Created by jgemig on 2/5/2015.
 */
public class LifestyleAdapter extends ArrayAdapter{

    private final LayoutInflater inflater;
    private final List<Lifestyle> lifestyles;
    private final int rowResId;

    public LifestyleAdapter(Context context, int listTypeResId, int rowResId, List lifestyles) {
        super(context, listTypeResId, rowResId, lifestyles);

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.lifestyles = lifestyles;
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
        rowTextView.setText(lifestyles.get(position).getName());
        theSwitch.setChecked(lifestyles.get(position).isEnabled());

        theSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //change enabled state of Lifestyle
                String text = lifestyles.get(position).getName();
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
        return (lifestyles != null) ? lifestyles.size() : 0;
    }
}