package com.distributedlife.animalwiki.partials;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.distributedlife.animalwiki.R;

public class SightingsHeaderRowDisplay implements RowDisplay {
    private final LayoutInflater inflater;

    public SightingsHeaderRowDisplay(Context context) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View display(View convertView, ViewGroup parent, Object data) {
        String label = (String) data;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.sighting_heading, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.label)).setText(label);

        return convertView;
    }
}