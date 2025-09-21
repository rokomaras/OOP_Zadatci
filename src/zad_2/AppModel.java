package SOLUTIONS.src.zad_2; // Definišemo paket u kojem se nalazi ova klasa

import java.io.*;                   // Uvozimo sve I/O klase za rad s datotekama
import java.nio.file.Files;         // Uvozimo Files klasu za rad s datotekama
import java.nio.file.Path;          // Uvozimo Path interface za rad s putanjama
import java.nio.file.Paths;         // Uvozimo Paths klasu za kreiranje putanja
import java.util.*;                 // Uvozimo sve kolekcije i utility klase
import java.util.stream.Collectors; // Uvozimo Collectors za stream operacije

// Model klasa koja upravlja podacima o programerima organiziranima po jezicima
public class AppModel implements Serializable {
    private static final long serialVersionUID = 1L; // Konstanta za verziju serijalizacije

    // Mapa koja organizira programere po programskim jezicima
    private TreeMap<Language, TreeSet<Programmer>> byLanguage = new TreeMap<>();

    // Konstruktor koji inicijalizira prazan model
    public AppModel() {
        // Iterira kroz sve dostupne programske jezike
        for (Language lang : Language.values()) {
            byLanguage.put(lang, new TreeSet<>()); // Kreira prazan skup za svaki jezik
        }
        // Ispisuje poruku o inicijalizaciji s popisom jezika
        System.out.println("[MODEL] Inicijaliziran prazan model s jezicima: " + Arrays.toString(Language.values()));
    }

    // Sinkronizirana metoda za dodavanje programera u model
    public synchronized boolean addProgrammer(Programmer p) {
        Objects.requireNonNull(p); // Provjerava da programer nije null
        System.out.println("[MODEL] Zahtjev za dodavanje: " + p); // Ispisuje zahtjev za dodavanje
        // Dohvaća ili kreira skup za jezik programera i pokušava ga dodati
        boolean added = byLanguage.computeIfAbsent(p.getLanguage(), k -> new TreeSet<>()).add(p);
        // Ispisuje rezultat dodavanja
        System.out.println("[MODEL] Rezultat dodavanja: " + (added ? "DODANO" : "DUPLIKAT"));
        return added; // Vraća true ako je programer dodan, false ako već postoji
    }

    // Sinkronizirana metoda koja vraća sve programere sortirane
    public synchronized List<Programmer> getAllProgrammers() {
        return byLanguage.values().stream()  // Kreira stream svih skupova programera
                .flatMap(Set::stream)        // Spljošćuje skupove u jedan stream
                .sorted()                    // Sortira programere
                .collect(Collectors.toUnmodifiableList()); // Kolekcira u nepromjenjivi popis
    }

    // Sinkronizirana metoda koja vraća programera za određeni jezik
    public synchronized List<Programmer> getByLanguage(Language language) {
        TreeSet<Programmer> set = byLanguage.get(language); // Dohvaća skup za jezik
        if (set == null) return List.of(); // Vraća prazan popis ako nema programera
        return new ArrayList<>(set); // Vraća novu listu s kopiranim sadržajem
    }

    // Sinkronizirana metoda za brisanje svih podataka iz modela
    public synchronized void clearAll() {
        System.out.println("[MODEL] Brisanje svih podataka iz modela."); // Ispisuje poruku o brisanju
        // Iterira kroz sve jezike i briše sadržaj skupova
        for (Language lang : Language.values()) {
            byLanguage.computeIfAbsent(lang, k -> new TreeSet<>()).clear(); // Briše sadržaj skupa
        }
    }

    // Sinkronizirana metoda za zamjenu svih podataka u modelu
    public synchronized void replaceAll(TreeMap<Language, TreeSet<Programmer>> newMap) {
        Objects.requireNonNull(newMap); // Provjerava da nova mapa nije null
        byLanguage = new TreeMap<>(newMap); // Kreira kopiju nove mape
        // Osigurava da postoje skupovi za sve jezike
        for (Language lang : Language.values()) {
            byLanguage.computeIfAbsent(lang, k -> new TreeSet<>()); // Kreira prazan skup ako ne postoji
        }
        // Ispisuje poruku o završetku zamjene s brojem programera
        System.out.println("[MODEL] Zamjena podataka u modelu završena. Ukupno: " + countTotal() + " programera.");
    }

