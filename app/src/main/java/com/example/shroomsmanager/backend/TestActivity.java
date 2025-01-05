package com.example.shroomsmanager.backend;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shroomsmanager.R;
import com.example.shroomsmanager.data.Mushroom;
import com.example.shroomsmanager.data.Yield;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        findViewById(R.id.testButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Button Pressed");

                // Test-Daten für Benutzer
                String testEmail = "test@example.com";
                String testPassword = "securePassword123";

                // Instanz der AuthHelper-Klasse
                AuthHelper authHelper = new AuthHelper();

                // Benutzer registrieren
                authHelper.registerUser(testEmail, testPassword);
                //   authHelper.loginUser(testEmail, testPassword);

                // Warten, bis der Benutzer registriert ist (z. B. durch kurze Verzögerung)
                findViewById(R.id.testButton).postDelayed(() -> {
                    // Benutzer-ID abrufen
                    String userId = FirebaseAuth.getInstance().getCurrentUser() != null ?
                            FirebaseAuth.getInstance().getCurrentUser().getUid() : null;

                    if (userId != null) {
                        Log.d(TAG, "onClick: Benutzer erfolgreich registriert! UserID: " + userId);

                        // Erstelle eine Liste von Yield-Objekten
                        List<Yield> testYields = new ArrayList<>();
                        testYields.add(new Yield("01.01.2024", 50.0));
                        testYields.add(new Yield("15.01.2024", 70.5));

                        // Erstelle ein Mushroom-Objekt mit der Liste
                        Mushroom testMushroom = new Mushroom("Champignon", testYields);

                        // Instanz der MushroomFirestoreHelper-Klasse
                        MushroomFirestoreHelper mushroomFirestoreHelper = new MushroomFirestoreHelper();

                        // Pilz speichern
                        mushroomFirestoreHelper.saveMushroom(userId, testMushroom);
                        Log.d(TAG, "onClick: Pilz gespeichert: " + testMushroom.getName());
                    } else {
                        Log.e(TAG, "onClick: Benutzer-ID konnte nicht abgerufen werden.");
                    }
                }, 2000); // 2 Sekunden Verzögerung
            }
        });
    }
}
