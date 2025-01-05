package com.example.shroomsmanager.backend;

import android.util.Log;

import com.example.shroomsmanager.data.Mushroom;
import com.example.shroomsmanager.data.Yield;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MushroomFirestoreHelper {

    private static final String TAG = "MushroomFirestoreHelper";
    private final FirebaseFirestore db;

    public MushroomFirestoreHelper() {
        this.db = FirebaseFirestore.getInstance(); // Firestore-Instanz initialisieren
    }

    // Speichert einen Pilz und seine Erträge in Firestore
    public void saveMushroom(String userId, Mushroom mushroom) {
        // Speichere den Pilz ohne die Erträge
        db.collection("Users")
                .document(userId)
                .collection("Mushrooms")
                .document(mushroom.getName())
                .set(mushroom)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Pilz gespeichert: " + mushroom.getName());
                    // Speichere die Erträge separat
                    saveYields(userId, mushroom.getName(), mushroom.getYields());
                })
                .addOnFailureListener(e -> Log.e(TAG, "Fehler beim Speichern des Pilzes: " + e.getMessage()));
    }

    // Speichert die Liste von Erträgen separat in einer Subcollection
    private void saveYields(String userId, String mushroomName, List<Yield> yields) {
        for (Yield yield : yields) {
            db.collection("Users")
                    .document(userId)
                    .collection("Mushrooms")
                    .document(mushroomName)
                    .collection("Yields")
                    .add(yield)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Yield gespeichert: " + yield))
                    .addOnFailureListener(e -> Log.e(TAG, "Fehler beim Speichern des Yields: " + e.getMessage()));
        }
    }

    // Holt alle Pilze für einen Benutzer
    public void getMushrooms(String userId, FirestoreCallback<List<Mushroom>> callback) {
        db.collection("Users")
                .document(userId)
                .collection("Mushrooms")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<Mushroom> mushrooms = task.getResult().toObjects(Mushroom.class);
                        callback.onSuccess(mushrooms);
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    // Holt die Erträge eines bestimmten Pilzes
    public void getYields(String userId, String mushroomName, FirestoreCallback<List<Yield>> callback) {
        db.collection("Users")
                .document(userId)
                .collection("Mushrooms")
                .document(mushroomName)
                .collection("Yields")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<Yield> yields = task.getResult().toObjects(Yield.class);
                        callback.onSuccess(yields);
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    // Löscht einen Pilz und seine Erträge
    public void deleteMushroom(String userId, String mushroomName) {
        db.collection("Users")
                .document(userId)
                .collection("Mushrooms")
                .document(mushroomName)
                .delete()
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Pilz gelöscht: " + mushroomName))
                .addOnFailureListener(e -> Log.e(TAG, "Fehler beim Löschen des Pilzes: " + e.getMessage()));
    }

    // Interface für Callback-Funktionen
    public interface FirestoreCallback<T> {
        void onSuccess(T result);
        void onFailure(Exception e);
    }
}
