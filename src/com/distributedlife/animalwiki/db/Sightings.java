package com.distributedlife.animalwiki.db;

import android.content.Context;
import android.util.Log;
import com.couchbase.lite.*;
import com.couchbase.lite.android.AndroidContext;
import com.distributedlife.animalwiki.model.Sighting;

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

    public Sightings(Context context) {
        sightingCache = new ArrayList<Sighting>();

        try {
            manager = new Manager(new AndroidContext(context), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            Log.e(TAG, "Cannot create manager object");
            return;
        }

        if (!Manager.isValidDatabaseName(NAME)) {
            Log.e(TAG, "Bad database name");
            return;
        }

        try {
            database = manager.getDatabase(NAME);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Cannot get database");
            return;
        }

        try {
            Map<String, Object> retrievedDocument = database.getAllDocs(new QueryOptions());
            List<QueryRow> queryRows = (List<QueryRow>) retrievedDocument.get("rows");
            for (QueryRow queryRow: queryRows) {
                Document document = queryRow.getDocument();
                Map<String, Object> properties = document.getProperties();

                String id = document.getId();
                String what = (String) properties.get("what");

                sightingCache.add(new Sighting(id, what));
            }
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void add(String what) {
        Sighting sighting = new Sighting(what);
        Map<String, Object> docContent = convertSightingToMap(sighting);

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

    private Map<String, Object> convertSightingToMap(Sighting sighting) {
        Map<String, Object> docContent = new HashMap<String, Object>();
        docContent.put("what", sighting.getWhat());

        return docContent;
    }

    private String tidy(String text) {
        return text.replaceAll("'", "%27").toLowerCase();
    }

    public Sighting getSighting(String what) {
        for (Sighting sighting : sightingCache) {
            if (sighting.getWhat().equals(tidy(what))) {
                return sighting;
            }
        }

        return null;
    }

    public boolean hasSighting(String what) {
        return (getSighting(what) != null);
    }

    public void remove(String what) {
        try {
            Sighting sighting = getSighting(what);
            database.getDocument(sighting.getId()).delete();
            sightingCache.remove(sighting);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, e.getMessage());
        }

    }
}
