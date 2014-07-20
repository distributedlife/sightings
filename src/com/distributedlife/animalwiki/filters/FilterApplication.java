package com.distributedlife.animalwiki.filters;

import com.distributedlife.animalwiki.model.Animal;

import java.util.ArrayList;
import java.util.List;

public class FilterApplication {
    static public List<Animal> apply(List<Filter> filterList, List<Animal> animals) {
        List<Animal> results = new ArrayList<Animal>();

        for (Animal animal : animals) {
            boolean includeAnimal = true;

            for (Filter filter : filterList) {
                if (filter.exclude(animal)) {
                    includeAnimal = false;
                    break;
                }
            }

            if (includeAnimal) {
                results.add(animal);
            }
        }

        return results;
    }
}