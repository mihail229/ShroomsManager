package com.example.shroomsmanager.backend;

// Firebase-Authentifizierungsbibliothek importieren
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class AuthHelper {

    private final FirebaseAuth auth;

    // Konstruktor, Initialisiert FirebaseAuth
    public AuthHelper() {
        auth = FirebaseAuth.getInstance();
    }

    /** Methode zur Benutzerregistrierung mit E-Mail und Passwort
     Verwendet Firebase-Methoden */
    public void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println("Benutzer erfolgreich registriert: " + auth.getCurrentUser().getEmail());
                        } else {
                            System.err.println("Fehler bei der Registrierung: " + task.getException().getMessage());
                        }
                    }
                });
    }

    // Methode zur Anmeldung eines Benutzers mit E-Mail und Passwort
    public void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println("Erfolgreich angemeldet: " + auth.getCurrentUser().getEmail());
                        } else {
                            System.err.println("Fehler bei der Anmeldung: " + task.getException().getMessage());
                        }
                    }
                });
    }

    // Methode zum Abmelden des aktuellen Benutzers
    public void logoutUser() {
        auth.signOut();
        System.out.println("Benutzer erfolgreich abgemeldet.");
    }

    // Methode zum Abrufen der Email des aktuellen Benutzers
    public String getCurrentUserEmail() {
        if (auth.getCurrentUser() != null) {
            return auth.getCurrentUser().getEmail();
        } else {
            System.out.println("Kein Benutzer angemeldet.");
            return null;
        }
    }
}