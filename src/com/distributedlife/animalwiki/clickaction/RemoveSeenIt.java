package com.distributedlife.animalwiki.clickaction;

import android.view.View;
import com.distributedlife.animalwiki.activities.AnimalDisplay;
import com.distributedlife.animalwiki.db.Sightings;

public class RemoveSeenIt implements View.OnClickListener {
    private final AnimalDisplay animalDisplay;
    private Sightings sightings;
    private final String what;

    public RemoveSeenIt(AnimalDisplay animalDisplay, Sightings sightings, String what) {
        this.animalDisplay = animalDisplay;
        this.sightings = sightings;
        this.what = what;
    }

    @Override
    public void onClick(View view) {
        sightings.remove(what);
        animalDisplay.refresh();
    }
}
