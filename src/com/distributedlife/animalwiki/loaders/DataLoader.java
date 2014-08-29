package com.distributedlife.animalwiki.loaders;

import com.distributedlife.animalwiki.model.Animal;
import com.distributedlife.animalwiki.model.ConservationStatus;
import com.distributedlife.animalwiki.sorting.AnimalCommonNameComparator;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

public class DataLoader {
    static List<Animal> animals = new ArrayList<Animal>();
    private static HashMap<String, Animal> animalsAsHash = new HashMap<String, Animal>();

    public static void add(InputStream inputStream) {
        try {
            JSONObject root = new JSONObject(IOUtils.toString(inputStream));
            JSONArray animals = root.getJSONArray("animals");

            for (int i = 0; i < animals.length(); i++) {
                JSONObject animal = animals.getJSONObject(i);

                List<String> colours;
                if (animal.has("colours")) {
                    colours = mapToStringArray(animal.getJSONArray("colours"));
                } else {
                    colours = new ArrayList<String>();
                }

                List<String> countries;
                if (animal.has("countries")) {
                    countries = mapToStringArray(animal.getJSONArray("countries"));
                } else {
                    countries = new ArrayList<String>();
                }

                Animal inflatedAnimal = new Animal(
                        animal.getString("common_name"),
                        animal.getString("official_name"),
                        animal.getString("phylum"),
                        animal.getString("klass"),
                        animal.getString("order"),
                        animal.getString("family"),
                        animal.getString("genus"),
                        animal.getString("species"),
                        animal.getString("subspecies"),
                        ConservationStatus.fromString(animal.getString("conservation_status")),
                        animal.getString("new_filename"),
                        colours,
                        countries,
                        animal.has("endemic") ? animal.getBoolean("endemic") : false
                );
                DataLoader.animals.add(inflatedAnimal);
                DataLoader.animalsAsHash.put(inflatedAnimal.getKey(), inflatedAnimal);

            }

        } catch (IOException e) {
            throw new RuntimeException("IO Exception", e);
        } catch (JSONException e) {
            throw new RuntimeException("JSON exception", e);
        }

        Collections.sort(animals, new AnimalCommonNameComparator());
    }

    public static void load(InputStream inputStream) {
        if (animals.size() > 0) {
            return;
        }

        add(inputStream);
    }

    private static List<String> mapToStringArray(JSONArray colours) {
        List<String> array = new ArrayList<String>();

        for (int i = 0; i < colours.length(); i++) {
            try {
                array.add(colours.getString(i));
            } catch(JSONException e) {}
        }

        return array;
    }

    public static List<Animal> getAnimals() {
        return animals;
    }

    public static Map<String, Animal> getAnimalsAsHash() {
        return animalsAsHash;
    }
}
