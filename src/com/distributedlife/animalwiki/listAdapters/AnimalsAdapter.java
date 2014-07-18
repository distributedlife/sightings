package com.distributedlife.animalwiki.listAdapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.distributedlife.animalwiki.R;
import com.distributedlife.animalwiki.Sightings;
import com.distributedlife.animalwiki.clickaction.OpenElement;
import com.distributedlife.animalwiki.clickaction.ToggleSeenIt;
import com.distributedlife.animalwiki.formatting.AnimalFormatting;
import com.distributedlife.animalwiki.model.Animal;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class AnimalsAdapter extends ArrayAdapter<Animal> {
    private final Context context;
    private final java.util.List<Animal> allAnimals;
    private Sightings sightings;
    private final Activity owner;

    public AnimalsAdapter(Context context, List<Animal> allAnimals, Sightings sightings, Activity owner) {
        super(context, R.id.place_to_put_list, allAnimals);
        this.context = context;
        this.allAnimals = allAnimals;
        this.sightings = sightings;
        this.owner = owner;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Animal animal = allAnimals.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.animal_list_item, parent, false);

        rowView.setOnClickListener(new OpenElement(animal.getWikiFileName(), owner));
        rowView.setOnLongClickListener(new ToggleSeenIt(animal.getCommonName(), owner));

        ((TextView) rowView.findViewById(R.id.label)).setText(animal.getCommonName());

        TextView conservationStatus = (TextView) rowView.findViewById(R.id.conservationStatus);
        conservationStatus.setText(animal.getConservationStatus().toAbbreviation());
        conservationStatus.setTextColor(AnimalFormatting.getTextColourForConservationStatus(animal.getConservationStatus()));
        conservationStatus.setBackgroundColor(AnimalFormatting.getBackgroundColourForConservationStatus(animal.getConservationStatus()));

        for (int i = 0; i < animal.getColours().size(); i++) {
            String colour = animal.getColour(i);
            if (colour == null) {
                View swatch = rowView.findViewById(AnimalFormatting.swatches().get(i));
                if (swatch != null) {
                    swatch.setVisibility(View.GONE);
                }

                continue;
            }

            rowView.findViewById(AnimalFormatting.swatches().get(i)).setBackgroundColor(Color.parseColor(colour));
        }

        for (int i = animal.getColours().size(); i < 9; i++) {
            rowView.findViewById(AnimalFormatting.swatches().get(i)).setVisibility(View.GONE);
        }

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageIcon);
        displayImage(animal, imageView, owner);


        if (sightings.hasSighting(animal.getCommonName().toLowerCase())) {
            ((TextView) rowView.findViewById(R.id.sightings)).setText("I've seen it!");
        } else {
            ((TextView) rowView.findViewById(R.id.sightings)).setText("");
        }

        return rowView;
    }

    private void displayImage(Animal animal, ImageView imageView, Activity owner1) {
        if (animal.hasImage()) {
            try {
                InputStream ims = owner1.getAssets().open("html/" + animal.getFilename());
                Drawable d = Drawable.createFromStream(ims, null);
                imageView.setImageDrawable(d);
            } catch (IOException e) {
                System.out.println(String.format("Missing image for %s. Filename: %s", animal.getCommonName(), animal.getFilename()));
            }
        }
    }

    public void setFilter(List<Animal> filteredAnimals) {
        this.clear();
        this.addAll(filteredAnimals);
        this.notifyDataSetChanged();
    }
}
