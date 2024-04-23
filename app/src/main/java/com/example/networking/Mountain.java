package com.example.networking;

public class Mountain {
    private String name;
    private String location;
    private int height;
    public Mountain() {
        this.name = "name";
        this.location = "location";
        this.height = 0;
    }
    public Mountain(String n, String l, int h) {
        this.name = n;
        this.location = l;
        this.height = h;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return name;
    }
}
