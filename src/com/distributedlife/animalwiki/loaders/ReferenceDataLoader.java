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
    static java.util.Map<String, String> countries;
    static java.util.Map<String, String> orders;
    static java.util.Map<String, String> families;

    public static void load(InputStream jsonFile) {
        try {
            JSONObject root = new JSONObject(IOUtils.toString(jsonFile));

            klasses = new HashMap<String, String>();
            JSONObject klass = root.getJSONObject("klass");
            Iterator klassItr = klass.keys();
            while(klassItr.hasNext()) {
                String key = (String) klassItr.next();
                klasses.put(key, klass.getString(key));
            }

            orders = new HashMap<String, String>();
            JSONObject order = root.getJSONObject("order");
            Iterator orderItr = order.keys();
            while(orderItr.hasNext()) {
                String key = (String) orderItr.next();
                orders.put(key, order.getString(key));
            }

            families = new HashMap<String, String>();
            JSONObject families = root.getJSONObject("family");
            Iterator familiesItr = families.keys();
            while(familiesItr.hasNext()) {
                String key = (String) familiesItr.next();
                families.put(key, families.getString(key));
            }

            countries = new HashMap<String, String>();
            JSONObject country = root.getJSONObject("countries");
            Iterator countryItr = country.keys();
            while(countryItr.hasNext()) {
                String key = (String) countryItr.next();
                countries.put(key, country.getString(key));
            }
        } catch (IOException e) {
            throw new RuntimeException("birds.json not found. Doh!.", e);
        } catch (JSONException e) {
            throw new RuntimeException("birds.json is not right or not used right", e);
        }
    }

    public static String replaceKlass(String klass) {
        return klasses.get(klass);
    }

    public static String replaceCountry(String country) {
        return countries.get(country);
    }

    public static String replaceOrder(String order) {
        if (order.isEmpty()) {
            return "Unknown";
        }

        if (orders.containsKey(order)) {
            return orders.get(order);
        } else {
            return order;
        }
    }

    public static String replaceFamily(String family) {
        if (family.isEmpty()) {
            return "Unknown";
        }

        if (families.containsKey(family)) {
            return families.get(family);
        } else {
            return family;
        }
    }
}
