package com.distributedlife.animalwiki.clickaction;

import android.view.View;
import com.distributedlife.animalwiki.Sightings;
import com.distributedlife.animalwiki.activities.AnimalDisplay;
import com.distributedlife.animalwiki.model.Sighting;

public class SeenIt implements View.OnClickListener {
    private AnimalDisplay animalDisplay;
    private String what;

    public SeenIt(AnimalDisplay animalDisplay, String what) {
        this.animalDisplay = animalDisplay;
        this.what = what;
    }

    @Override
    public void onClick(View view) {
        Sightings sightings = new Sightings(animalDisplay);

        if (sightings.hasSighting(what)) {
            sightings.remove(new Sighting(what));
        } else {
            sightings.add(new Sighting(what));
        }

        animalDisplay.refresh();
    }
}
