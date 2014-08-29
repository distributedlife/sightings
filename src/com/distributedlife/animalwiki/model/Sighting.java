package com.distributedlife.animalwiki.model;

import org.joda.time.DateTime;

public class Sighting {
    private String id;
    private String what;
    private DateTime when;

    public Sighting(String id, String what, DateTime when) {
        this.id = id;
        this.what = what;
        this.when = when;
    }

    public Sighting(String what) {
        this.id = null;
        this.what = what;
        this.when = DateTime.now();
    }

    public String getWhat() {
        return what;
    }

    public DateTime getWhen() {
        return when;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