    // Sinkronizirana metoda koja kreira snimku trenutnog stanja modela
    public synchronized TreeMap<Language, TreeSet<Programmer>> snapshot() {
        TreeMap<Language, TreeSet<Programmer>> copy = new TreeMap<>(); // Kreira novu mapu
        // Iterira kroz sve unose i kreira kopije
        for (Map.Entry<Language, TreeSet<Programmer>> e : byLanguage.entrySet()) {
            copy.put(e.getKey(), new TreeSet<>(e.getValue())); // Kreira kopiju skupa
        }
        return copy; // Vraća kopiju podataka
    }

    // Privatna metoda za brojanje ukupnog broja programera
    private int countTotal() {
        return byLanguage.values().stream().mapToInt(Set::size).sum(); // Sumira veličine svih skupova
    }

    // Konstanta koja definira zadanu putanju do datoteke
    public static final Path DEFAULT_PATH = Paths.get("DATA", "programeri.bin");

    // Sinkronizirana metoda za spremanje u zadanu datoteku
    public synchronized void saveToFile() throws IOException {
        saveToFile(DEFAULT_PATH); // Poziva glavnu metodu s zadanom putanjom
    }

    // Sinkronizirana metoda za spremanje podataka u specificiranu datoteku
    public synchronized void saveToFile(Path path) throws IOException {
        Objects.requireNonNull(path); // Provjerava da putanja nije null
        Files.createDirectories(path.getParent()); // Kreira direktorije ako ne postoje
        int total = countTotal(); // Dohvaća ukupan broj programera
        // Ispisuje poruku o spremanju s putanjom datoteke
        System.out.println("[MODEL] Spremanje " + total + " programera u datoteku: " + path.toAbsolutePath());
        // Koristi try-with-resources za automatsko zatvaranje stream-a
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            oos.writeObject(byLanguage); // Serijalizira i sprema mapu u datoteku
        }
        System.out.println("[MODEL] Spremanje završeno."); // Ispisuje poruku o završetku
    }

    // Potiskuje upozorenja kompajilatora za neprovjerne cast operacije
    @SuppressWarnings("unchecked")
    // Sinkronizirana metoda za učitavanje iz zadane datoteke
    public synchronized void loadFromFile() throws IOException {
        loadFromFile(DEFAULT_PATH); // Poziva glavnu metodu s zadanom putanjom
    }

    // Potiskuje upozorenja kompajilatora za neprovjerne cast operacije
    @SuppressWarnings("unchecked")
    // Sinkronizirana metoda za učitavanje podataka iz specificirane datoteke
    public synchronized void loadFromFile(Path path) throws IOException {
        Objects.requireNonNull(path); // Provjerava da putanja nije null
        // Ispisuje poruku o učitavanju s putanjom datoteke
        System.out.println("[MODEL] Učitavanje iz datoteke: " + path.toAbsolutePath());
        // Provjerava postoji li datoteka
        if (!Files.exists(path)) {
            System.out.println("[MODEL] Datoteka ne postoji. Inicijaliziram prazan model."); // Poruka da datoteka ne postoji
            replaceAll(new TreeMap<>()); // Postavlja prazan model
            return; // Izlazi iz metode
        }
        // Koristi try-with-resources za automatsko zatvaranje stream-a
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            Object obj = ois.readObject(); // Čita objekt iz datoteke
            // Provjerava je li učitani objekt TreeMap
            if (!(obj instanceof TreeMap)) {
                throw new IOException("Neispravan format datoteke."); // Baca iznimku za neispravan format
            }
            // Kasta objekt u odgovarajući tip
            TreeMap<Language, TreeSet<Programmer>> loaded = (TreeMap<Language, TreeSet<Programmer>>) obj;
            replaceAll(loaded); // Zamjenjuje postojeće podatke učitanima
            // Ispisuje poruku o završetku s brojem programera
            System.out.println("[MODEL] Učitavanje završeno. Ukupno: " + countTotal() + " programera.");
        } catch (ClassNotFoundException e) {
            // Hvata iznimku ako nedostaju klase tijekom deserijalizacije
            throw new IOException("Nedostaju klase pri učitavanju: " + e.getMessage(), e);
        }
    }
}