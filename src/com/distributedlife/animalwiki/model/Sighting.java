package com.distributedlife.animalwiki.model;

public class Sighting {
    private int id;
    private String what;

    public Sighting(int id, String what) {
        this.id = id;
        this.what = what;
    }

    public Sighting(String what) {
        this.id = 0;
        this.what = what;
    }

    public String getWhat() {
        return what;
    }
}
