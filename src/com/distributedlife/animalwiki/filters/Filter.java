package com.distributedlife.animalwiki.filters;

import com.distributedlife.animalwiki.model.Animal;

public interface Filter {
    public String getName();

    boolean exclude(Animal animal);
}
