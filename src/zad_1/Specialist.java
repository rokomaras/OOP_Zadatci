package SOLUTIONS.src.zad_1; // Definišemo paket u kojem se nalazi ova klasa

import java.io.Serializable; // Uvozimo Serializable interface za mogućnost serijalizacije
import java.util.Objects;     // Uvozimo Objects klasu za helper metode

// Klasa Specialist koja nasljeđuje od Person i implementira Comparable i Serializable
public class Specialist extends Person implements Comparable<Specialist>, Serializable {

    private static final long serialVersionUID = 1L; // Konstanta za verziju serijalizacije

    private final String speciality; // Nepromjenjivo polje za čuvanje specijalnosti

    // Konstruktor koji prima ime i specijalnost
    public Specialist(String name, String speciality) {
        super(name); // Poziva konstruktor roditeljske klase Person
        // Provjerava da specijalnost nije null ili prazna
        if (speciality == null || speciality.isBlank()) {
            throw new IllegalArgumentException("Speciality ne smije biti null/prazan."); // Baca iznimku ako je specijalnost neispravna
        }
        this.speciality = speciality; // Postavlja vrijednost specijalnosti
    }

    // Getter metoda koja vraća specijalnost
    public String getSpeciality() {
        return speciality; // Vraća vrijednost polja speciality
    }

    // Prepisuje equals metodu za usporedbu objekata
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Vraća true ako su objekti isti (ista referenca)
        if (!(o instanceof Specialist)) return false; // Vraća false ako objekt nije tipa Specialist
        Specialist that = (Specialist) o; // Kasta objekt u Specialist
        // Uspoređuje ID, ime i specijalnost
        return this.getID() == that.getID()
                && Objects.equals(this.getName(), that.getName())
                && Objects.equals(this.speciality, that.speciality);
    }

    // Prepisuje hashCode metodu za generiranje hash koda
    @Override
    public int hashCode() {
        return Objects.hash(getID(), getName(), speciality); // Generirae hash kod na osnovu ID-a, imena i specijalnosti
    }

    // Implementira compareTo metodu za sortiranje
    @Override
    public int compareTo(Specialist o) {
        // Redoslijed sortiranja: id -> name -> speciality
        int c = Integer.compare(this.getID(), o.getID()); // Uspoređuje ID-ove
        if (c != 0) return c; // Ako su ID-ovi različiti, vraća rezultat usporedbe
        c = this.getName().compareTo(o.getName()); // Uspoređuje imena
        if (c != 0) return c; // Ako su imena različita, vraća rezultat usporedbe
        return this.speciality.compareTo(o.speciality); // Uspoređuje specijalnosti
    }

    // Prepisuje toString metodu za prilagođeni prikaz objekta
    @Override
    public String toString() {
        return String.format("%s, Speciality: %s", super.toString(), speciality); // Poziva toString roditeljske klase i dodaje specijalnost
    }
}