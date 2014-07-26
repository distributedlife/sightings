package com.distributedlife.animalwiki.listAdapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import com.distributedlife.animalwiki.R;
import com.distributedlife.animalwiki.clickaction.ToggleFilterAction;
import com.distributedlife.animalwiki.clickaction.ZeroFilterList;
import com.distributedlife.animalwiki.filters.Filter;
import com.distributedlife.animalwiki.filters.ToggleFilter;
import com.distributedlife.animalwiki.filters.ZeroFilter;

import java.util.List;
import java.util.Map;

public class FilterAdapter extends BaseExpandableListAdapter {
    private final Context context;
    private final List<String> headers;
    private final Map<String, List<Filter>> children;

    public FilterAdapter(Context context, List<String> headers, Map<String, List<Filter>> children) {
        this.context = context;
        this.headers = headers;
        this.children = children;
    }

    @Override
    public int getGroupCount() {
        return headers.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return children.get(headers.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headers.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return children.get(headers.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,	View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.filter_heading, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.label);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Filter filter = (Filter) getChild(groupPosition, childPosition);
        if (filter instanceof ToggleFilter) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.filter_pick_item, null);
            }

            ToggleFilter toggleFilter = (ToggleFilter) filter;

            ((TextView) convertView.findViewById(R.id.label)).setText(toggleFilter.getName());

            Switch toggle = (Switch) convertView.findViewById(R.id.toggle);
            toggle.setChecked(toggleFilter.getValue());
            toggle.setOnCheckedChangeListener(new ToggleFilterAction(toggleFilter));
        }
        if (filter instanceof ZeroFilter) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.filter_reset_all, null);
            }

            ZeroFilter zeroFilter = (ZeroFilter) filter;

            Button button = (Button) convertView.findViewById(R.id.none);
            button.setOnClickListener(new ZeroFilterList(zeroFilter.getFilters(), this));
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
