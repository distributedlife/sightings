package com.distributedlife.animalwiki.filters;

import com.distributedlife.animalwiki.model.Animal;

import java.util.List;

public class ZeroFilter implements Filter {
    private List<ToggleFilter> filters;

    public ZeroFilter(List<ToggleFilter> filters) {
        this.filters = filters;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean exclude(Animal animal) {
        return false;
    }

    public List<ToggleFilter> getFilters() {
        return filters;
    }
}
