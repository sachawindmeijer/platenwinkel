package com.example.platenwinkel.enumeration;

public enum Muziek {
    ROCK("Energetic and loud"),
    POP("Popular and catchy"),
    JAZZ("Smooth and complex"),
    CLASSICAL("Timeless and sophisticated"),
    HIPHOP("Rhythmic and expressive"),
    SOUL("Emotional and smooth");

    private String description;

    Muziek(String s) {
    }

    void Genre(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
