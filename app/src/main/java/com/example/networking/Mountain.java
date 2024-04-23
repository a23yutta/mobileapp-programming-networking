package com.example.networking;
import androidx.annotation.NonNull;

public class Mountain {
    private String name;
    private String location;
    private int height;
    public Mountain() {
        this.name = "name";
        this.location = "location";
        this.height = 0;
    }
    public Mountain(String name, String location, int height) {
        this.name = "name";
        this.location = "location";
        this.height = 0;
    }
    public void setName(String n) {
        this.name=n;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setHeight(int height) {
        this.height = height;
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
    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
