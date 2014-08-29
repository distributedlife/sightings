package com.distributedlife.animalwiki.filters;

import com.distributedlife.animalwiki.db.Sightings;
import com.distributedlife.animalwiki.model.Animal;

public class SeenFilter extends ToggleFilter {
    private Sightings sightings;

    public SeenFilter(boolean value, Sightings sightings) {
        super("Seen", value);
        this.sightings = sightings;
    }

    @Override
    public boolean exclude(Animal animal) {
        if (getValue()) {
            return false;
        }

        return sightings.hasSighting(animal) != getValue();
    }
}
