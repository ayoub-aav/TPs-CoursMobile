package com.example.travailafaire;
import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class MainActivity2 extends AppCompatActivity {
    TextView nomResultat, adressResultat, emailResultat, villeResultat, phoneResultat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nomResultat = findViewById(R.id.nom_resultat);
        adressResultat = findViewById(R.id.adress_resultat);
        emailResultat = findViewById(R.id.email_resultat);
        villeResultat = findViewById(R.id.ville_resultat);
        phoneResultat = findViewById(R.id.phone_resultat);
        String nom = getIntent().getStringExtra("nom");
        String adress = getIntent().getStringExtra("adress");
        String email = getIntent().getStringExtra("email");
        String ville = getIntent().getStringExtra("ville");
        String phone = getIntent().getStringExtra("phone");
        nomResultat.setText("Nom: " + nom);
        adressResultat.setText("Adress: " + adress);
        emailResultat.setText("Email: " + email);
        villeResultat.setText("Ville: " + ville);
        phoneResultat.setText("Phone: " + phone);
    }
}