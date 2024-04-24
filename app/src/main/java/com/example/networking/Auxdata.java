package com.example.networking;

public class Auxdata {
    private String wiki;
    private String img;
    public Auxdata(){
        this.wiki = "wiki";
        this.img = "img";
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setWiki(String wiki) {
        this.wiki = wiki;
    }

    public String getImg() {
        return img;
    }

    public String getWiki() {
        return wiki;
    }
}
