package SOLUTIONS.src.zad_1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class AUX_CLS {

    private static final Path DEFAULT_DATA_PATH = Paths.get("data", "lab_staff.bin");

    public static void saveTreeMapDataToBinFile(TreeMap<String, TreeSet<Specialist>> map, Path filePath) throws IOException {
        if (map == null) throw new IllegalArgumentException("map ne smije biti null.");
        if (filePath == null) throw new IllegalArgumentException("filePath ne smije biti null.");
        Files.createDirectories(filePath.getParent());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
            oos.writeObject(map);
        }
    }

    public static void saveTreeMapDataToBinFile(TreeMap<String, TreeSet<Specialist>> map) throws IOException {
        saveTreeMapDataToBinFile(map, DEFAULT_DATA_PATH);
    }


    @SuppressWarnings("unchecked")
    public static TreeMap<String, TreeSet<Specialist>> loadTreeMapDataFromBinFile(Path filePath) throws IOException {
        if (filePath == null) throw new IllegalArgumentException("filePath ne smije biti null.");
        if (!Files.exists(filePath)) {
            return new TreeMap<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
            Object obj = ois.readObject();
            if (obj instanceof TreeMap<?, ?>) {
                return (TreeMap<String, TreeSet<Specialist>>) obj;
            } else {
                throw new IOException("Neispravan format datoteke (nije TreeMap).");
            }
        } catch (ClassNotFoundException e) {
            throw new IOException("Nedostaju klase tijekom deserijalizacije: " + e.getMessage(), e);
        }
    }


    public static TreeMap<String, TreeSet<Specialist>> loadTreeMapDataFromBinFile() throws IOException {
        return loadTreeMapDataFromBinFile(DEFAULT_DATA_PATH);
    }


    public static void listDataFromTreeMap(TreeMap<String, TreeSet<Specialist>> map) {
        if (map == null || map.isEmpty()) {
            System.out.println("(nema podataka)");
            return;
        }
        for (Map.Entry<String, TreeSet<Specialist>> e : map.entrySet()) {
            String spec = e.getKey();
            TreeSet<Specialist> set = e.getValue();
            System.out.println(spec + ":");
            if (set == null || set.isEmpty()) {
                System.out.println("  - (nema članova)");
                continue;
            }
            for (Specialist s : set) {
                System.out.println("  - " + s);
            }
        }
    }
}