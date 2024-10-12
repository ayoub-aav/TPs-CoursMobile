package com.example.myapplication.Adapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Produit;

import java.util.List;

public class ProduitAdapter extends BaseAdapter {

    private List<Produit> produit;
    private LayoutInflater inflater;

    public ProduitAdapter(List<Produit> produit, Activity activity) {
        this.produit = produit;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return produit.size();
    }

    @Override
    public Object getItem(int position) {
        return produit.get(position);
    }
    public void remove(int position) {
        produit.remove(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.items, null);
        TextView id = convertView.findViewById(R.id.produitid);
        TextView name = convertView.findViewById(R.id.produitnom);
        TextView nbringred = convertView.findViewById(R.id.produitnbringred);
        TextView duree = convertView.findViewById(R.id.produitduree);
        ImageView image = convertView.findViewById(R.id.produitimage);
        id.setText(produit.get(position).getId() + "");
        name.setText(produit.get(position).getNom() + "");
        nbringred.setText(produit.get(position).getNbrIngredients() + "");
        duree.setText(produit.get(position).getDuree() + "");
        image.setImageResource(produit.get(position).getPhoto());
        return convertView;
    }
}
