package com.distributedlife.animalwiki.filters;

import com.distributedlife.animalwiki.loaders.ReferenceDataLoader;
import com.distributedlife.animalwiki.model.Animal;

public class ClassFilter extends ToggleFilter {
    private String name;

    public ClassFilter(String name, boolean value) {
        super(ReferenceDataLoader.replaceKlass(name), value);
        this.name = name;
    }

    @Override
    public boolean exclude(Animal animal) {
        if (getValue()) {
            return false;
        }

        return animal.getKlass().equals(name) != getValue();
    }
}
