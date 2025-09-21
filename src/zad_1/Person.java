package SOLUTIONS.src.zad_1; // Definišemo paket u kojem se nalazi ova klasa

import java.io.Serializable; // Uvozimo Serializable interface za mogućnost serijalizacije

// Klasa Person koja predstavlja osnovne informacije o osobi
public class Person implements Serializable {

    private static final long serialVersionUID = 1L; // Konstanta za verziju serijalizacije

    private String name; // Polje za čuvanje imena osobe
    private int id;      // Polje za čuvanje jedinstvenog ID-a osobe
    private static int cntId = 40; // Statično polje za brojanje ID-ova, počinje od 40

    // Konstruktor koji prima ime osobe
    public Person(String name) {
        this.name = name;    // Postavlja ime osobe
        this.id = ++cntId;   // Dodjeljuje sljedeći dostupni ID i uvećava brojač
    }

    // Getter metoda koja vraća ime osobe
    public String getName() {
        return name; // Vraća vrijednost polja name
    }

    // Getter metoda koja vraća ID osobe
    public int getID() {
        return id; // Vraća vrijednost polja id
    }

    // Prepisuje toString metodu za prilagođeni prikaz objekta
    @Override
    public String toString() {
        return String.format("Person ID: %d, Name: %s", id, name); // Formatira i vraća string s ID-om i imenom
    }
}