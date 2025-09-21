package SOLUTIONS.src.zad_2; // Definišemo paket u kojem se nalazi ova klasa

import java.io.Serializable; // Uvozimo Serializable interface za mogućnost serijalizacije
import java.util.Objects;     // Uvozimo Objects klasu za helper metode

// Klasa Programmer koja predstavlja programera s osnovnim podacima
public class Programmer implements Serializable, Comparable<Programmer> {
    private static final long serialVersionUID = 1L; // Konstanta za verziju serijalizacije

    private final String name;              // Nepromjenjivo polje za čuvanje imena programera
    private final String email;             // Nepromjenjivo polje za čuvanje email adrese
    private final Language language;        // Nepromjenjivo polje za čuvanje programskog jezika
    private final EmploymentType employmentType; // Nepromjenjivo polje za čuvanje tipa zaposlenja

    // Konstruktor koji prima sve potrebne podatke o programeru
    public Programmer(String name, String email, Language language, EmploymentType employmentType) {
        // Validira da ime nije null ili prazno
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Ime je obavezno.");
        // Validira da email nije null ili prazan
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email je obavezan.");
        // Validira da programski jezik nije null
        if (language == null) throw new IllegalArgumentException("Programski jezik je obavezan.");
        // Validira da tip zaposlenja nije null
        if (employmentType == null) throw new IllegalArgumentException("Tip zaposlenja je obavezan.");
        this.name = name.trim();                 // Postavlja ime nakon uklanjanja razmaka
        this.email = email.trim();               // Postavlja email nakon uklanjanja razmaka
        this.language = language;                // Postavlja programski jezik
        this.employmentType = employmentType;    // Postavlja tip zaposlenja
    }

    // Getter metoda koja vraća ime programera
    public String getName() {
        return name; // Vraća vrijednost polja name
    }

    // Getter metoda koja vraća email adresu programera
    public String getEmail() {
        return email; // Vraća vrijednost polja email
    }

    // Getter metoda koja vraća programski jezik programera
    public Language getLanguage() {
        return language; // Vraća vrijednost polja language
    }

    // Getter metoda koja vraća tip zaposlenja programera
    public EmploymentType getEmploymentType() {
        return employmentType; // Vraća vrijednost polja employmentType
    }

    // Prepisuje equals metodu za usporedbu objekata
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Vraća true ako su objekti isti (ista referenca)
        if (!(o instanceof Programmer)) return false; // Vraća false ako objekt nije tipa Programmer
        Programmer that = (Programmer) o; // Kasta objekt u Programmer
        // Uspoređuje sva polja za jednakost
        return Objects.equals(name, that.name)
                && Objects.equals(email, that.email)
                && language == that.language
                && employmentType == that.employmentType;
    }

    // Prepisuje hashCode metodu za generiranje hash koda
    @Override
    public int hashCode() {
        return Objects.hash(name, email, language, employmentType); // Generira hash kod na osnovu svih polja
    }

    // Implementira compareTo metodu za sortiranje programera
    @Override
    public int compareTo(Programmer o) {
        int c = this.name.compareToIgnoreCase(o.name); // Uspoređuje imena (ignorirajući velika/mala slova)
        if (c != 0) return c; // Ako su imena različita, vraća rezultat usporedbe
        c = this.email.compareToIgnoreCase(o.email); // Uspoređuje email adrese (ignorirajući velika/mala slova)
        if (c != 0) return c; // Ako su email adrese različite, vraća rezultat usporedbe
        c = this.language.compareTo(o.language); // Uspoređuje programske jezike
        if (c != 0) return c; // Ako su jezici različiti, vraća rezultat usporedbe
        return this.employmentType.compareTo(o.employmentType); // Uspoređuje tipove zaposlenja
    }

    // Prepisuje toString metodu za prilagođeni prikaz objekta
    @Override
    public String toString() {
        // Formatira string s imenom, emailom, jezikom i tipom zaposlenja
        return String.format("%s | %s | %s | %s", name, email, language.getDisplayName(), employmentType.getDisplayName());
    }
}