package com.distributedlife.animalwiki.clickaction;

import android.view.View;
import com.distributedlife.animalwiki.activities.AnimalDisplay;
import com.distributedlife.animalwiki.db.Sightings;
import com.distributedlife.animalwiki.model.Animal;

public class SeenIt implements View.OnClickListener {
    private AnimalDisplay animalDisplay;
    private Sightings sightings;
    private Animal animal;

    public SeenIt(AnimalDisplay animalDisplay, Sightings sightings, Animal animal) {
        this.animalDisplay = animalDisplay;
        this.sightings = sightings;
        this.animal = animal;
    }

    @Override
    public void onClick(View view) {
        if (sightings.hasSighting(animal)) {
            sightings.remove(animal);
        } else {
            sightings.add(animal);
        }

        animalDisplay.refresh();
    }
}
