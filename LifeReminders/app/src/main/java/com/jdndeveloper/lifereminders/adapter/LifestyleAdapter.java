package com.jdndeveloper.lifereminders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(rowResId, parent, false);

        TextView rowTextView = (TextView) convertView.findViewById(R.id.rowTextView);
        rowTextView.setText(lifestyles.get(position).getName());
        return convertView;
    }

    @Override
    public int getCount() {
        return (lifestyles != null) ? lifestyles.size() : 0;
    }
}