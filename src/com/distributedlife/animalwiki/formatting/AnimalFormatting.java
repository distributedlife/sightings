package com.distributedlife.animalwiki.formatting;

import android.graphics.Color;
import com.distributedlife.animalwiki.R;
import com.distributedlife.animalwiki.model.ConservationStatus;

import java.util.HashMap;
import java.util.Map;

public class AnimalFormatting {
    private static Map<Integer, Integer> swatchLookup = new HashMap<Integer, Integer>();

    public static Map<Integer, Integer> swatches() {
        if (swatchLookup.size() > 0) {
            return swatchLookup;
        }

        swatchLookup.put(0, R.id.swatch1);
        swatchLookup.put(1, R.id.swatch2);
        swatchLookup.put(2, R.id.swatch3);
        swatchLookup.put(3, R.id.swatch4);
        swatchLookup.put(4, R.id.swatch5);
        swatchLookup.put(5, R.id.swatch6);
        swatchLookup.put(6, R.id.swatch7);
        swatchLookup.put(7, R.id.swatch8);
        swatchLookup.put(8, R.id.swatch9);

        return swatchLookup;
    }
    public static int getTextColourForConservationStatus(ConservationStatus conservationStatus) {
        switch (conservationStatus) {
            case Extinct:
                return Color.parseColor("black");
            case Vulnerable:
                return Color.parseColor("black");
            case NearThreatened:
                return Color.parseColor("black");
            case DataDeficient:
                return Color.parseColor("black");
            case NotEvaluated:
                return Color.parseColor("black");
            default:
                return Color.parseColor("white");

        }
    }

    public static int getBackgroundColourForConservationStatus(ConservationStatus conservationStatus) {
        switch (conservationStatus) {
            case Extinct:
                return Color.parseColor("white");
            case ExtinctInWild:
                return Color.parseColor("black");
            case CriticallyEndangered:
                return Color.parseColor("red");
            case Endangered:
                return Color.parseColor("#ff9944");
            case Vulnerable:
                return Color.parseColor("yellow");
            case NearThreatened:
                return Color.parseColor("green");
            case LeastConcern:
                return Color.parseColor("blue");
            case DataDeficient:
                return Color.parseColor("white");
            case NotEvaluated:
                return Color.parseColor("white");
            default:
                return Color.parseColor("white");

        }
    }
}
