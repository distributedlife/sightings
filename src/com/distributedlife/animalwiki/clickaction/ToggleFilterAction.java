package com.distributedlife.animalwiki.clickaction;

import android.widget.CompoundButton;
import com.distributedlife.animalwiki.filters.ToggleFilter;

public class ToggleFilterAction implements CompoundButton.OnCheckedChangeListener {
    private ToggleFilter filter;

    public ToggleFilterAction(ToggleFilter filter) {
        this.filter = filter;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean value) {
        filter.setValue(value);
    }
}
