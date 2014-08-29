package com.distributedlife.animalwiki.model;

import com.distributedlife.animalwiki.sorting.AnimalCommonNameComparator;
import com.distributedlife.animalwiki.loaders.ReferenceDataLoader;

import java.util.*;

public class AnimalCollectionBuilder {
    private static final int SPLIT_ORDER_SIZE = 25;

    public static AnimalCollection organiseIntoOrdersAndFamilies(List<Animal> animals) {
        List<String> orders = getOrderNames(animals);
        Map<String, List<Animal>> orderBuckets = putAnimalsIntoOrderBuckets(animals);

        List<String> familyAndOrderBucketNames = new ArrayList<String>();
        Map<String, List<Animal>> familyAndOrderBuckets = new HashMap<String, List<Animal>>();
        for(String orderName : orders) {
            List<Animal> orderAnimals = orderBuckets.get(orderName);

            if (orderAnimals.size() <= SPLIT_ORDER_SIZE) {
                familyAndOrderBucketNames.add(orderName);
                familyAndOrderBuckets.put(orderName, orderAnimals);
            } else {
                for (Animal animal : orderAnimals) {
                    String family = ReferenceDataLoader.replaceFamily(animal.getFamily());
                    String familyName = String.format("%s (%s)", orderName, family);

                    if (!familyAndOrderBucketNames.contains(familyName)) {
                        familyAndOrderBucketNames.add(familyName);
                    }

                    List<Animal> familyAnimals;
                    if (familyAndOrderBuckets.containsKey(familyName)) {
                        familyAnimals = familyAndOrderBuckets.get(familyName);
                    } else {
                        familyAnimals = new ArrayList<Animal>();
                    }

                    familyAnimals.add(animal);
                    familyAndOrderBuckets.put(familyName, familyAnimals);
                }
            }
        }
        for(String familyAndOrderName : familyAndOrderBuckets.keySet()) {
            List<Animal> familyAndOrderAnimals = familyAndOrderBuckets.get(familyAndOrderName);
            Collections.sort(familyAndOrderAnimals, new AnimalCommonNameComparator());
            familyAndOrderBuckets.put(familyAndOrderName, familyAndOrderAnimals);
        }
        Collections.sort(familyAndOrderBucketNames);

        return new AnimalCollection(familyAndOrderBucketNames, familyAndOrderBuckets);
    }


    private static List<String> getOrderNames(List<Animal> animals) {
        List<String> orders = new ArrayList<String>();

        for (Animal animal : animals) {
            String order = ReferenceDataLoader.replaceOrder(animal.getOrder());
            if (orders.contains(order)) {
                continue;
            }

            orders.add(order);
        }
        return orders;
    }

    private static Map<String, List<Animal>> putAnimalsIntoOrderBuckets(List<Animal> animals) {
        Map<String, List<Animal>> allAnimals = new HashMap<String, List<Animal>>();

        for (Animal animal : animals) {
            String order = ReferenceDataLoader.replaceOrder(animal.getOrder());

            List<Animal> orderAnimals;
            if (allAnimals.containsKey(order)) {
                orderAnimals = allAnimals.get(order);
            } else {
                orderAnimals = new ArrayList<Animal>();
            }

            orderAnimals.add(animal);
            allAnimals.put(order, orderAnimals);
        }

        return allAnimals;
    }
}
