package com.distributedlife.animalwiki.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ListView;
import com.distributedlife.animalwiki.R;
import com.distributedlife.animalwiki.db.Sightings;
import com.distributedlife.animalwiki.filters.*;
import com.distributedlife.animalwiki.listAdapters.AnimalsAdapter;
import com.distributedlife.animalwiki.listAdapters.FilterAdapter;
import com.distributedlife.animalwiki.loaders.DataLoader;
import com.distributedlife.animalwiki.loaders.ReferenceDataLoader;
import com.distributedlife.animalwiki.model.Animal;
import com.distributedlife.animalwiki.model.ConservationStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnimalList extends Activity {
    public static final String SEEN = "Seen";
    public static final String UNSEEN = "Unseen";
    public static final String CLASS = "Class";
    private final FilterApplication filterApplication = new FilterApplication();
    private Sightings sightings;
    private AnimalsAdapter animalsAdapter;
    private List<Filter> listOfFilters = new ArrayList<Filter>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_list);

        sightings = new Sightings(this);
        loadContent();
        buildFilterList(DataLoader.getAnimals());


        ((ListView) findViewById(R.id.place_to_put_list)).setAdapter(new AnimalsAdapter(this, filterApplication.apply(listOfFilters, DataLoader.getAnimals()), sightings, this));
        animalsAdapter = (AnimalsAdapter) ((ListView) findViewById(R.id.place_to_put_list)).getAdapter();


        ((ListView) findViewById(R.id.filters)).setAdapter(new FilterAdapter(this, listOfFilters, this));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {}

            @Override
            public void onDrawerOpened(View view) {}

            @Override
            public void onDrawerClosed(View view) {
                animalsAdapter.setFilter(filterApplication.apply(listOfFilters, DataLoader.getAnimals()));
            }

            @Override
            public void onDrawerStateChanged(int i) {}
        });
    }

    private void loadContent() {
        try {
            DataLoader.load(getAssets().open("birds.json"));
            DataLoader.add(getAssets().open("mammals.json"));
            ReferenceDataLoader.load(getAssets().open("reference.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void buildFilterList(List<Animal> animals) {
        listOfFilters.add(new Heading("Sightings"));
        listOfFilters.add(new SeenFilter(true, sightings));
        listOfFilters.add(new NotSeenFilter(true, sightings));

        listOfFilters.add(new Heading("Class"));
        addClassFilters(animals);

        listOfFilters.add(new Heading("Conservation Status"));
        addConservationStatusFilters(animals);

        listOfFilters.add(new Heading("Country"));
        addCountryFilters(animals);

        listOfFilters.add(new Heading("Order"));
        addOrderFilters(animals);
    }

    private void addCountryFilters(List<Animal> animals) {
        List<String> done = new ArrayList<String>();
        for(Animal animal : animals) {
            for (String country : animal.getCountries()) {
                if (done.contains(country)) {
                    continue;
                }

                done.add(country);

                listOfFilters.add(new CountryFilter(country, true));
            }
        }
    }

    private void addConservationStatusFilters(List<Animal> animals) {
        List<ConservationStatus> done = new ArrayList<ConservationStatus>();
        for(Animal animal : animals) {
            if (done.contains(animal.getConservationStatus())) {
                continue;
            }

            done.add(animal.getConservationStatus());
            listOfFilters.add(new ConservationStatusFilter(animal.getConservationStatus(), true));
        }
    }

    private void addOrderFilters(List<Animal> animals) {
        List<String> done = new ArrayList<String>();
        for(Animal animal : animals) {
            if (done.contains(animal.getOrder())) {
                continue;
            }

            done.add(animal.getOrder());
            listOfFilters.add(new OrderFilter(animal.getOrder(), true));
        }
    }

    private void addClassFilters(List<Animal> animals) {
        List<String> done = new ArrayList<String>();
        for(Animal animal : animals) {
            if (done.contains(animal.getKlass())) {
                continue;
            }

            done.add(animal.getKlass());
            listOfFilters.add(new ClassFilter(animal.getKlass(), true));
        }
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
}
