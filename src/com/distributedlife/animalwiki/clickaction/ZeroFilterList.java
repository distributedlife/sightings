package com.distributedlife.animalwiki.clickaction;

import android.view.View;
import com.distributedlife.animalwiki.filters.ToggleFilter;
import com.distributedlife.animalwiki.listAdapters.FilterAdapter;

import java.util.List;

public class ZeroFilterList implements View.OnClickListener {
    private List<ToggleFilter> filters;
    private FilterAdapter filterAdapter;

    public ZeroFilterList(List<ToggleFilter> filters, FilterAdapter filterAdapter) {
        this.filters = filters;
        this.filterAdapter = filterAdapter;
    }

    @Override
    public void onClick(View view) {
        for (ToggleFilter filter: filters) {
            filter.setValue(false);
        }

        filterAdapter.notifyDataSetChanged();
    }
}
