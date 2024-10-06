package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProduitRecepes extends AppCompatActivity {

    TextView produitnom, produitdescription, produitingredient;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_recepes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        produitnom = findViewById(R.id.produitnom1);
        produitdescription = findViewById(R.id.produitdescription);
        produitingredient = findViewById(R.id.produitingredient);
        image = findViewById(R.id.produitimage1);

        produitnom.setText(getIntent().getStringExtra("nom") + "");
        produitdescription.setText(getIntent().getStringExtra("description") + "");
        produitingredient.setText(getIntent().getStringExtra("ingredient") + "");
        image.setImageResource(getIntent().getIntExtra("image", 0));
    }
}