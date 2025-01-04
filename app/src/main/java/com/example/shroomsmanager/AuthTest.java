package com.example.shroomsmanager;

public class AuthTest {
    public static void main(String[] args) {
        // Instanz der AuthHelper-Klasse
        AuthHelper authHelper = new AuthHelper();

        // Testdaten
        String testEmail = "test@example.com";
        String testPassword = "securePassword123";

        // Test Registrierung
        authHelper.registerUser(testEmail, testPassword);

        // Test Anmeldung
        authHelper.loginUser(testEmail, testPassword);

        // Optional: Abmelden testen
        authHelper.logoutUser();
    }
}
