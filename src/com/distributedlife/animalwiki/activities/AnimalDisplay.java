package com.distributedlife.animalwiki.activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.distributedlife.animalwiki.R;
import com.distributedlife.animalwiki.clickaction.RemoveSeenIt;
import com.distributedlife.animalwiki.clickaction.SeenIt;
import com.distributedlife.animalwiki.db.Sightings;
import com.distributedlife.animalwiki.formatting.AnimalFormatting;
import com.distributedlife.animalwiki.loaders.DataLoader;
import com.distributedlife.animalwiki.loaders.ReferenceDataLoader;
import com.distributedlife.animalwiki.model.Animal;

import java.io.IOException;
import java.io.InputStream;

public class AnimalDisplay extends Activity {
    private static Sightings sightings = null;
    private Animal animal;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_display);

        String key = getIntent().getStringExtra("key");

        WebView myWebView = (WebView) findViewById(R.id.webView);
        myWebView.loadUrl("file:///android_asset/html/" + key + ".html");

        TextView commonName = (TextView) findViewById(R.id.commonName);
        TextView officialName = (TextView) findViewById(R.id.officialName);
        TextView conservationStatus = (TextView) findViewById(R.id.conservationStatus);

        TextView kingdom = (TextView) findViewById(R.id.kingdom);
        TextView phylum = (TextView) findViewById(R.id.phylum);
        TextView klass = (TextView) findViewById(R.id.klass);
        TextView order = (TextView) findViewById(R.id.order);
        TextView family = (TextView) findViewById(R.id.family);
        TextView genus = (TextView) findViewById(R.id.genus);

        animal = DataLoader.getAnimalsAsHash().get(key);

        if (animal.getFilename().equals("images/")) {
            ImageView imageView = (ImageView) findViewById(R.id.image);
            imageView.setVisibility(View.GONE);
        } else {
            ImageView imageView = (ImageView) findViewById(R.id.image);

            try {
                InputStream ims = getAssets().open("html/" + animal.getFilename());
                Drawable d = Drawable.createFromStream(ims, null);
                imageView.setImageDrawable(d);
            } catch (IOException e) {
                imageView.setImageResource(R.drawable.ic_launcher);
            }
        }

        conservationStatus.setText(animal.getConservationStatus().toString());
        conservationStatus.setTextColor(AnimalFormatting.getTextColourForConservationStatus(animal.getConservationStatus()));
        conservationStatus.setBackgroundColor(AnimalFormatting.getBackgroundColourForConservationStatus(animal.getConservationStatus()));
        commonName.setText(animal.getCommonName());
        officialName.setText(animal.getOfficialName());


        for (int i = 0; i < animal.getColours().size(); i++) {
            String colour = animal.getColour(i);
            if (colour == null) {
                findViewById(AnimalFormatting.swatches().get(i)).setVisibility(View.GONE);
                continue;
            }

            findViewById(AnimalFormatting.swatches().get(i)).setBackgroundColor(Color.parseColor(colour));
        }
        for (int i = animal.getColours().size(); i < 9; i++) {
            findViewById(AnimalFormatting.swatches().get(i)).setVisibility(View.GONE);
        }


        kingdom.setText(animal.getKingdom());
        phylum.setText(animal.getPhylum());
        klass.setText(animal.getKlass());
        order.setText(animal.getOrder());
        family.setText(animal.getFamily());
        genus.setText(animal.getGenus());

        if (animal.isEndemic()) {
            String country = animal.getCountries().get(0);
            ((TextView) findViewById(R.id.endemic)).setText(String.format("Endemic to %s", ReferenceDataLoader.replaceCountry(country)));
            InputStream ims = null;
            try {
                ims = getAssets().open(String.format("flags/%s.png", country.toLowerCase().replaceAll(" ", "_")));
                Drawable d = Drawable.createFromStream(ims, null);
                ((ImageView) findViewById(R.id.flag)).setImageDrawable(d);
            } catch (IOException e) {
                ((ImageView) findViewById(R.id.flag)).setImageResource(R.drawable.ic_launcher);
            }
        } else {
            findViewById(R.id.endemic).setVisibility(View.INVISIBLE);
        }

        updateAfterSightingChange(animal);
    }

    private void updateAfterSightingChange(Animal animal) {
        if (sightings == null) {
            sightings = new Sightings(this);
        }

        if (sightings.hasSighting(animal)) {
            ((Button) findViewById(R.id.sighting)).setText("Remove Seen It Flag");
            findViewById(R.id.sighting).setOnClickListener(new RemoveSeenIt(this, sightings, animal));
        } else {
            ((Button) findViewById(R.id.sighting)).setText("I've Seen It");
            findViewById(R.id.sighting).setOnClickListener(new SeenIt(this, sightings, animal));
        }
    }

    public void refresh() {
        updateAfterSightingChange(animal);
    }
}