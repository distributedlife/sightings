package com.distributedlife.animalwiki.sorting;

import com.distributedlife.animalwiki.model.Sighting;

public class SightingsRecentToOldSorter implements java.util.Comparator<Sighting> {
    @Override
    public int compare(Sighting sighting, Sighting sighting2) {
        return sighting2.getWhen().compareTo(sighting.getWhen());
    }
}
