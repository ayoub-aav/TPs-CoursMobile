package com.example.php2.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RadioGroup;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.php2.R;
import com.example.php2.beans.Etudiant;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.EtudiantViewHolder> implements Filterable {



    private List<Etudiant> etudiantList;
    private List<Etudiant> etudiantListFull;
    private Context context;

    private RequestQueue requestQueue;
    private static final String DELETE_URL = "http://10.0.2.2/php_volley/ws/deleteEtudiant.php";
    private static final String UPDATE_URL = "http://10.0.2.2/php_volley/ws/updateEtudiant.php";


    public EtudiantAdapter(Activity context, List<Etudiant> etudiantList) {
        this.etudiantList = new ArrayList<>(etudiantList);
        this.etudiantListFull = new ArrayList<>(etudiantList);

        this.context = context;

        requestQueue = Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public EtudiantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.etudiant, parent, false);
        return new EtudiantViewHolder(view, context, etudiantList);

    }

    @Override
    public int getItemCount() {
        // Use the filtered list size for item count
        return etudiantList.size();
    }





    @Override
    public void onBindViewHolder(@NonNull EtudiantViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Etudiant etudiant = etudiantList.get(position);

        // Set name, prenom, and other data
        holder.nomTextView.setText(etudiant.getNom());
        holder.prenomTextView.setText(etudiant.getPrenom());
        holder.villeTextView.setText(etudiant.getVille());
        holder.sexeTextView.setText(etudiant.getSexe());

        // Handling the image
        String base64Image = etudiant.getImage();
        Log.d("EtudiantAdapter", "Image data: " + base64Image);

        if (base64Image != null && !base64Image.isEmpty()) {
            try {
                // Decode Base64 string to byte array
                byte[] decodedBytes = android.util.Base64.decode(base64Image, android.util.Base64.DEFAULT);

                // Convert byte array to Bitmap
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

                // Load image using Glide
                Glide.with(holder.itemView.getContext())
                        .load(decodedBitmap)
                        .into(holder.profileImageView);
            } catch (IllegalArgumentException e) {
                Log.e("EtudiantAdapter", "Invalid Base64 image data", e);
                holder.profileImageView.setImageResource(R.drawable.baseline_person_24); // Default image
            }
        } else {
            holder.profileImageView.setImageResource(R.drawable.baseline_person_24); // Default image if no Base64 string
        }
        // Set click listener on the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the dialog when the item is clicked
                showUpdateDialog(holder.nomTextView, holder.prenomTextView, holder.villeTextView, holder.sexeTextView, position);
            }
        });
    }





    // Method to show the dialog
    private void showUpdateDialog(TextView nomTextView, TextView prenomTextView, TextView villeTextView, TextView sexeTextView, int position) {
        View popup = LayoutInflater.from(context).inflate(R.layout.activity_modifi_etudiant, null, false);

        final EditText nom = popup.findViewById(R.id.nommodif);
        final EditText prenom = popup.findViewById(R.id.prenommodif);
        final Spinner villeSpinner = popup.findViewById(R.id.villemodif);
        final RadioGroup sexeRadioGroup = popup.findViewById(R.id.radiomodif);

        // Set existing data into the popup fields
        nom.setText((nomTextView.getText().toString()));
        prenom.setText((prenomTextView.getText().toString()));

        String currentVille = (villeTextView.getText().toString());
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) villeSpinner.getAdapter();
        int villePosition = adapter.getPosition(currentVille);
        villeSpinner.setSelection(villePosition);

        String currentSexe = (sexeTextView.getText().toString());
        if (currentSexe.equals("Homme")) {
            sexeRadioGroup.check(R.id.m_modif);
        } else if (currentSexe.equals("Femme")) {
            sexeRadioGroup.check(R.id.f_modif);
        }

        new AlertDialog.Builder(context)
                .setTitle("Modifier les informations")
                .setView(popup)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Retrieve updated values from the input fields
                        String updatedNom = nom.getText().toString();
                        String updatedPrenom = prenom.getText().toString();
                        String updatedVille = villeSpinner.getSelectedItem().toString();

                        int selectedSexeId = sexeRadioGroup.getCheckedRadioButtonId();
                        String updatedSexe = selectedSexeId == R.id.m_modif ? "Homme" : "Femme";

                        // Update the UI or the data list
                        if (position != RecyclerView.NO_POSITION) {
                            Etudiant etudiant = etudiantList.get(position);
                            etudiant.setNom(updatedNom);
                            etudiant.setPrenom(updatedPrenom);
                            etudiant.setVille(updatedVille);
                            etudiant.setSexe(updatedSexe);

                            // Call the update method to update the backend
                            updateEtudiant(etudiant);

                            // Update the TextViews with the new values
                            nomTextView.setText("Nom : " + updatedNom);
                            prenomTextView.setText("Prénom : " + updatedPrenom);
                            villeTextView.setText("Ville : " + updatedVille);
                            sexeTextView.setText("Sexe : " + updatedSexe);

                            notifyItemChanged(position);
                        }
                    }
                })
                .setNegativeButton("Annuler", null)
