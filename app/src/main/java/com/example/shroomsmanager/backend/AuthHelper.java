package com.example.shroomsmanager.backend;
// Firebase-Authentifizierungsbibliothek importieren
import com.google.firebase.auth.FirebaseAuth;

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
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        System.out.println("Benutzer erfolgreich registriert: " + auth.getCurrentUser().getEmail());
                    } else {
                        System.err.println("Fehler bei der Registrierung: " + task.getException().getMessage());
                    }
                });
    }

    // Methode zur Anmeldung eines Benutzers mit E-Mail und Passwort
    public void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        System.out.println("Erfolgreich angemeldet: " + auth.getCurrentUser().getEmail());
                    } else {
                        System.err.println("Fehler bei der Anmeldung: " + task.getException().getMessage());
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

    // Methode zum Zurücksetzen des Passworts für eine angegebene E-Mail
    public void resetPassword(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        System.out.println("Passwort-Zurücksetzen-E-Mail gesendet an: " + email);
                    } else {
                        System.err.println("Fehler beim Senden der Zurücksetzen-E-Mail: " + task.getException().getMessage());
                    }
                });
    }
}