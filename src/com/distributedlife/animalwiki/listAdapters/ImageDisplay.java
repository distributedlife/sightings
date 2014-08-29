package com.distributedlife.animalwiki.listAdapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.distributedlife.animalwiki.R;
import com.distributedlife.animalwiki.model.Animal;

import java.io.IOException;
import java.io.InputStream;

public class ImageDisplay {
    public void display(Animal animal, ImageView imageView, Activity owner) {
        if (animal.hasImage()) {
            try {
                InputStream ims = owner.getAssets().open("html/" + animal.getFilename());
                Drawable d = Drawable.createFromStream(ims, null);
                imageView.setImageDrawable(d);
            } catch (IOException e) {
                imageView.setImageResource(R.drawable.ic_launcher);
            }
        }
    }
}