//                .setNeutralButton("Suprimer", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Get the selected student
//                        if (position != RecyclerView.NO_POSITION) {
//                            Etudiant etudiantToDelete = etudiantList.get(position);
//
//                            // Call the delete method to delete the student
//                            deleteEtudiant(etudiantToDelete);
//
//                            // Remove the student from the list and notify adapter
//                            etudiantList.remove(position);
//                            notifyItemRemoved(position);
//
//                            // Optionally, show a message
//                            Toast.makeText(context, "Étudiant supprimé", Toast.LENGTH_SHORT).show();
//                        }
//                        dialog.dismiss();
//                    }
//                })
                .show();
    }









    private Filter etudiantFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Etudiant> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(etudiantListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Etudiant etudiant : etudiantListFull) {
                    if (etudiant.getNom().toLowerCase().startsWith(filterPattern) ||
                            etudiant.getPrenom().toLowerCase().startsWith(filterPattern) ||
                            etudiant.getVille().toLowerCase().startsWith(filterPattern) ||
                            etudiant.getSexe().toLowerCase().startsWith(filterPattern)) {
                        filteredList.add(etudiant);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            etudiantList.clear();
            etudiantList.addAll((List<Etudiant>) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return etudiantFilter;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Etudiant> newList) {
        this.etudiantListFull = new ArrayList<>(newList);
        this.etudiantList.clear();
        this.etudiantList.addAll(newList);
        notifyDataSetChanged();
    }


    public void attachSwipeToDelete(RecyclerView recyclerView) {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Etudiant etudiantToDelete = etudiantList.get(position);
                    deleteEtudiant(etudiantToDelete);
                    etudiantList.remove(position);
                    notifyItemRemoved(position);
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // Ajoutez ici des effets visuels lors du glissement, si vous le souhaitez
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void deleteEtudiant(Etudiant etudiant) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("DeleteEtudiant", "Response: " + response);
                        // Assume the response returns a success message
                        Toast.makeText(context, "Etudiant supprimé avec succès", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("DeleteEtudiant", "Volley error: " + error.getMessage(), error);
                        Toast.makeText(context, "Erreur: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(etudiant.getId())); // Assuming Etudiant has a getId() method
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
    private void updateEtudiant(Etudiant etudiant) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("UpdateEtudiant", "Response: " + response);
                        // Assume the response returns a success message
                        Toast.makeText(context, "Etudiant modifier avec succès", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("UpdateEtudiant", "Volley error: " + error.getMessage(), error);
                        Toast.makeText(context, "Erreur: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(etudiant.getId()));
                params.put("nom", String.valueOf(etudiant.getNom()));
                params.put("prenom", String.valueOf(etudiant.getPrenom()));
                params.put("ville", String.valueOf(etudiant.getVille()));
                params.put("sexe", String.valueOf(etudiant.getSexe()));// Assuming Etudiant has a getId() method
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    class EtudiantViewHolder extends RecyclerView.ViewHolder {
        public TextView nomTextView, prenomTextView, villeTextView, sexeTextView;
        public ImageView  profileImageView;
        public Context context;
        public List<Etudiant> etudiantList;

        EtudiantViewHolder(View itemView, Context context, List<Etudiant> etudiantList) {
            super(itemView);
            this.context = context;
            this.etudiantList = etudiantList;

            // Initialize views
            nomTextView = itemView.findViewById(R.id.nommodif);
            prenomTextView = itemView.findViewById(R.id.prenommodif);
            villeTextView = itemView.findViewById(R.id.villemodif);
            sexeTextView = itemView.findViewById(R.id.sexe);
            profileImageView = itemView.findViewById(R.id.imageitem);

            setupListeners();
        }

        // Method to bind data to views
        void bind(Etudiant etudiant, EtudiantViewHolder holder) {
            nomTextView.setText(etudiant.getFormattedNom());
            prenomTextView.setText(etudiant.getFormattedPrenom());
            villeTextView.setText(etudiant.getFormattedVille());
            sexeTextView.setText(etudiant.getFormattedSexe());

            // Load and set the image
            String base64Image = etudiant.getImage();
            Log.d("EtudiantAdapter", "Image data: " + base64Image);

            if (base64Image != null && !base64Image.isEmpty()) {
                try {
                    // Decode Base64 string to byte array
                    byte[] decodedBytes = android.util.Base64.decode(base64Image, android.util.Base64.DEFAULT);

                    // Convert byte array to Bitmap
                    Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

                    // Use Glide to load the decoded Bitmap
                    Glide.with(holder.itemView.getContext())
                            .load(decodedBitmap)
                            .into(holder.profileImageView);
                } catch (IllegalArgumentException e) {
                    Log.e("EtudiantAdapter", "Invalid Base64 image data", e);
                    holder.profileImageView.setImageResource(R.drawable.baseline_person_24); // Default image if decoding fails
                }
            } else {
                holder.profileImageView.setImageResource(R.drawable.baseline_person_24); // Default image if no Base64 string
            }
        }
        private void setupListeners() {

        }

        // Helper method to extract the value from formatted text
        private String extractValue(String formattedText) {
            int colonIndex = formattedText.indexOf(':');
            if (colonIndex != -1 && colonIndex < formattedText.length() - 1) {
                return formattedText.substring(colonIndex + 1).trim();
            }
            return formattedText;
        }



    }
}
