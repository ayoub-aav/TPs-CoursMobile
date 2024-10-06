package com.example.myapplication.Services;

import com.example.myapplication.Dao.idao;
import com.example.myapplication.beans.Produit;

import java.util.ArrayList;
import java.util.List;

public class ProduitService implements idao<Produit> {

    private List<Produit> produits;

    public ProduitService() {
        produits = new ArrayList<>();
    }

    @Override
    public boolean create(Produit f) {
        return produits.add(f);
    }

    @Override
    public boolean update(Produit f) {
        return false;
    }

    @Override
    public boolean delete(Produit f) {
        return produits.remove(f);
    }

    @Override
    public Produit findById(int id) {
        for (Produit f : produits) {
            if (f.getId() == id) {
                return f;
            }
        }
        return null;
    }

    @Override
    public List<Produit> findall() {
        return produits;
    }
}
