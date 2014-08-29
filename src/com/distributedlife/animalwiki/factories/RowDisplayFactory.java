package com.distributedlife.animalwiki.factories;

import com.distributedlife.animalwiki.partials.RowDisplay;

public interface RowDisplayFactory {
    RowDisplay build(int position);

    int getTypeCount();
}
