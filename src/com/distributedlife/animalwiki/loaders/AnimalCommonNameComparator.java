package com.distributedlife.animalwiki.loaders;

import com.distributedlife.animalwiki.model.Animal;

import java.util.Comparator;

public class AnimalCommonNameComparator implements Comparator<Animal>{
    @Override
    public int compare(Animal animal, Animal animal2) {
        return animal.getCommonName().compareToIgnoreCase(animal2.getCommonName());
    }
}
