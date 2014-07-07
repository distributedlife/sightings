package com.distributedlife.animalwiki;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class List extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        setupSearchControl(this);

        java.util.List<String> files = new ArrayList<String>();
        try {
            Collections.addAll(files, getAssets().list("html"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ((ListView) findViewById(R.id.place_to_put_list)).setAdapter(new PagesAdapter(this, files, this));
    }

    public JSONArray getBirds() {
        JSONObject root;
        try {
            root = new JSONObject(IOUtils.toString(getAssets().open("birds.json")));
            return root.getJSONArray("birds");
        } catch (IOException e) {
            throw new RuntimeException("birds.json not found. Doh!.", e);
        } catch (JSONException e) {
            throw new RuntimeException("birds.json not found. Doh!.", e);
        }
    }

    private void setupSearchControl(final Activity parent) {
        EditText search = (EditText) findViewById(R.id.search);
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                ((ListView) findViewById(R.id.place_to_put_list)).setAdapter(
                        new PagesAdapter(parent, filterWords(String.valueOf(((TextView) view).getText())), parent)
                );

                return false;
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ((ListView) findViewById(R.id.place_to_put_list)).setAdapter(
                        new PagesAdapter(parent, filterWords(String.valueOf(charSequence)), parent)
                );
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private java.util.List<String> filterWords(String text) {
        java.util.List<String> results = new ArrayList<String>();

        try {
            JSONArray birds = getBirds();
            for (int i = 0; i < birds.length(); i++) {
                JSONObject bird = birds.getJSONObject(i);

                if (bird.getString("common_name").contains(text)) {
                    results.add(bird.getString("common_name").replaceAll(" ", "_").toLowerCase()+".html");
                    continue;
                }
                if (bird.getString("official_name").contains(text)) {
                    results.add(bird.getString("common_name").replaceAll("_", " ").toLowerCase()+".html");
                    continue;
                }
                if (bird.getString("conservation_status").contains(text)) {
                    results.add(bird.getString("common_name").replaceAll("_", " ").toLowerCase()+".html");
                    continue;
                }
                if (bird.getString("kingdom").contains(text)) {
                    results.add(bird.getString("common_name").replaceAll("_", " ").toLowerCase()+".html");
                    continue;
                }
                if (bird.getString("phylum").contains(text)) {
                    results.add(bird.getString("common_name").replaceAll("_", " ").toLowerCase()+".html");
                    continue;
                }
                if (bird.getString("klass").contains(text)) {
                    results.add(bird.getString("common_name").replaceAll("_", " ").toLowerCase()+".html");
                    continue;
                }
                if (bird.getString("order").contains(text)) {
                    results.add(bird.getString("common_name").replaceAll("_", " ").toLowerCase()+".html");
                    continue;
                }
                if (bird.getString("family").contains(text)) {
                    results.add(bird.getString("common_name").replaceAll("_", " ").toLowerCase()+".html");
                    continue;
                }
                if (bird.getString("genus").contains(text)) {
                    results.add(bird.getString("common_name").replaceAll("_", " ").toLowerCase()+".html");
                    continue;
                }
                if (bird.getString("species").contains(text)) {
                    results.add(bird.getString("common_name").replaceAll("_", " ").toLowerCase()+".html");
                    continue;
                }
                if (bird.getString("subspecies").contains(text)) {
                    results.add(bird.getString("common_name").replaceAll("_", " ").toLowerCase()+".html");
                    continue;
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException("birds.json not found. Doh!.", e);
        }

        return results;
    }
}
