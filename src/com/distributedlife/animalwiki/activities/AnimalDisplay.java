package com.distributedlife.animalwiki.activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import com.distributedlife.animalwiki.R;
import com.distributedlife.animalwiki.db.Sightings;
import com.distributedlife.animalwiki.clickaction.RemoveSeenIt;
import com.distributedlife.animalwiki.clickaction.SeenIt;
import com.distributedlife.animalwiki.formatting.AnimalFormatting;
import com.distributedlife.animalwiki.loaders.DataLoader;
import com.distributedlife.animalwiki.model.Animal;

import java.io.IOException;
import java.io.InputStream;

public class AnimalDisplay extends Activity {
    private String commonNameFromFileName;
    private static Sightings sightings = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_display);

        String name = getIntent().getStringExtra("name");
        commonNameFromFileName = transformFilenameToCommonName(name);

        WebView myWebView = (WebView) findViewById(R.id.webView);
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
        TableRow speciesRow = (TableRow) findViewById(R.id.speciesRow);

        for(Animal animal: DataLoader.getAnimals()) {
            if (animal.getCommonName().toLowerCase().equals(commonNameFromFileName.toLowerCase())) {
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
                species.setText(animal.getSpecies());

                if (animal.getSubspecies().isEmpty()) {
                    speciesRow.setVisibility(View.GONE);
                }
            }
        }

        updateAfterSightingChange(commonNameFromFileName);
    }

    private void updateAfterSightingChange(String what) {
        if (sightings == null) {
            sightings = new Sightings(this);
        }

        if (sightings.hasSighting(what)) {
            ((Button) findViewById(R.id.sighting)).setText("Remove Seen It Flag");
            findViewById(R.id.sighting).setOnClickListener(new RemoveSeenIt(this, sightings, what));
        } else {
            ((Button) findViewById(R.id.sighting)).setText("I've Seen It");
            findViewById(R.id.sighting).setOnClickListener(new SeenIt(this, sightings, what));
        }
    }

    private String transformFilenameToCommonName(String name) {
        return name.replaceAll("_", " ").replaceAll(".html", "");
    }

    public void refresh() {
        updateAfterSightingChange(commonNameFromFileName);
    }
}