package com.distributedlife.animalwiki.partials;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.distributedlife.animalwiki.R;
import com.distributedlife.animalwiki.clickaction.OpenElement;
import com.distributedlife.animalwiki.listAdapters.ImageDisplay;
import com.distributedlife.animalwiki.model.Animal;
import com.distributedlife.animalwiki.model.Sighting;

import java.util.Map;

public class AnimalRowDisplay implements RowDisplay {
    private final LayoutInflater inflater;
    private ImageDisplay imageDisplay;
    private Map<String, Animal> animals;
    private final Activity owner;

    public AnimalRowDisplay(ImageDisplay imageDisplay, Map<String, Animal> animals, Context context, Activity owner) {
        this.imageDisplay = imageDisplay;
        this.animals = animals;
        this.owner = owner;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View display(View convertView, ViewGroup parent, Object data) {
        Sighting sighting = (Sighting) data;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.sighting_list_item, parent, false);
        }

        Animal animal = animals.get(sighting.getWhat());
        if (animal == null) {
            Log.e("Sightings", "Missing animal in RowDisplayAdapter: " + sighting.getWhat());
            return convertView;
        }

        ((TextView) convertView.findViewById(R.id.label)).setText(animal.getCommonName());
        ((TextView) convertView.findViewById(R.id.officialName)).setText(animal.getOfficialName());

        imageDisplay.display(animal, (ImageView) convertView.findViewById(R.id.imageIcon), owner);

        ImageView removeSightingButton = (ImageView) convertView.findViewById(R.id.removeSighting);
//        removeSightingButton.setOnClickListener(new RemoveSighting(new Sightings(context), sighting, sightingsAdapter));
        removeSightingButton.setBackground(null);

        convertView.setOnClickListener(new OpenElement(animal, owner));

        return convertView;
    }
}