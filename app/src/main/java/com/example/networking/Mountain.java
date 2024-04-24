package com.example.networking;
/*
"ID":"mobilprog_kinnekulle","name":"Kinnekulle","type":"brom","company":"","location":"Skaraborg",
"category":"","size":306,"cost":1004,"auxdata":{"wiki":"https://sv.wikipedia.org/wiki/Kinnekulle","img":""
*/

public class Mountain {
    private String ID;
    private String type;
    private String company;
    private String category;
    private int cost;
    private Auxdata auxdata;

    private String name;
    private String location;
    private int size;
    public Mountain() {
        this.name = "name";
        this.location = "location";
        this.size = 0;
    }
    public Mountain(String n, String l, int h) {
        this.name = n;
        this.location = l;
        this.size = h;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getHeight() {
        return size;
    }

    @Override
    public String toString() {
        return name;
    }
}
