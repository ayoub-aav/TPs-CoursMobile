package com.example.exercice2;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText surfaceText, nbrPieceText;
    CheckBox picsineCheckBox;
    TextView baseText, supplementaireText, totale;
    Button calcul;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceText = findViewById(R.id.surfacetext);
        nbrPieceText = findViewById(R.id.nbrpiece);
        picsineCheckBox = findViewById(R.id.checkBox);
        baseText = findViewById(R.id.base);
        supplementaireText = findViewById(R.id.supplementaire);
        totale = findViewById(R.id.total);
        calcul = findViewById(R.id.button);
        calcul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateTaxes();
            }
        });
    }
    private void calculateTaxes() {
        int surface = Integer.parseInt(surfaceText.getText().toString());
        int nbrPiece = Integer.parseInt(nbrPieceText.getText().toString());
        int baseTax = surface * 2;
        int supplementaryTax = nbrPiece * 50;
        if (picsineCheckBox.isChecked()) {
            supplementaryTax += 100; // Add 100 if the pool is checked
        }
        int totalTax = baseTax + supplementaryTax;
        baseText.setText("Impôt de base: " + baseTax);
        supplementaireText.setText("Impôt supplémentaire: " + supplementaryTax);
        totale.setText("Impôt Total: " + totalTax);
    }
}
