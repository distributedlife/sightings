package com.distributedlife.animalwiki.partials;

import android.app.Activity;
import android.content.Context;
import com.distributedlife.animalwiki.factories.RowDisplayFactory;
import com.distributedlife.animalwiki.listAdapters.ImageDisplay;
import com.distributedlife.animalwiki.model.Animal;

import java.util.List;
import java.util.Map;

public class SightingsRowDisplayFactory implements RowDisplayFactory {
    public static final Integer HEADER = 0;
    public static final int ANIMAL = 1;
    private List<Integer> types;
    private SightingsHeaderRowDisplay sightingsHeaderRowDisplay;
    private AnimalRowDisplay animalRowDisplay;

    public SightingsRowDisplayFactory(List<Integer> types, Map<String, Animal> animals, Activity owner, Context context) {
        this.types = types;
        sightingsHeaderRowDisplay = new SightingsHeaderRowDisplay(context);

        animalRowDisplay = new AnimalRowDisplay(new ImageDisplay(), animals, context, owner);
    }

    @Override
    public RowDisplay build(int position) {
        if (types.get(position).equals(HEADER)) {
            return sightingsHeaderRowDisplay;
        } else {
            return animalRowDisplay;
        }
    }

    @Override
    public int getTypeCount() {
        return 2;
    }
}
