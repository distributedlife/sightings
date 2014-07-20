package com.distributedlife.animalwiki.filters;

public abstract class ToggleFilter implements Filter {
    private final String name;
    private Boolean value;

    public ToggleFilter(String name, Boolean value) {
        this.name = name;
        this.value = value;
    }

    public String getName() { return name; }

    public Boolean getValue() { return value; }

    public void setValue(boolean value) {
        this.value = value;
    }
}
