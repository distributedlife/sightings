package com.distributedlife.animalwiki.filters;

import com.distributedlife.animalwiki.model.Animal;

public class ShowEndemicOnlyFilter extends ToggleFilter {
    public ShowEndemicOnlyFilter() {
        super("Show only endemics", false);
    }

    @Override
    public boolean exclude(Animal animal) {
        if (!getValue()) {
            return false;
        }

        return !animal.isEndemic();
    }
}
