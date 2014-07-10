package com.distributedlife.animalwiki.loaders;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

public class ReferenceDataLoader {
    static java.util.Map<String, String> klasses;
    static java.util.Map<String, String> orders;

    public static void load(InputStream jsonFile) {
        klasses = new HashMap<String, String>();
        orders = new HashMap<String, String>();

        try {
            JSONObject root = new JSONObject(IOUtils.toString(jsonFile));

            JSONObject klass = root.getJSONObject("klass");
            Iterator klassItr = klass.keys();
            while(klassItr.hasNext()) {
                String key = (String) klassItr.next();
                klasses.put(key, klass.getString(key));
            }

            JSONObject order = root.getJSONObject("order");
            Iterator orderItr = order.keys();
            while(orderItr.hasNext()) {
                String key = (String) orderItr.next();
                orders.put(key, order.getString(key));
            }
        } catch (IOException e) {
            throw new RuntimeException("birds.json not found. Doh!.", e);
        } catch (JSONException e) {
            throw new RuntimeException("birds.json is not right or not used right", e);
        }
    }
}
