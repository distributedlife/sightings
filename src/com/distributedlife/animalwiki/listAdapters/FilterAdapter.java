package com.distributedlife.animalwiki.listAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.distributedlife.animalwiki.R;
import com.distributedlife.animalwiki.clickaction.ToggleFilterAction;
import com.distributedlife.animalwiki.filters.Filter;
import com.distributedlife.animalwiki.filters.Heading;
import com.distributedlife.animalwiki.filters.ToggleFilter;

import java.util.List;

public class FilterAdapter extends ArrayAdapter<Filter> {
    private List<Filter> filterItems;
    private final Activity owner;
    private final Context context;

    public FilterAdapter(Context context, List<Filter> filterItems, Activity owner) {
        super(context, R.id.filters, filterItems);
        this.context = context;
        this.filterItems = filterItems;
        this.owner = owner;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Filter filter = filterItems.get(position);

        if (filter instanceof ToggleFilter) {
            ToggleFilter toggleFilter = (ToggleFilter) filter;

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.filter_pick_item, parent, false);

            ((TextView) rowView.findViewById(R.id.label)).setText(toggleFilter.getName());

            ToggleButton toggle = (ToggleButton) rowView.findViewById(R.id.toggle);
            toggle.setChecked(toggleFilter.getValue());
            toggle.setOnCheckedChangeListener(new ToggleFilterAction(toggleFilter));

            return rowView;
        }
        if (filter instanceof Heading) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.filter_heading, parent, false);

            ((TextView) rowView.findViewById(R.id.label)).setText(filter.getName());

//            CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.enabled);
//            checkBox.setOnCheckedChangeListener();

            return rowView;
        }

        return null;
    }
}
