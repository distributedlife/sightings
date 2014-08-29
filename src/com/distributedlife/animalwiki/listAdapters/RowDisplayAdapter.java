package com.distributedlife.animalwiki.listAdapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.distributedlife.animalwiki.R;
import com.distributedlife.animalwiki.factories.RowDisplayFactory;

import java.util.List;

public class RowDisplayAdapter extends ArrayAdapter<Object> {
    private final List<Object> rows;
    private final List<Integer> types;

    private RowDisplayFactory factory;

    public RowDisplayAdapter(Context context, List<Object> rows, List<Integer> types, RowDisplayFactory sightingsRowDisplayFactory) {
        super(context, R.id.place_to_put_list, rows);
        this.rows = rows;
        this.types = types;

        factory = sightingsRowDisplayFactory;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return factory.build(position).display(convertView, parent, rows.get(position));
    }

    @Override
    public int getViewTypeCount() {
        return factory.getTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return types.get(position);
    }
}
