package com.distributedlife.animalwiki.clickaction;

import android.view.View;
import com.distributedlife.animalwiki.activities.AnimalDisplay;
import com.distributedlife.animalwiki.db.Sightings;
import com.distributedlife.animalwiki.model.Animal;

public class RemoveSeenIt implements View.OnClickListener {
    private final AnimalDisplay animalDisplay;
    private Sightings sightings;
    private final Animal animal;

    public RemoveSeenIt(AnimalDisplay animalDisplay, Sightings sightings, Animal animal) {
        this.animalDisplay = animalDisplay;
        this.sightings = sightings;
        this.animal = animal;
    }

    @Override
    public void onClick(View view) {
        sightings.remove(animal);
        animalDisplay.refresh();
    }
}
