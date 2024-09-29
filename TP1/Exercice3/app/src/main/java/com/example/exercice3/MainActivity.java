package com.example.exercice3;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class MainActivity extends AppCompatActivity {
    Button quitter , suivant ;
    CheckBox checkbox_1, checkbox_2,checkbox_3, checkbox_4 ;
    RadioGroup radiogrp ;
    RadioButton choixoui, choixnon ;
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
        checkbox_1 = findViewById(R.id.checkbox1);
        checkbox_2 = findViewById(R.id.checkbox2);
        checkbox_3 = findViewById(R.id.checkbox3);
        checkbox_4 = findViewById(R.id.checkbox4);
        choixoui = findViewById(R.id.radiooui);
        choixnon = findViewById(R.id.radionon);
        radiogrp = findViewById(R.id.radiogroup);
        suivant = findViewById(R.id.btnsuivant);
        quitter = findViewById(R.id.btnquitter);
        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder Options = new StringBuilder("Selected: ");
                if (checkbox_1.isChecked()) Options.append("Multiple Versions Combined, ");
                if (checkbox_2.isChecked()) Options.append("Model View Controller, ");
                if (checkbox_3.isChecked()) Options.append("Main Value Combined, ");
                if (checkbox_4.isChecked()) Options.append("Mandatory Validated Controls, ");
                int selectedRadioId = radiogrp.getCheckedRadioButtonId();
                if (selectedRadioId == R.id.radiooui) {
                    Options.append("OUI");
                } else if (selectedRadioId == R.id.radionon) {
                    Options.append("NON");
                }
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("les options sont :", Options.toString());
                startActivity(intent);
            }
        });
        quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finish the activity and quit
                finish();
            }
        });
    }
}