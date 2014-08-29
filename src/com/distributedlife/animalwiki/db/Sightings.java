package com.distributedlife.animalwiki.db;

import android.content.Context;
import android.util.Log;
import com.couchbase.lite.*;
import com.couchbase.lite.android.AndroidContext;
import com.distributedlife.animalwiki.model.Animal;
import com.distributedlife.animalwiki.model.Sighting;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sightings {
    private static final String TAG = "Sightings";

    private Manager manager;
    private Database database;
    private List<Sighting> sightingCache;
    public static final String NAME = "sightings";

    public static final String PATTERN = "YYYY-MM-dd HH:mm:ss.SSSZ";

    public Sightings(Context context) {
        sightingCache = new ArrayList<Sighting>();

        if (setupDatabase(context)) return;

        refreshSightingsCache();
    }

    private boolean setupDatabase(Context context) {
        try {
            manager = new Manager(new AndroidContext(context), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            Log.e(TAG, "Cannot create manager object");
            return true;
        }

        if (!Manager.isValidDatabaseName(NAME)) {
            Log.e(TAG, "Bad database name");
            return true;
        }

        try {
            database = manager.getDatabase(NAME);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Cannot get database");
            return true;
        }
        return false;
    }

    private void refreshSightingsCache() {
        try {
            Map<String, Object> retrievedDocument = database.getAllDocs(new QueryOptions());
            List<QueryRow> queryRows = (List<QueryRow>) retrievedDocument.get("rows");
            for (QueryRow queryRow: queryRows) {
                sightingCache.add(convertMapToSighting(queryRow));
            }
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private Sighting convertMapToSighting(QueryRow queryRow) {
        Document document = queryRow.getDocument();
        Map<String, Object> properties = document.getProperties();

        String id = document.getId();
        String what = (String) properties.get("what");

        DateTime when;
        if (properties.containsKey("when")) {
            DateTimeParser parser = DateTimeFormat.forPattern(PATTERN).getParser();
            DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(parser).toFormatter();

            when = DateTime.parse((String) properties.get("when"), formatter);
        } else {
            when = DateTime.now().minusDays(10);
        }

        return new Sighting(id, what, when);
    }

    public void add(Animal animal) {
        Sighting sighting = new Sighting(animal.getKey());
        Map<String, Object> docContent = convertSightingToMap(sighting, animal);

        Document document = database.createDocument();

        try {
            document.putProperties(docContent);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Cannot write document to database", e);
            return;
        }

        sighting.setId(document.getId());
        sightingCache.add(sighting);
    }

    public Sighting getSighting(String key) {
        for (Sighting sighting : sightingCache) {
            if (sighting.getWhat().equals(key)) {
                return sighting;
            }
        }

        return null;
    }

    public boolean hasSighting(Animal animal) {
        return (getSighting(animal.getKey()) != null);
    }

    public void remove(Animal animal) {
        remove(getSighting(animal.getKey()));
    }

    public List<Sighting> getAll() {
        return new ArrayList<Sighting>(sightingCache);
    }

    private Map<String, Object> convertSightingToMap(Sighting sighting, Animal animal) {
        DateTimeParser parser = DateTimeFormat.forPattern(PATTERN).getParser();
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(parser).toFormatter();

        Map<String, Object> docContent = new HashMap<String, Object>();
        docContent.put("what", animal.getKey());
        Log.d(TAG, sighting.getWhen().toString(formatter));
        docContent.put("when", sighting.getWhen().toString(formatter));

        return docContent;
    }

    public void remove(Sighting sighting) {
        try {
            database.getDocument(sighting.getId()).delete();
            sightingCache.remove(sighting);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
