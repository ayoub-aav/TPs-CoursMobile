package com.example.travailafaire;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class MainActivity extends AppCompatActivity {
    Button send;
    TextView Nom ,Adress ,Email ,Ville ,Phone ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Nom = findViewById(R.id.name);
        Adress = findViewById(R.id.adress);
        Email = findViewById(R.id.email);
        Ville = findViewById(R.id.ville);
        Phone = findViewById(R.id.phone);
        send = findViewById(R.id.envoyer);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = Nom.getText().toString();
                String adress = Adress.getText().toString();
                String email = Email.getText().toString();
                String ville = Ville.getText().toString();
                String phone = Phone.getText().toString();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("nom", nom);
                intent.putExtra("adress", adress);
                intent.putExtra("email", email);
                intent.putExtra("ville", ville);
                intent.putExtra("phone", phone);
                startActivity(intent);
            }
        });
    }
}