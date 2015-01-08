package com.distributedlife.animalwiki.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import com.distributedlife.animalwiki.R;
import com.distributedlife.animalwiki.db.Sightings;
import com.distributedlife.animalwiki.filters.*;
import com.distributedlife.animalwiki.listAdapters.AnimalsWithOrderAdapter;
import com.distributedlife.animalwiki.listAdapters.FilterAdapter;
import com.distributedlife.animalwiki.loaders.DataLoader;
import com.distributedlife.animalwiki.loaders.ReferenceDataLoader;
import com.distributedlife.animalwiki.model.*;

import java.io.IOException;
import java.util.*;

public class AnimalList extends Activity {
    private final AnimalFilterer animalFilterer = new AnimalFilterer();
    private Sightings sightings;
    private AnimalsWithOrderAdapter animalsAdapter;
    private List<Filter> listOfFilters = new ArrayList<Filter>();
    private DrawerLayout drawer;
    private ExpandableListView drawerFilterList;
    private AnimalCollection unfilteredAnimalCollection;
    private AnimalCollection currentAnimalCollection;
    private Menu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_list);

        sightings = new Sightings(this);
        loadContent();

        for (Sighting sighting : sightings.getAll()) {
            Animal animal = DataLoader.getAnimalsAsHash().get(sighting.getWhat());

            if (animal == null) {
                Log.e("Sightings", "Missing animal in RowDisplayAdapter: " + sighting.getWhat());
                for (Animal candidate : DataLoader.getAnimals()) {
                    if (candidate.getCommonName().toLowerCase().equals(sighting.getWhat())) {
//                        sighting.setWhat(animal.getKey());
//                        sightings.update(sighting);
                        break;
                    }
                }
            }
        }

        unfilteredAnimalCollection = AnimalCollectionBuilder.organiseIntoOrdersAndFamilies(DataLoader.getAnimals());
        currentAnimalCollection = unfilteredAnimalCollection;

        animalsAdapter = new AnimalsWithOrderAdapter(this, currentAnimalCollection.getHeadings(), currentAnimalCollection.getChildren(), sightings, this);
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.place_to_put_list);
        expandableListView.setAdapter(animalsAdapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                                                       @Override
                                                       public boolean onGroupClick(ExpandableListView expandableListView, View view, int position, long l) {
                                                           for (int i = 0; i < currentAnimalCollection.getHeadings().size(); i++) {
                                                               if (expandableListView.isGroupExpanded(i)) {
                                                                   menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_action_expand));
                                                                   return false;
                                                               }
                                                           }

                                                           menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_action_collapse));

                                                           return false;
                                                       }
                                                   }
        );

        SeenFilter seenFilter = new SeenFilter(true, sightings);
        NotSeenFilter notSeenFilter = new NotSeenFilter(true, sightings);

        listOfFilters.add(seenFilter);
        listOfFilters.add(notSeenFilter);


        List<String> headers = new ArrayList<String>();
        headers.add("Sightings");
        headers.add("Conservation Status");
        headers.add("Country");
        headers.add("Class");
        headers.add("Endemic");

        List<Filter> sightingsChildren = new ArrayList<Filter>();
        sightingsChildren.add(seenFilter);
        sightingsChildren.add(notSeenFilter);

        List<Filter> conservationStatusChildren = addConservationStatusFilters(DataLoader.getAnimals());
        listOfFilters.addAll(conservationStatusChildren);

        List<Filter> countryChildren = addCountryFilters(DataLoader.getAnimals());
        listOfFilters.addAll(countryChildren);

        List<Filter> classChildren = addClassFilters(DataLoader.getAnimals());
        listOfFilters.addAll(classChildren);

        List<Filter> endemicChildren = addEndemicFilters();
        listOfFilters.addAll(endemicChildren);

        Map<String, List<Filter>> headersAndChildren = new HashMap<String, List<Filter>>();
        headersAndChildren.put(headers.get(0), sightingsChildren);
        headersAndChildren.put(headers.get(1), conservationStatusChildren);
        headersAndChildren.put(headers.get(2), countryChildren);
        headersAndChildren.put(headers.get(3), classChildren);
        headersAndChildren.put(headers.get(4), endemicChildren);

        drawerFilterList = (ExpandableListView)

                findViewById(R.id.filters);

        drawerFilterList.setAdapter(new

                        FilterAdapter(this, headers, headersAndChildren)
        );


        drawer = (DrawerLayout)

                findViewById(R.id.drawer_layout);

        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
                                     @Override
                                     public void onDrawerSlide(View view, float v) {
                                     }

                                     @Override
                                     public void onDrawerOpened(View view) {
                                     }

                                     @Override
                                     public void onDrawerClosed(View view) {
                                         currentAnimalCollection = animalFilterer.apply(listOfFilters, unfilteredAnimalCollection);

                                         animalsAdapter.setFilter(currentAnimalCollection.getHeadings(), currentAnimalCollection.getChildren());
                                     }

                                     @Override
                                     public void onDrawerStateChanged(int i) {
                                     }
                                 }
        );
    }

    private List<Filter> addEndemicFilters() {
        List<Filter> endemicChildren = new ArrayList<Filter>();

        endemicChildren.add(new ShowEndemicOnlyFilter());

        return endemicChildren;
    }

    private void loadContent() {
        try {
            DataLoader.load(getAssets().open("animals.csv"));
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
        allFilters.add(new ZeroFilter(toggleFilters));
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
        allFilters.add(new ZeroFilter(toggleFilters));
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
        allFilters.add(new ZeroFilter(toggleFilters));
        allFilters.addAll(toggleFilters);

        return allFilters;
    }

    @Override
    public void onResume() {
        super.onResume();
        animalsAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);

        this.menu = menu;

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sightings:
                Intent intent = new Intent(this, SightingsList.class);
                startActivity(intent);

                return true;
            case R.id.expandCollapse:
                ExpandableListView listView = (ExpandableListView) findViewById(R.id.place_to_put_list);
                boolean oneClosed = false;
                for (int i = 0; i < currentAnimalCollection.getHeadings().size(); i++) {
                    if (listView.isGroupExpanded(i)) {
                        listView.collapseGroup(i);
                        oneClosed = true;
                    }
                }

                if (!oneClosed) {
                    for (int i = 0; i < currentAnimalCollection.getHeadings().size(); i++) {
                        listView.expandGroup(i);
                    }
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_action_collapse));
                } else {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_action_expand));
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.place_to_put_list);
        boolean oneClosed = false;
        for (int i = 0; i < currentAnimalCollection.getHeadings().size(); i++) {
            if (listView.isGroupExpanded(i)) {
                listView.collapseGroup(i);
                oneClosed = true;
            }
        }

        if (oneClosed) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_action_expand));
        } else {
            finish();
        }
    }
}