package com.distributedlife.animalwiki.clickaction;

import android.view.View;
import com.distributedlife.animalwiki.db.Sightings;
import com.distributedlife.animalwiki.activities.AnimalDisplay;
import com.distributedlife.animalwiki.model.Sighting;

public class RemoveSeenIt implements View.OnClickListener {
    private final AnimalDisplay animalDisplay;
    private final String what;

    public RemoveSeenIt(AnimalDisplay animalDisplay, String what) {
        this.animalDisplay = animalDisplay;
        this.what = what;
    }

    @Override
    public void onClick(View view) {
        Sightings sightings = new Sightings(animalDisplay);
        sightings.remove(new Sighting(what));

        animalDisplay.refresh();
    }
}
