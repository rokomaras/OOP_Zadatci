package SOLUTIONS.src.zad_2; // Definišemo paket u kojem se nalazi ova klasa

import java.io.Serializable; // Uvozimo Serializable interface za mogućnost serijalizacije

// Enum koji predstavlja različite tipove zaposlenja
public enum EmploymentType implements Serializable {
    FULL_TIME("Puno radno vrijeme"),  // Konstanta za puno radno vrijeme
    PART_TIME("Pola radnog vremena"); // Konstanta za pola radnog vremena

    private final String displayName; // Privatno, nepromjenjivo polje za čuvanje naziva tipa zaposlenja

    // Konstruktor koji prima naziv tipa zaposlenja za prikaz
    EmploymentType(String displayName) {
        this.displayName = displayName; // Postavlja vrijednost naziva tipa zaposlenja
    }

    // Getter metoda koja vraća naziv tipa zaposlenja za prikaz
    public String getDisplayName() {
        return displayName; // Vraća vrijednost polja displayName
    }

    // Prepisuje toString metodu za prilagođeni prikaz objekta
    @Override
    public String toString() {
        return displayName; // Vraća naziv tipa zaposlenja za prikaz
    }
}