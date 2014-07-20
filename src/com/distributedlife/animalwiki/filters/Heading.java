package com.distributedlife.animalwiki.filters;

import com.distributedlife.animalwiki.model.Animal;

public class Heading implements Filter {
    private String name;

    public Heading(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean exclude(Animal animal) {
        return false;
    }
}
