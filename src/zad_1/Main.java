package SOLUTIONS.src.zad_1; // Definišemo paket u kojem se nalazi ova klasa

import java.io.IOException; // Uvozimo IOException za rukovanje I/O greškama
import java.util.TreeMap;   // Uvozimo TreeMap za sortirane mape
import java.util.TreeSet;   // Uvozimo TreeSet za sortirane skupove

// Glavna klasa koja demonstrira rad s laboratorijem i specijalistima
public class Main {

    // Glavna metoda koja se izvršava pri pokretanju programa
    public static void main(String[] args) throws IOException {

        // Kreiramo specijalist za umjetnu inteligenciju
        Specialist sAI1 = new Specialist("SPEC_1", "AISpecialist");
        Specialist sAI2 = new Specialist("SPEC_4", "AISpecialist");

        // Kreiramo specijalist za duboko učenje
        Specialist sDL1 = new Specialist("SPEC_2", "DLSpecialist");
        Specialist sDL2 = new Specialist("SPEC_3", "DLSpecialist");

        // Kreiramo specijalist za obradu prirodnog jezika
        Specialist sNLP1 = new Specialist("SPEC_5", "NLPSpecialist");
        Specialist sNLP2 = new Specialist("SPEC_7", "NLPSpecialist");

        // Kreiramo novi laboratorij s nazivom
        LAB_STAFF lab = new LAB_STAFF("AI Research Lab");

        // Dodajemo specijalist u laboratorij
        lab.addSpecialistToLab(sAI1);  // Dodaje prvog AI specijalista
        lab.addSpecialistToLab(sAI2);  // Dodaje drugog AI specijalista
        lab.addSpecialistToLab(sDL1);  // Dodaje prvog DL specijalista
        lab.addSpecialistToLab(sDL2);  // Dodaje drugog DL specijalista
        lab.addSpecialistToLab(sNLP1); // Dodaje prvog NLP specijalista
        lab.addSpecialistToLab(sNLP2); // Dodaje drugog NLP specijalista

        // Ispisuje početni popis članova koristeći toString metodu laboratorija
        System.out.println("=== Početni izlist članova (LAB.toString) ===");
        System.out.println(lab); // Poziva toString metodu LAB_STAFF klase

        // Ispisuje početni popis članova koristeći AUX_CLS helper metodu
        System.out.println("=== Početni izlist članova (AUX_CLS.listDataFromTreeMap) ===");
        AUX_CLS.listDataFromTreeMap(lab.getStaffMap()); // Poziva helper metodu za ispis

        // Pokušava dodati duplikat (postojećeg člana) i provjerava rezultat
        boolean addedDuplicate = lab.addSpecialistToLab(sAI1);
        System.out.println("Pokušaj ponovnog dodavanja postojećeg člana (očekivano false): " + addedDuplicate);

        // Sprema podatke laboratorija u binarnu datoteku
        AUX_CLS.saveTreeMapDataToBinFile(lab.getStaffMap());
        System.out.println("Podaci spremljeni u binarnu datoteku.");

        // Učitava podatke iz binarne datoteke
        TreeMap<String, TreeSet<Specialist>> loaded = AUX_CLS.loadTreeMapDataFromBinFile();
        // Dodaje učitane podatke u laboratorij i vraća broj novo dodanih članova
        int newlyAdded = lab.addAllFromTreeMap(loaded);
        System.out.println("Nakon učitavanja i dodavanja iz bin datoteke, novo dodano: " + newlyAdded);

        // Ispisuje završni popis članova nakon svih operacija
        System.out.println("=== Završni izlist članova (LAB.toString) ===");
        System.out.println(lab); // Poziva toString metodu LAB_STAFF klase
    }
}
