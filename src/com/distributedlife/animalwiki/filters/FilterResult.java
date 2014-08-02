package com.distributedlife.animalwiki.filters;

import com.distributedlife.animalwiki.model.Animal;

import java.util.List;
import java.util.Map;

public class FilterResult {
    private List<String> headers;
    private Map<String, List<Animal>> children;

    public List<String> getHeaders() {
        return headers;
    }

    public Map<String, List<Animal>> getChildren() {
        return children;
    }
}
