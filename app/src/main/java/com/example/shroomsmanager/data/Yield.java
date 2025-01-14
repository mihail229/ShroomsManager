package com.example.shroomsmanager.data;

import androidx.annotation.NonNull;

// Firestore-kompatible Klasse zur Speicherung von Erträgen
public class Yield {

    // Felder müssen öffentlich oder über Getter zugänglich sein
    private String date;
    private double amount;

    // Leerer Standard-Konstruktor (erforderlich für Firestore)
    public Yield() {
        amount = 500;
        // Default-Werte, falls benötigt
    }

    // Konstruktor mit Parametern
    public Yield(String date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    // Getter für Firestore
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @NonNull
    @Override
    public String toString() {
        return "Date: " + getDate() + " Amount: " + getAmount();
    }
}
