package com.distributedlife.animalwiki.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ExpandableListView;
import com.distributedlife.animalwiki.R;
import com.distributedlife.animalwiki.db.Sightings;
import com.distributedlife.animalwiki.filters.*;
import com.distributedlife.animalwiki.listAdapters.AnimalsWithOrderAdapter;
import com.distributedlife.animalwiki.listAdapters.FilterAdapter;
import com.distributedlife.animalwiki.loaders.AnimalCommonNameComparator;
import com.distributedlife.animalwiki.loaders.DataLoader;
import com.distributedlife.animalwiki.loaders.ReferenceDataLoader;
import com.distributedlife.animalwiki.model.Animal;
import com.distributedlife.animalwiki.model.ConservationStatus;

import java.io.IOException;
import java.util.*;

public class AnimalList extends Activity {
    public static final String SEEN = "Seen";
    public static final String UNSEEN = "Unseen";
    public static final String CLASS = "Class";
    private final FilterApplication filterApplication = new FilterApplication();
    private Sightings sightings;
    private AnimalsWithOrderAdapter animalsAdapter;
    private List<Filter> listOfFilters = new ArrayList<Filter>();
    private DrawerLayout drawer;
    private ExpandableListView drawerFilterList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_list);

        sightings = new Sightings(this);
        loadContent();

        List<String> orders = new ArrayList<String>();
        Map<String, List<Animal>> allAnimals = new HashMap<String, List<Animal>>();

        for (Animal animal : DataLoader.getAnimals()) {
            String order = ReferenceDataLoader.replaceOrder(animal.getOrder());

            if (!orders.contains(order)) {
                orders.add(order);
            }

            List<Animal> orderAnimals;
            if (allAnimals.containsKey(order)) {
                orderAnimals = allAnimals.get(order);
            } else {
                orderAnimals = new ArrayList<Animal>();
            }

            orderAnimals.add(animal);
            Collections.sort(orderAnimals, new AnimalCommonNameComparator());
            allAnimals.put(order, orderAnimals);
        }
        Collections.sort(orders);








        ((ExpandableListView) findViewById(R.id.place_to_put_list)).setAdapter(new AnimalsWithOrderAdapter(this, orders, allAnimals, sightings, this));
//        animalsAdapter = (AnimalsWithOrderAdapter) ((ExpandableListView) findViewById(R.id.place_to_put_list)).getAdapter();

        SeenFilter seenFilter = new SeenFilter(true, sightings);
        NotSeenFilter notSeenFilter = new NotSeenFilter(true, sightings);

        listOfFilters.add(seenFilter);
        listOfFilters.add(notSeenFilter);

        List<String> headers = new ArrayList<String>();
        headers.add("Sightings");
        headers.add("Conservation Status");
        headers.add("Country");
        headers.add("Class");
//        headers.add("Order");

        List<Filter> sightingsChildren = new ArrayList<Filter>();
        sightingsChildren.add(seenFilter);
        sightingsChildren.add(notSeenFilter);

        List<Filter> conservationStatusChildren = addConservationStatusFilters(DataLoader.getAnimals());
        listOfFilters.addAll(conservationStatusChildren);

        List<Filter> countryChildren = addCountryFilters(DataLoader.getAnimals());
        listOfFilters.addAll(countryChildren);

        List<Filter> classChildren = addClassFilters(DataLoader.getAnimals());
        listOfFilters.addAll(classChildren);

//        List<Filter> orderChildren = addOrderFilters(DataLoader.getAnimals());
//        listOfFilters.addAll(orderChildren);

        Map<String, List<Filter>> headersAndChildren = new HashMap<String, List<Filter>>();
        headersAndChildren.put(headers.get(0), sightingsChildren);
        headersAndChildren.put(headers.get(1), conservationStatusChildren);
        headersAndChildren.put(headers.get(2), countryChildren);
        headersAndChildren.put(headers.get(3), classChildren);
//        headersAndChildren.put(headers.get(4), orderChildren);

        drawerFilterList = (ExpandableListView) findViewById(R.id.filters);
        drawerFilterList.setAdapter(new FilterAdapter(this, headers, headersAndChildren));


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {}

            @Override
            public void onDrawerOpened(View view) {}

            @Override
            public void onDrawerClosed(View view) {
//                animalsAdapter.setFilter(filterApplication.apply(listOfFilters, DataLoader.getAnimals()));
            }

            @Override
            public void onDrawerStateChanged(int i) {}
        });
    }

    private void loadContent() {
        try {
            DataLoader.load(getAssets().open("animals.json"));
            ReferenceDataLoader.load(getAssets().open("reference.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Filter> addCountryFilters(List<Animal> animals) {
        List<String> unsortedItems = new ArrayList<String>();
        for (Animal animal : animals) {
            for (String country : animal.getCountries()) {
                if (unsortedItems.contains(country)) {
                    continue;
                }

                unsortedItems.add(country);
            }
        }

        Collections.sort(unsortedItems);

        List<ToggleFilter> toggleFilters = new ArrayList<ToggleFilter>();
        for (String country : unsortedItems) {
            toggleFilters.add(new CountryFilter(country, true));
        }

        List<Filter> allFilters = new ArrayList<Filter>();
//        allFilters.add(new ZeroFilter(toggleFilters));
        allFilters.addAll(toggleFilters);

        return allFilters;
    }

    private List<Filter> addConservationStatusFilters(List<Animal> animals) {
        List<ConservationStatus> unsortedItems = new ArrayList<ConservationStatus>();
        for (Animal animal : animals) {
            if (unsortedItems.contains(animal.getConservationStatus())) {
                continue;
            }

            unsortedItems.add(animal.getConservationStatus());
        }

        Collections.sort(unsortedItems);

        List<ToggleFilter> toggleFilters = new ArrayList<ToggleFilter>();
        for (ConservationStatus conservationStatus : unsortedItems) {
            toggleFilters.add(new ConservationStatusFilter(conservationStatus, true));
        }

        List<Filter> allFilters = new ArrayList<Filter>();
//        allFilters.add(new ZeroFilter(toggleFilters));
        allFilters.addAll(toggleFilters);

        return allFilters;
    }

    private List<Filter> addClassFilters(List<Animal> animals) {
        List<String> unsortedItems = new ArrayList<String>();
        for (Animal animal : animals) {
            if (unsortedItems.contains(animal.getKlass())) {
                continue;
            }

            unsortedItems.add(animal.getKlass());
        }

        Collections.sort(unsortedItems);

        List<ToggleFilter> toggleFilters = new ArrayList<ToggleFilter>();
        for (String klass : unsortedItems) {
            toggleFilters.add(new ClassFilter(klass, true));
        }

        List<Filter> allFilters = new ArrayList<Filter>();
//        allFilters.add(new ZeroFilter(toggleFilters));
        allFilters.addAll(toggleFilters);

        return allFilters;
    }

    @Override
    public void onResume() {
        super.onResume();

//        animalsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        sightings.close();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (drawer == null) {
                return super.onKeyDown(keyCode, e);
            }

            if (drawer.isDrawerOpen(drawerFilterList)) {
                drawer.closeDrawer(drawerFilterList);
            } else {
                drawer.openDrawer(drawerFilterList);
            }

            return true;
        }

        return super.onKeyDown(keyCode, e);
    }
}