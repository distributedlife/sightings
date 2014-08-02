package com.distributedlife.animalwiki.model;

import java.util.List;
import java.util.Map;

public class AnimalCollection {
    private List<String> headings;
    private Map<String, List<Animal>> children;

    public AnimalCollection(List<String> headings, Map<String, List<Animal>> children) {
        this.headings = headings;
        this.children = children;
    }

    public List<String> getHeadings() {
        return headings;
    }

    public Map<String, List<Animal>> getChildren() {
        return children;
    }
}
