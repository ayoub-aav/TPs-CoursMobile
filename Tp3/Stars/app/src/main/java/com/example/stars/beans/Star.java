package com.example.stars.beans;

public class Star {
    private int id;
    private String name;
    private int image;
    private float star;

    private static int comp;

    public Star(String name, int image, float star) {
        this.id=++comp;
        this.name = name;
        this.image = image;
        this.star = star;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }
}
