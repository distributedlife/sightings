package com.distributedlife.animalwiki.filters;

import com.distributedlife.animalwiki.loaders.ReferenceDataLoader;
import com.distributedlife.animalwiki.model.Animal;

public class CountryFilter extends ToggleFilter {
    private String country;

    public CountryFilter(String country, boolean enabled) {
        super(ReferenceDataLoader.replaceCountry(country), enabled);
        this.country = country;
    }

    @Override
    public boolean exclude(Animal animal) {
        if (getValue()) {
            return false;
        }

        return animal.getCountries().contains(country) != getValue();
    }
}
