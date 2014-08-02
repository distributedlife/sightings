package com.distributedlife.animalwiki.clickaction;

import android.view.View;
import com.distributedlife.animalwiki.activities.AnimalDisplay;
import com.distributedlife.animalwiki.db.Sightings;

public class SeenIt implements View.OnClickListener {
    private AnimalDisplay animalDisplay;
    private Sightings sightings;
    private String what;

    public SeenIt(AnimalDisplay animalDisplay, Sightings sightings, String what) {
        this.animalDisplay = animalDisplay;
        this.sightings = sightings;
        this.what = what;
    }

    @Override
    public void onClick(View view) {
        if (sightings.hasSighting(what)) {
            sightings.remove(what);
        } else {
            sightings.add(what);
        }

        animalDisplay.refresh();
    }
}
