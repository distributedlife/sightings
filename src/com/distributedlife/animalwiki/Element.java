package com.distributedlife.animalwiki;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class Element extends Activity {
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.element);

        String name = getIntent().getStringExtra("name");

        WebView myWebView = (WebView) findViewById(R.id.webView);
        System.err.println(name);
        myWebView.loadUrl("file:///android_asset/html/" + name);

        TextView commonName = (TextView) findViewById(R.id.commonName);
        TextView officialName = (TextView) findViewById(R.id.officialName);
        TextView conservationStatus = (TextView) findViewById(R.id.conservationStatus);

        TextView kingdom = (TextView) findViewById(R.id.kingdom);
        TextView phylum = (TextView) findViewById(R.id.phylum);
        TextView klass = (TextView) findViewById(R.id.klass);
        TextView order = (TextView) findViewById(R.id.order);
        TextView family = (TextView) findViewById(R.id.family);
        TextView genus = (TextView) findViewById(R.id.genus);
        TextView species = (TextView) findViewById(R.id.species);
        TextView subspecies = (TextView) findViewById(R.id.subspecies);
        TableRow subspeciesRow = (TableRow) findViewById(R.id.subspeciesRow);

        try {
            JSONArray birds = getBirds();
            for (int i = 0; i < birds.length(); i++) {
                JSONObject bird = birds.getJSONObject(i);

                if (bird.getString("common_name").toLowerCase().equals(transformFilenameToCommonName(name).toLowerCase())) {
                    if (bird.getString("new_filename").equals("images/")) {
                        ImageView imageView = (ImageView) findViewById(R.id.image);
                        imageView.setVisibility(View.GONE);
                    } else {
                        ImageView imageView = (ImageView) findViewById(R.id.image);

                        InputStream ims = getAssets().open("html/" + bird.getString("new_filename"));
                        Drawable d = Drawable.createFromStream(ims, null);
                        imageView.setImageDrawable(d);
                    }

                    commonName.setText(bird.getString("common_name"));
                    officialName.setText(bird.getString("official_name"));
                    conservationStatus.setText(bird.getString("conservation_status"));

                    kingdom.setText(bird.getString("kingdom"));
                    phylum.setText(bird.getString("phylum"));
                    klass.setText(bird.getString("klass"));
                    order.setText(bird.getString("order"));
                    family.setText(bird.getString("family"));
                    genus.setText(bird.getString("genus"));
                    species.setText(bird.getString("species"));
                    subspecies.setText(bird.getString("subspecies"));

                    if (bird.getString("subspecies").isEmpty()) {
                        subspeciesRow.setVisibility(View.GONE);
                    }
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException("birds.json not found. Doh!.", e);
        } catch (IOException e) {
            throw new RuntimeException("birds.json not found. Doh!.", e);
        }
    }

    private String transformFilenameToCommonName(String name) {
        return name.replaceAll("_", " ").replaceAll(".html", "");
    }
}