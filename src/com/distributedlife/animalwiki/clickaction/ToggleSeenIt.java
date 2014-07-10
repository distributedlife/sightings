package com.distributedlife.animalwiki.clickaction;

import android.app.Activity;
import android.view.View;
import com.distributedlife.animalwiki.Sightings;
import com.distributedlife.animalwiki.model.Sighting;

public class ToggleSeenIt implements View.OnLongClickListener {
    private final String commonName;
    private final Activity activity;

    public ToggleSeenIt(String commonName, Activity activity) {
        this.commonName = commonName;
        this.activity = activity;
    }

    @Override
    public boolean onLongClick(View view) {
        Sightings sightings = new Sightings(activity);
        sightings.add(new Sighting(commonName));

//        animalDisplay.refresh();

        return true;
    }
}
