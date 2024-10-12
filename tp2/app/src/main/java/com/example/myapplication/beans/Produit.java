package com.example.myapplication.beans;
public class Produit {
    private int id;
    private String nom;
    private int nbrIngredients;
    private int photo;
    private int duree;
    private String detaisIngres;
    private String description;
    private String preparation;
    private static int comp;
    public Produit() {
    }
    public Produit(String nom, int nbrIngredients, int photo,
                   int duree, String detaisIngres, String description, String preparation) {
        this.id = ++comp;
        this.nom = nom;
        this.nbrIngredients = nbrIngredients;
        this.photo = photo;
        this.duree = duree;
        this.detaisIngres = detaisIngres;
        this.description = description;
        this.preparation = preparation;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public int getNbrIngredients() {
        return nbrIngredients;
    }
    public void setNbrIngredients(int nbrIngredients) {
        this.nbrIngredients = nbrIngredients;
    }
    public int getPhoto() {
        return photo;
    }
    public void setPhoto(int photo) {this.photo = photo;}
    public int getDuree() {
        return duree;
    }
    public void setDuree(int duree) {
        this.duree = duree;
    }
    public String getDetaisIngres() {
        return detaisIngres;
    }
    public void setDetaisIngres(String detaisIngres) {this.detaisIngres = detaisIngres;}
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPreparation() {
        return preparation;
    }
    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }
    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", nbrIngredients=" + nbrIngredients +
                ", photo=" + photo +
                ", duree=" + duree +
                ", detaisIngres='" + detaisIngres + '\'' +
                ", description=" + description +
                ", preparation='" + preparation + '\'' +
                '}';
    }
}
