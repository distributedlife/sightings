package com.distributedlife.animalwiki.loaders;

import com.distributedlife.animalwiki.model.Animal;
import com.distributedlife.animalwiki.model.ConservationStatus;
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

    public static void load(InputStream jsonFile) {
        if (animals.size() > 0) {
            return;
        }

        try {
            JSONObject root = new JSONObject(IOUtils.toString(jsonFile));
            JSONArray birds = root.getJSONArray("birds");

            for (int i = 0; i < birds.length(); i++) {
                JSONObject bird = birds.getJSONObject(i);

                List<String> colours;
                if (bird.has("colours")) {
                    colours = mapToStringArray(bird.getJSONArray("colours"));
                } else {
                    colours = new ArrayList<String>();
                }

                List<String> countries;
                if (bird.has("countries")) {
                    countries = mapToStringArray(bird.getJSONArray("countries"));
                } else {
                    countries = new ArrayList<String>();
                }

                animals.add(new Animal(
                        bird.getString("common_name"),
                        bird.getString("official_name"),
                        bird.getString("phylum"),
                        bird.getString("klass"),
                        bird.getString("order"),
                        bird.getString("family"),
                        bird.getString("genus"),
                        bird.getString("species"),
                        bird.getString("subspecies"),
                        ConservationStatus.fromString(bird.getString("conservation_status")),
                        bird.getString("new_filename"),
                        colours,
                        countries,
                        bird.has("endemic") ? bird.getBoolean("endemic") : false
                ));
            }

        } catch (IOException e) {
            throw new RuntimeException("birds.json not found. Doh!.", e);
        } catch (JSONException e) {
            throw new RuntimeException("birds.json is not right or not used right", e);
        }
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

    public static final List<Animal> getAnimals() {
        return animals;
    }
}
