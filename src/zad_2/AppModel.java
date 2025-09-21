package SOLUTIONS.src.zad_2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class AppModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private TreeMap<Language, TreeSet<Programmer>> byLanguage = new TreeMap<>();

    public AppModel() {
        for (Language lang : Language.values()) {
            byLanguage.put(lang, new TreeSet<>());
        }
        System.out.println("[MODEL] Inicijaliziran prazan model s jezicima: " + Arrays.toString(Language.values()));
    }

    public synchronized boolean addProgrammer(Programmer p) {
        Objects.requireNonNull(p);
        System.out.println("[MODEL] Zahtjev za dodavanje: " + p);
        boolean added = byLanguage.computeIfAbsent(p.getLanguage(), k -> new TreeSet<>()).add(p);
        System.out.println("[MODEL] Rezultat dodavanja: " + (added ? "DODANO" : "DUPLIKAT"));
        return added;
    }

    public synchronized List<Programmer> getAllProgrammers() {
        return byLanguage.values().stream()
                .flatMap(Set::stream)
                .sorted()
                .collect(Collectors.toUnmodifiableList());
    }

    public synchronized List<Programmer> getByLanguage(Language language) {
        TreeSet<Programmer> set = byLanguage.get(language);
        if (set == null) return List.of();
        return new ArrayList<>(set);
    }

    public synchronized void clearAll() {
        System.out.println("[MODEL] Brisanje svih podataka iz modela.");
        for (Language lang : Language.values()) {
            byLanguage.computeIfAbsent(lang, k -> new TreeSet<>()).clear();
        }
    }

    public synchronized void replaceAll(TreeMap<Language, TreeSet<Programmer>> newMap) {
        Objects.requireNonNull(newMap);
        byLanguage = new TreeMap<>(newMap);
        for (Language lang : Language.values()) {
            byLanguage.computeIfAbsent(lang, k -> new TreeSet<>());
        }
        System.out.println("[MODEL] Zamjena podataka u modelu završena. Ukupno: " + countTotal() + " programera.");
    }

    public synchronized TreeMap<Language, TreeSet<Programmer>> snapshot() {
        TreeMap<Language, TreeSet<Programmer>> copy = new TreeMap<>();
        for (Map.Entry<Language, TreeSet<Programmer>> e : byLanguage.entrySet()) {
            copy.put(e.getKey(), new TreeSet<>(e.getValue()));
        }
        return copy;
    }

    private int countTotal() {
        return byLanguage.values().stream().mapToInt(Set::size).sum();
    }

    public static final Path DEFAULT_PATH = Paths.get("DATA", "programeri.bin");

    public synchronized void saveToFile() throws IOException {
        saveToFile(DEFAULT_PATH);
    }

    public synchronized void saveToFile(Path path) throws IOException {
        Objects.requireNonNull(path);
        Files.createDirectories(path.getParent());
        int total = countTotal();
        System.out.println("[MODEL] Spremanje " + total + " programera u datoteku: " + path.toAbsolutePath());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            oos.writeObject(byLanguage);
        }
        System.out.println("[MODEL] Spremanje završeno.");
    }

    @SuppressWarnings("unchecked")
    public synchronized void loadFromFile() throws IOException {
        loadFromFile(DEFAULT_PATH);
    }

    @SuppressWarnings("unchecked")
    public synchronized void loadFromFile(Path path) throws IOException {
        Objects.requireNonNull(path);
        System.out.println("[MODEL] Učitavanje iz datoteke: " + path.toAbsolutePath());
        if (!Files.exists(path)) {
            System.out.println("[MODEL] Datoteka ne postoji. Inicijaliziram prazan model.");
            replaceAll(new TreeMap<>());
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            Object obj = ois.readObject();
            if (!(obj instanceof TreeMap)) {
                throw new IOException("Neispravan format datoteke.");
            }
            TreeMap<Language, TreeSet<Programmer>> loaded = (TreeMap<Language, TreeSet<Programmer>>) obj;
            replaceAll(loaded);
            System.out.println("[MODEL] Učitavanje završeno. Ukupno: " + countTotal() + " programera.");
        } catch (ClassNotFoundException e) {
            throw new IOException("Nedostaju klase pri učitavanju: " + e.getMessage(), e);
        }
    }
}