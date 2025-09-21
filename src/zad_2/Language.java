package SOLUTIONS.src.zad_2; // Definišemo paket u kojem se nalazi ova klasa

import java.io.Serializable; // Uvozimo Serializable interface za mogućnost serijalizacije

// Enum koji predstavlja različite programske jezike
public enum Language implements Serializable {
    PYTHON("Python"),         // Konstanta za Python programski jezik
    JAVA("JAVA"),            // Konstanta za Java programski jezik
    JAVASCRIPT("JavaScript"), // Konstanta za JavaScript programski jezik
    RUBY("Ruby"),            // Konstanta za Ruby programski jezik
    PERL("Perl"),            // Konstanta za Perl programski jezik
    SWIFT("Swift"),          // Konstanta za Swift programski jezik
    RUST("RUST");            // Konstanta za Rust programski jezik

    private final String displayName; // Privatno, nepromjenjivo polje za čuvanje imena jezika

    // Konstruktor koji prima naziv jezika za prikaz
    Language(String displayName) {
        this.displayName = displayName; // Postavlja vrijednost imena jezika
    }

    // Getter metoda koja vraća naziv jezika za prikaz
    public String getDisplayName() {
        return displayName; // Vraća vrijednost polja displayName
    }

    // Prepisuje toString metodu za prilagođeni prikaz objekta
    @Override
    public String toString() {
        return displayName; // Vraća naziv jezika za prikaz
    }
}