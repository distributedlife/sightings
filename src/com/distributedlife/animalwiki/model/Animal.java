package com.distributedlife.animalwiki.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Animal {
    private final List<String> countries;
    String commonName;
    String officialName;
    String kingdom;
    String phylum;
    String klass;
    String order;
    String family;
    String genus;
    String species;
    String subspecies;
    String filename;
    ConservationStatus conservationStatus;
    private List<String> colours;

    public Animal(String commonName, String officialName, String phylum, String klass, String order, String family, String genus, String species, String subspecies, ConservationStatus conservationStatus, String filename, List<String> colours, List<String> countries, boolean endemic) {
        this.commonName = commonName;
        this.officialName = officialName;
        this.kingdom = "animalia";
        this.phylum = phylum;
        this.klass = klass;
        this.order = order;
        this.family = family;
        this.genus = genus;
        this.species = species;
        this.subspecies = subspecies;
        this.conservationStatus = conservationStatus;
        this.filename = filename;
        this.colours = colours;
        this.countries = countries;
    }

    public Animal(String commonName, String officialName, String phylum, String klass, String order, String family, String genus, String species, String subspecies, ConservationStatus conservationStatus, String filename, List<String> colours, List<String> countries) {
        this.commonName = commonName;
        this.officialName = officialName;
        this.phylum = phylum;
        this.klass = klass;
        this.order = order;
        this.family = family;
        this.genus = genus;
        this.species = species;
        this.subspecies = subspecies;
        this.conservationStatus = conservationStatus;
        this.filename = filename;
        this.colours = colours;
        this.countries = countries;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getKingdom() {
        return kingdom;
    }

    public String getPhylum() {
        return phylum;
    }

    public String getKlass() {
        return klass;
    }

    public String getOrder() {
        return order;
    }

    public String getFamily() {
        return family;
    }

    public String getGenus() {
        return genus;
    }

    public String getSpecies() {
        return species;
    }

    public String getSubspecies() {
        return subspecies;
    }

    public String getOfficialName() {
        return officialName;
    }

    public ConservationStatus getConservationStatus() {
        return conservationStatus;
    }

    public String getFilename() {
        return filename;
    }

    public boolean hasImage() {
        return !filename.equals("images/");
    }

//    public boolean hasColours() {
//        return colours.size() > 0;
//    }

    public String getColour(int i) {
        return colourReference().get(colours.get(i));
    }

    public List<String> getColours() {
        return colours;
    }
    
    private Map<String, String> colourReference() {
        Map<String, String> derp = new HashMap<String, String>();

        return derp;
    }

    public List<String> getCountries() {
        return countries;
    }

    public String getKey() {
        return officialName.replaceAll(" ", "_").toLowerCase();
    }

    public boolean isEndemic() {
        return countries.size() == 1;
    }
}
