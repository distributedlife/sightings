package com.distributedlife.animalwiki.filters;

import com.distributedlife.animalwiki.model.Animal;
import com.distributedlife.animalwiki.model.ConservationStatus;

public class ConservationStatusFilter extends ToggleFilter {
    private ConservationStatus conservationStatus;

    public ConservationStatusFilter(ConservationStatus conservationStatus, boolean value) {
        super(conservationStatus.toString(), value);
        this.conservationStatus = conservationStatus;
    }

    @Override
    public boolean exclude(Animal animal) {
        if (getValue()) {
            return false;
        }

        return animal.getConservationStatus().equals(conservationStatus) != getValue();
    }
}
