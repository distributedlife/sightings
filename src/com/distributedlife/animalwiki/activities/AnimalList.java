package com.distributedlife.animalwiki.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import com.distributedlife.animalwiki.R;
import com.distributedlife.animalwiki.Sightings;
import com.distributedlife.animalwiki.listAdapters.AnimalsAdapter;
import com.distributedlife.animalwiki.loaders.DataLoader;
import com.distributedlife.animalwiki.loaders.ReferenceDataLoader;
import com.distributedlife.animalwiki.model.Animal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnimalList extends Activity {
    TextView listCount;
    private Sightings sightings;
    private AnimalsAdapter animalsAdapter;
    private String text;
    private boolean seenOnly;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_list);

        sightings = new Sightings(this);
        seenOnly = false;
        text = "";

        setupSearchControl(this);

        try {
            DataLoader.load(getAssets().open("birds.json"));
            DataLoader.add(getAssets().open("mammals.json"));
            ReferenceDataLoader.load(getAssets().open("reference.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Animal> animals = new ArrayList<Animal>(DataLoader.getAnimals());
        ((ListView) findViewById(R.id.place_to_put_list)).setAdapter(new AnimalsAdapter(this, animals, sightings, this));
        animalsAdapter = (AnimalsAdapter) ((ListView) findViewById(R.id.place_to_put_list)).getAdapter();

        listCount = (TextView) findViewById(R.id.listTotal);
        listCount.setText(Integer.toString(DataLoader.getAnimals().size()));

        Switch filterBySeen = (Switch) findViewById(R.id.seenFilter);
        filterBySeen.setOnCheckedChangeListener(new FilterBySeen());
    }

    @Override
    public void onResume() {
        super.onResume();

        animalsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        sightings.close();
    }

    private void setupSearchControl(final Activity parent) {
        EditText search = (EditText) findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                text = String.valueOf(editable);
                List<Animal> filteredAnimals = filter(text, seenOnly);

                animalsAdapter.setFilter(filteredAnimals);
                listCount.setText(Integer.toString(animalsAdapter.getCount()));
            }
        });
    }

    private List<Animal> filter(String text, boolean seenOnly) {
        List<Animal> results = new ArrayList<Animal>();

        for(Animal animal: DataLoader.getAnimals()) {
            if (animal.getCommonName().contains(text.toLowerCase())) {
                if (seenOnly) {
                    if (sightings.hasSighting(animal.getCommonName().toLowerCase())) {
                        results.add(animal);
                    }
                } else {
                    results.add(animal);
                }
            }
        }

        return results;
    }

    private class FilterBySeen implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
            seenOnly = checked;

            animalsAdapter.setFilter(filter(text, seenOnly));
            listCount.setText(Integer.toString(animalsAdapter.getCount()));
        }
    }
}
