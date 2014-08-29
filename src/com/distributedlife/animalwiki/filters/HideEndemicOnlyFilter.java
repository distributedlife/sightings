package com.distributedlife.animalwiki.filters;

import com.distributedlife.animalwiki.model.Animal;

public class HideEndemicOnlyFilter extends ToggleFilter {
    public HideEndemicOnlyFilter() {
        super("Hide Endemics", false);
    }

    @Override
    public boolean exclude(Animal animal) {
        return animal.isEndemic();
    }
}
