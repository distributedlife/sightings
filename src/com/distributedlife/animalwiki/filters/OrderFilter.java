package com.distributedlife.animalwiki.filters;

import com.distributedlife.animalwiki.model.Animal;

public class OrderFilter extends ToggleFilter {
    public OrderFilter(String order, boolean value) {
        super(order, value);
    }

    @Override
    public boolean exclude(Animal animal) {
        if (getValue()) {
            return false;
        }

        return animal.getOrder().equals(getName()) != getValue();
    }
}
