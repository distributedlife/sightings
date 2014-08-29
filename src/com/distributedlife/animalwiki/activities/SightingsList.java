package com.distributedlife.animalwiki.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.distributedlife.animalwiki.R;
import com.distributedlife.animalwiki.db.Sightings;
import com.distributedlife.animalwiki.formatting.SightingsFormatter;
import com.distributedlife.animalwiki.listAdapters.RowDisplayAdapter;
import com.distributedlife.animalwiki.loaders.DataLoader;
import com.distributedlife.animalwiki.model.Sighting;
import com.distributedlife.animalwiki.partials.SightingsRowDisplayFactory;
import com.distributedlife.animalwiki.sorting.SightingsRecentToOldSorter;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SightingsList extends Activity {
    private final SightingsFormatter sightingsFormatter = new SightingsFormatter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sightings_list);

        Sightings sightings = new Sightings(this);
        List<Sighting> allSightings = sightings.getAll();

        List<Object> rows = getRowsForSightingsList(allSightings);
        List<Integer> types = getTypesForSightingsRows(allSightings);

        SightingsRowDisplayFactory sightingsRowDisplayFactory = new SightingsRowDisplayFactory(types, DataLoader.getAnimalsAsHash(), this, this);
        RowDisplayAdapter rowDisplayAdapter = new RowDisplayAdapter(this, rows, types, sightingsRowDisplayFactory);
        ((ListView) findViewById(R.id.place_to_put_list)).setAdapter(rowDisplayAdapter);
    }

    private List<Integer> getTypesForSightingsRows(List<Sighting> allSightings) {
        List<Integer> rows = new ArrayList<Integer>();
        Collections.sort(allSightings, new SightingsRecentToOldSorter());

        DateTime currentDate = null;
        for (Sighting sighting : allSightings) {
            if (currentDate == null) {
                currentDate = removeTimePart(sighting.getWhen());
                rows.add(SightingsRowDisplayFactory.HEADER);
            } else {
                if (currentDate.isAfter(removeTimePart(sighting.getWhen()))) {
                    currentDate = removeTimePart(sighting.getWhen());
                    rows.add(SightingsRowDisplayFactory.HEADER);
                }
            }

            rows.add(SightingsRowDisplayFactory.ANIMAL);
        }

        return rows;
    }

    private List<Object> getRowsForSightingsList(List<Sighting> allSightings) {
        List<Object> rows = new ArrayList<Object>();
        Collections.sort(allSightings, new SightingsRecentToOldSorter());

        DateTime currentDate = null;
        for (Sighting sighting : allSightings) {
            if (currentDate == null) {
                currentDate = removeTimePart(sighting.getWhen());

                rows.add(sightingsFormatter.getDisplayString(currentDate));
            } else {
                if (currentDate.isAfter(removeTimePart(sighting.getWhen()))) {
                    currentDate = removeTimePart(sighting.getWhen());
                    rows.add(sightingsFormatter.getDisplayString(currentDate));
                }
            }

            rows.add(sighting);
        }
        return rows;
    }

    private DateTime removeTimePart(DateTime dateTime) {
        return dateTime.minusMillis(dateTime.getMillisOfSecond())
                .minusSeconds(dateTime.getSecondOfMinute())
                .minusMinutes(dateTime.getMinuteOfHour())
                .minusHours(dateTime.getHourOfDay());
    }
}
