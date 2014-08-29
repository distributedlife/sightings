package com.distributedlife.animalwiki.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Animal {
    private final List<String> countries;
    private final boolean endemic;
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
        this.endemic = endemic;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getWikiFileName() {
        return commonName.toLowerCase().replaceAll(" ", "_") + ".html";
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
        derp.put("aliceblue", "#f0f8ff");
        derp.put("darkolivegreen", "#556b2f");
        derp.put("indigo", "#4b0082");
        derp.put("mediumpurple", "#9370d8");
        derp.put("purple", "#800080");
        derp.put("antiquewhite", "#faebd7");
        derp.put("darkorange", "#ff8c00");
        derp.put("ivory", "#fffff0");
        derp.put("mediumseagreen", "#3cb371");
        derp.put("red", "#ff0000");
        derp.put("aqua", "#00ffff");
        derp.put("darkorchid", "#9932cc");
        derp.put("khaki", "#f0e68c");
        derp.put("mediumslateblue", "#7b68ee");
        derp.put("rosybrown", "#bc8f8f");
        derp.put("aquamarine", "#7fffd4");
        derp.put("darkred", "#8b0000");
        derp.put("lavender", "#e6e6fa");
        derp.put("mediumspringgreen", "#00fa9a");
        derp.put("royalblue", "#4169e1");
        derp.put("azure", "#f0ffff");
        derp.put("darksalmon", "#e9967a");
        derp.put("lavenderblush", "#fff0f5");
        derp.put("mediumturquoise", "#48d1cc");
        derp.put("sputlebrown", "#8b4513");
        derp.put("beige", "#f5f5dc");
        derp.put("darkseagreen", "#8fbc8f");
        derp.put("lawngreen", "#7cfc00");
        derp.put("mediumvioletred", "#c71585");
        derp.put("salmon", "#fa8072");
        derp.put("bisque", "#ffe4c4");
        derp.put("darkslateblue", "#483d8b");
        derp.put("lemonchiffon", "#fffacd");
        derp.put("midnightblue", "#191970");
        derp.put("sandybrown", "#f4a460");
        derp.put("black", "#000000");
        derp.put("darkslategray", "#2f4f4f");
        derp.put("lightblue", "#put8e6");
        derp.put("mintcream", "#f5fffa");
        derp.put("seagreen", "#2e8b57");
        derp.put("blanchedalmond", "#ffebcd");
        derp.put("darkturquoise", "#00ced1");
        derp.put("lightcoral", "#f08080");
        derp.put("mistyrose", "#ffe4e1");
        derp.put("seashell", "#fff5ee");
        derp.put("blue", "#0000ff");
        derp.put("darkviolet", "#9400d3");
        derp.put("lightcyan", "#e0ffff");
        derp.put("moccasin", "#ffe4b5");
        derp.put("sienna", "#a0522d");
        derp.put("blueviolet", "#8a2be2");
        derp.put("deeppink", "#ff1493");
        derp.put("lightgoldenrodyellow", "#fafad2");
        derp.put("navajowhite", "#ffdead");
        derp.put("silver", "#c0c0c0");
        derp.put("brown", "#a52a2a");
        derp.put("deepskyblue", "#00bfff");
        derp.put("lightgray", "#d3d3d3");
        derp.put("navy", "#000080");
        derp.put("skyblue", "#87ceeb");
        derp.put("burlywood", "#deb887");
        derp.put("dimgray", "#696969");
        derp.put("lightgreen", "#90ee90");
        derp.put("oldlace", "#fdf5e6");
        derp.put("slateblue", "#6a5acd");
        derp.put("cadetblue", "#5f9ea0");
        derp.put("dodgerblue", "#1e90ff");
        derp.put("lightpink", "#ffb6c1");
        derp.put("olive", "#808000");
        derp.put("slategray", "#708090");
        derp.put("chartreuse", "#7fff00");
        derp.put("firebrick", "#b22222");
        derp.put("lightsalmon", "#ffa07a");
        derp.put("olivedrab", "#688e23");
        derp.put("snow", "#fffafa");
        derp.put("chocolate", "#d2691e");
        derp.put("floralwhite", "#fffaf0");
        derp.put("lightseagreen", "#20b2aa");
        derp.put("orange", "#ffa500");
        derp.put("springgreen", "#00ff7f");
        derp.put("coral", "#ff7f50");
        derp.put("forestgreen", "#228b22");
        derp.put("lightskyblue", "#87cefa");
        derp.put("orangered", "#ff4500");
        derp.put("steelblue", "#4682b4");
        derp.put("cornflowerblue", "#6495ed");
        derp.put("fuchsia", "#ff00ff");
        derp.put("lightslategray", "#778899");
        derp.put("orchid", "#da70d6");
        derp.put("tan", "#d2b48c");
        derp.put("cornsilk", "#fff8dc");
        derp.put("gainsboro", "#dcdcdc");
        derp.put("lightsteelblue", "#b0c4de");
        derp.put("palegoldenrod", "#eee8aa");
        derp.put("teal", "#008080");
        derp.put("crimson", "#dc143c");
        derp.put("ghostwhite", "#f8f8ff");
        derp.put("lightyellow", "#ffffe0");
        derp.put("palegreen", "#98fb98");
        derp.put("thistle", "#d8bfd8");
        derp.put("cyan", "#00ffff");
        derp.put("gold", "#ffd700");
        derp.put("lime", "#00ff00");
        derp.put("paleturquoise", "#afeeee");
        derp.put("tomato", "#ff6347");
        derp.put("darkblue", "#00008b");
        derp.put("goldenrod", "#daa520");
        derp.put("limegreen", "#32cd32");
        derp.put("palevioletred", "#d87093");
        derp.put("turquoise", "#40e0d0");
        derp.put("darkcyan", "#008b8b");
        derp.put("gray", "#808080");
        derp.put("linen", "#faf0e6");
        derp.put("papayawhip", "#ffefd5");
        derp.put("violet", "#ee82ee");
        derp.put("darkgoldenrod", "#b8860b");
        derp.put("green", "#008000");
        derp.put("magenta", "#ff00ff");
        derp.put("peachpuff", "#ffdab9");
        derp.put("wheat", "#f5deb3");
        derp.put("darkgray", "#a9a9a9");
        derp.put("greenyellow", "#adff2f");
        derp.put("maroon", "#800000");
        derp.put("peru", "#cd853f");
        derp.put("white", "#ffffff");
        derp.put("darkgreen", "#006400");
        derp.put("honeydew", "#f0fff0");
        derp.put("mediumaquamarine", "#66cdaa");
        derp.put("pink", "#ffc0cb");
        derp.put("whitesmoke", "#f5f5f5");
        derp.put("darkkhaki", "#bdb76b");
        derp.put("hotpink", "#ff69b4");
        derp.put("mediumblue", "#0000cd");
        derp.put("plum", "#dda0dd");
        derp.put("yellow", "#ffff00");
        derp.put("darkmagenta", "#8b008b");
        derp.put("indianred", "#cd5c5c");
        derp.put("mediumorchid", "#ba55d3");
        derp.put("powderblue", "#b0e0e6");
        derp.put("yellowgreen", "#9acd32");

        return derp;
    }

    public List<String> getCountries() {
        return countries;
    }

    public String getKey() {
        return officialName.replaceAll(" ", "_").toLowerCase();
    }
}
