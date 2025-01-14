package com.example.shroomsmanager.data;

import java.util.ArrayList;
import java.util.List;

public class Mushroom {

    private String name;
    private final List<Yield> yields = new ArrayList<>(); // Sichere Initialisierung

    // Standard-Konstruktor (für Firestore erforderlich)
    public Mushroom() {}

    // Konstruktor mit Parametern
    public Mushroom(String name, List<Yield> yields) {
        this.name = name;
        this.yields.addAll(yields); // Hinzufügen der Elemente in die Liste
    }

    // Getter und Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Yield> getYields() {
        return yields;
    }

    // Methode zum Hinzufügen eines einzelnen Ertrags
    public void addYield(Yield yield) {
        this.yields.add(yield);
    }
}
