package com.distributedlife.animalwiki.filters;

import com.distributedlife.animalwiki.model.Animal;
import com.distributedlife.animalwiki.model.AnimalCollection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalFilterer {
    static public AnimalCollection apply(List<Filter> filterList, AnimalCollection animalCollection) {
        List<String> filteredHeaders = new ArrayList<String>();
        Map<String, List<Animal>> filteredChildren = new HashMap<String, List<Animal>>();

        for (String heading : animalCollection.getHeadings()) {
            for (Animal animal : animalCollection.getChildren().get(heading)) {
                boolean includeAnimal = true;

                for (Filter filter : filterList) {
                    if (filter.exclude(animal)) {
                        includeAnimal = false;
                        break;
                    }
                }

                if (includeAnimal) {
                    if (!filteredHeaders.contains(heading)) {
                        filteredHeaders.add(heading);
                    }

                    List<Animal> animalsUnderHeading;
                    if (filteredChildren.containsKey(heading)) {
                        animalsUnderHeading = filteredChildren.get(heading);
                    } else {
                        animalsUnderHeading = new ArrayList<Animal>();
                    }

                    animalsUnderHeading.add(animal);
                    filteredChildren.put(heading, animalsUnderHeading);
                }
            }
        }

        return new AnimalCollection(filteredHeaders, filteredChildren);
    }
}