package com.distributedlife.animalwiki;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PagesAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final java.util.List<String> files;
    private final Activity owner;

    public PagesAdapter(Context context, java.util.List<String> files, Activity owner) {
        super(context, R.id.place_to_put_list, files);
        this.context = context;
        this.files = files;
        this.owner = owner;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String file = files.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.wiki_pages_list , parent, false);

        rowView.setOnClickListener(new OpenElement(file, owner));

        ((TextView) rowView.findViewById(R.id.label)).setText(file.replaceAll(".html", "").replaceAll("_", " "));

        return rowView;
    }
}
