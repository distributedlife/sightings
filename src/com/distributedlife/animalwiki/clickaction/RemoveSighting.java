package com.distributedlife.animalwiki.clickaction;

import android.view.View;
import android.widget.ArrayAdapter;
import com.distributedlife.animalwiki.db.Sightings;
import com.distributedlife.animalwiki.model.Sighting;

public class RemoveSighting implements View.OnClickListener {
    private Sightings sightings;
    private Sighting sighting;
    private ArrayAdapter owner;

    public RemoveSighting(Sightings sightings, Sighting sighting, ArrayAdapter owner) {
        this.sightings = sightings;
        this.sighting = sighting;
        this.owner = owner;
    }

    @Override
    public void onClick(View view) {
        sightings.remove(sighting);
        owner.notifyDataSetChanged();
    }
}
