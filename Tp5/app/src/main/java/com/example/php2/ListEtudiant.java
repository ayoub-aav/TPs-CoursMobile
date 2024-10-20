package com.example.php2;
import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
//import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import androidx.appcompat.widget.SearchView;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import android.graphics.Bitmap;
import android.net.Uri;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import androidx.appcompat.app.AlertDialog;

import com.example.php2.adapter.EtudiantAdapter;
import com.example.php2.beans.Etudiant;

import java.io.ByteArrayOutputStream;

public class ListEtudiant extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EtudiantAdapter adapter;
    private List<Etudiant> etudiantList;
    private RequestQueue requestQueue;
    private static final String LOAD_URL = "http://10.0.2.2/php_volley/ws/loadEtudiant.php";
    private static final String insertUrl = "http://10.0.2.2/php_volley/ws/createEtudiant.php";
    private Bitmap selectedBitmap; // For image handling in dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_etudiant);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        etudiantList = new ArrayList<>();
        adapter = new EtudiantAdapter(this, etudiantList);
        recyclerView.setAdapter(adapter);
        adapter.attachSwipeToDelete(recyclerView);

        requestQueue = Volley.newRequestQueue(this);
        loadEtudiants();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null) {
                    adapter.getFilter().filter(newText);
                }
                return true;
            }
        });

        MenuItem addItem = menu.findItem(R.id.app_plus);
        addItem.setOnMenuItemClickListener(item -> {
            showAddEtudiantDialog(); // Opens the dialog box to add a student
            return true;
        });

        return true;
    }



    private void showAddEtudiantDialog() {
        // Inflate the layout for the dialog
        LayoutInflater inflater = getLayoutInflater();
        View popup = inflater.inflate(R.layout.activity_add_etudiant, null);

        // Get references to the views in the layout
        EditText nom = popup.findViewById(R.id.nommodif);
        EditText prenom = popup.findViewById(R.id.prenommodif);
        Spinner ville = popup.findViewById(R.id.villemodif);
        RadioButton homme = popup.findViewById(R.id.m_modif);
        RadioButton femme = popup.findViewById(R.id.f_modif);

        Button selectImage = popup.findViewById(R.id.imageadd);
        selectImage.setOnClickListener(view -> openImageChooser());

        // Create the dialog
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Ajouter un étudiant")
                .setView(popup)
                .setPositiveButton("Valider", (dialogInterface, i) -> {
                    // Validate and send the student data
                    if (validateForm(nom, prenom)) {
                        sendEtudiantData(nom, prenom, ville, homme, femme, selectedBitmap);
                    } else {
                        Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Annuler", (dialogInterface, i) -> {
                    // Dismiss the dialog
                    dialogInterface.dismiss();
                })
                .create();

        dialog.show();
    }

    private boolean validateForm(EditText nom, EditText prenom) {
        return !nom.getText().toString().isEmpty() && !prenom.getText().toString().isEmpty();
    }


    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                selectedBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error selecting image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendEtudiantData(EditText nom, EditText prenom, Spinner ville, RadioButton homme, RadioButton femme, Bitmap bitmap) {
        String sexe = homme.isChecked() ? "homme" : "femme";
        String nomVal = nom.getText().toString();
        String prenomVal = prenom.getText().toString();
        String villeVal = ville.getSelectedItem().toString();
        String imageBase64 = "";

        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            imageBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }

        String finalImageBase6 = imageBase64;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, insertUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response);
                        Toast.makeText(ListEtudiant.this, "Étudiant ajouté avec succès", Toast.LENGTH_SHORT).show();
                        loadEtudiants(); // Reload student list after adding
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(ListEtudiant.this, "Erreur: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nom", nomVal);
                params.put("prenom", prenomVal);
                params.put("ville", villeVal);
                params.put("sexe", sexe);
                if (!finalImageBase6.isEmpty()) {
                    params.put("image", finalImageBase6);
                }
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }


    private void loadEtudiants() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response);
                        try {
                            // Parse the JSON array
                            Type listType = new TypeToken<List<Etudiant>>() {}.getType();
                            List<Etudiant> loadedEtudiants = new Gson().fromJson(response, listType);

                            if (loadedEtudiants == null || loadedEtudiants.isEmpty()) {
                                throw new Exception("Parsed student list is null or empty");
                            }

                            etudiantList.clear();
                            etudiantList.addAll(loadedEtudiants);
                            adapter.updateData(loadedEtudiants);
                            adapter.notifyDataSetChanged();
                        } catch (JsonSyntaxException e) {
                            Log.e(TAG, "JSON syntax error: " + e.getMessage(), e);
                            Toast.makeText(ListEtudiant.this, "Error parsing JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e(TAG, "Error loading students", e);
                            Toast.makeText(ListEtudiant.this, "Error loading students", Toast.LENGTH_SHORT).show();
                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Volley error: " + error.getMessage(), error);
                        Toast.makeText(ListEtudiant.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(stringRequest);
    }
}
