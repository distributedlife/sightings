package com.distributedlife.animalwiki.filters;

import com.distributedlife.animalwiki.db.Sightings;
import com.distributedlife.animalwiki.model.Animal;

public class NotSeenFilter extends ToggleFilter {

    private Sightings sightings;

    public NotSeenFilter(boolean value, Sightings sightings) {
        super("Not Seen", value);
        this.sightings = sightings;
    }

    @Override
    public boolean exclude(Animal animal) {
        if (getValue()) {
            return false;
        }

        return sightings.hasSighting(animal.getCommonName().toLowerCase()) == getValue();
    }
}
