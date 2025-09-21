package SOLUTIONS.src.zad_1; // Definišemo paket u kojem se nalazi ova klasa

import java.io.IOException;        // Uvozimo IOException za rukovanje I/O greškama
import java.io.ObjectInputStream;  // Uvozimo ObjectInputStream za čitanje objekata iz datoteka
import java.io.ObjectOutputStream; // Uvozimo ObjectOutputStream za pisanje objekata u datoteke
import java.io.Serializable;       // Uvozimo Serializable interface
import java.io.FileInputStream;    // Uvozimo FileInputStream za čitanje datoteka
import java.io.FileOutputStream;   // Uvozimo FileOutputStream za pisanje datoteka
import java.nio.file.Files;        // Uvozimo Files klasu za rad s datotekama
import java.nio.file.Path;         // Uvozimo Path interface za rad s putanjama
import java.nio.file.Paths;        // Uvozimo Paths klasu za kreiranje putanja
import java.util.Map;              // Uvozimo Map interface
import java.util.TreeMap;          // Uvozimo TreeMap za sortirane mape
import java.util.TreeSet;          // Uvozimo TreeSet za sortirane skupove

// Pomoćna klasa (Auxiliary Class) za rad s datotekama i ispisivanje podataka
public class AUX_CLS {

    // Konstanta koja definira zadanu putanju do datoteke s podacima
    private static final Path DEFAULT_DATA_PATH = Paths.get("data", "lab_staff.bin");

    // Metoda za spremanje TreeMap podataka u binarnu datoteku na specificiranoj putanji
    public static void saveTreeMapDataToBinFile(TreeMap<String, TreeSet<Specialist>> map, Path filePath) throws IOException {
        // Provjerava da mapa nije null
        if (map == null) throw new IllegalArgumentException("map ne smije biti null.");
        // Provjerava da putanja datoteke nije null
        if (filePath == null) throw new IllegalArgumentException("filePath ne smije biti null.");
        Files.createDirectories(filePath.getParent()); // Kreira direktorije ako ne postoje
        // Koristi try-with-resources za automatsko zatvaranje stream-a
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
            oos.writeObject(map); // Serijalizira i sprema mapu u datoteku
        }
    }

    // Preopterećena metoda koja koristi zadanu putanju za spremanje
    public static void saveTreeMapDataToBinFile(TreeMap<String, TreeSet<Specialist>> map) throws IOException {
        saveTreeMapDataToBinFile(map, DEFAULT_DATA_PATH); // Poziva glavnu metodu s zadanom putanjom
    }

    // Potiskuje upozorenja kompajilatora za neprovjerne cast operacije
    @SuppressWarnings("unchecked")
    // Metoda za učitavanje TreeMap podataka iz binarne datoteke
    public static TreeMap<String, TreeSet<Specialist>> loadTreeMapDataFromBinFile(Path filePath) throws IOException {
        // Provjerava da putanja datoteke nije null
        if (filePath == null) throw new IllegalArgumentException("filePath ne smije biti null.");
        // Provjerava postoji li datoteka
        if (!Files.exists(filePath)) {
            return new TreeMap<>(); // Vraća praznu mapu ako datoteka ne postoji
        }
        // Koristi try-with-resources za automatsko zatvaranje stream-a
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
            Object obj = ois.readObject(); // Čita objekt iz datoteke
            // Provjerava je li učitani objekt TreeMap
            if (obj instanceof TreeMap<?, ?>) {
                return (TreeMap<String, TreeSet<Specialist>>) obj; // Kasta i vraća objekt
            } else {
                // Baca iznimku ako format datoteke nije isprava
                throw new IOException("Neispravan format datoteke (nije TreeMap).");
            }
        } catch (ClassNotFoundException e) {
            // Hvata iznimku ako nedostaju klase tijekom deserijalizacije
            throw new IOException("Nedostaju klase tijekom deserijalizacije: " + e.getMessage(), e);
        }
    }

    // Preopterećena metoda koja koristi zadanu putanju za učitavanje
    public static TreeMap<String, TreeSet<Specialist>> loadTreeMapDataFromBinFile() throws IOException {
        return loadTreeMapDataFromBinFile(DEFAULT_DATA_PATH); // Poziva glavnu metodu s zadanom putanjom
    }

    // Metoda za ispisivanje sadržaja TreeMap na konzolu
    public static void listDataFromTreeMap(TreeMap<String, TreeSet<Specialist>> map) {
        // Provjerava je li mapa null ili prazna
        if (map == null || map.isEmpty()) {
            System.out.println("(nema podataka)"); // Ispisuje poruku da nema podataka
            return; // Izlazi iz metode
        }
        // Iterira kroz sve unose u mapi
        for (Map.Entry<String, TreeSet<Specialist>> e : map.entrySet()) {
            String spec = e.getKey();           // Dohvaća ključ (specijalnost)
            TreeSet<Specialist> set = e.getValue(); // Dohvaća vrijednost (skup specijalista)
            System.out.println(spec + ":"); // Ispisuje naziv specijalnosti
            // Provjerava je li skup null ili prazan
            if (set == null || set.isEmpty()) {
                System.out.println("  - (nema članova)"); // Ispisuje poruku da nema članova
                continue; // Prelazi na sljedeću specijalnost
            }
            // Iterira kroz sve specijalist u skupu
            for (Specialist s : set) {
                System.out.println("  - " + s); // Ispisuje specijalista s uvlakom
            }
        }
    }
}