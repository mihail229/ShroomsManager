package com.example.shroomsmanager.backend;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.shroomsmanager.data.Mushroom;
import com.example.shroomsmanager.data.Yield;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class MushroomFirestoreHelper {
    private static final String TAG = "MushroomFirestoreHelper";
    private final FirebaseFirestore db;

    public MushroomFirestoreHelper() {
        this.db = FirebaseFirestore.getInstance(); // Firestore-Instanz erstellen
    }

    // 1. Speichere einen Pilz
    public void saveMushroom(String userId, Mushroom mushroom) {
        db.collection("Users")
                .document(userId)
                .collection("Mushrooms")
                .document(mushroom.getName()) // Name des Pilzes als Dokument-ID
                .set(mushroom)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Pilz erfolgreich gespeichert: " + mushroom.getName());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Fehler beim Speichern des Pilzes: " + e.getMessage());
                    }
                });
    }

    // 2. Speichere Erträge für einen Pilz
    public void saveYields(String userId, String mushroomName, List<Yield> yields) {
        for (Yield yield : yields) {
            db.collection("Users")
                    .document(userId)
                    .collection("Mushrooms")
                    .document(mushroomName)
                    .collection("Yields")
                    .add(yield) // `add` erwartet DocumentReference
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "Yield erfolgreich gespeichert mit ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Fehler beim Speichern des Yields: " + e.getMessage());
                        }
                    });
        }
    }

    // 3. Hole alle Pilze
    public void getMushrooms(String userId) {
        db.collection("Users")
                .document(userId)
                .collection("Mushrooms")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        QuerySnapshot result = task.getResult();
                        boolean isSuccessful = task.isSuccessful();
                        if (isSuccessful && (result != null)) {
                            for (QueryDocumentSnapshot document : result) {
                                Mushroom mushroom = document.toObject(Mushroom.class);
                                Log.d(TAG, "Gefundener Pilz: " + mushroom.getName());
                            }
                        } else {
                            Log.e(TAG, "Fehler beim Abrufen der Pilze");
                        }
                    }
                });
    }

    // 4. Lösche einen Pilz
    public void deleteMushroom(String userId, String mushroomName) {
        db.collection("Users")
                .document(userId)
                .collection("Mushrooms")
                .document(mushroomName)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Pilz erfolgreich gelöscht: " + mushroomName);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Fehler beim Löschen des Pilzes: " + e.getMessage());
                    }
                });
    }
}
