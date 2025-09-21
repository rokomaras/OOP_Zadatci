package SOLUTIONS.src.zad_1; // Definišemo paket u kojem se nalazi ova klasa

import java.util.Map;      // Uvozimo Map interface za rad s mapama
import java.util.Objects;  // Uvozimo Objects klasu za helper metode
import java.util.Set;      // Uvozimo Set interface za rad sa skupovima
import java.util.TreeMap;  // Uvozimo TreeMap za sortirane mape
import java.util.TreeSet;  // Uvozimo TreeSet za sortirane skupove

// Klasa LAB_STAFF koja predstavlja osoblje laboratorija organizirano po specijalnostima
public class LAB_STAFF {

    private String labName; // Polje za čuvanje naziva laboratorija
    private TreeMap<String, TreeSet<Specialist>> staffMap; // Mapa koja organizira specijalist po specijalnostima

    // Konstruktor koji prima naziv laboratorija
    public LAB_STAFF(String labName) {
        // Validira da naziv laboratorija nije null ili prazan
        if (labName == null || labName.isBlank()) {
            throw new IllegalArgumentException("Naziv laba ne smije biti null/prazan."); // Baca iznimku ako je naziv neispravan
        }
        this.labName = labName;                      // Postavlja naziv laboratorija
        this.staffMap = new TreeMap<>();            // Inicijalizira praznu mapu za osoblje
    }

    // Getter metoda koja vraća naziv laboratorija
    public String getLabName() {
        return labName; // Vraća vrijednost polja labName
    }

    // Setter metoda koja postavlja novi naziv laboratorija
    public void setLabName(String labName) {
        // Validira da naziv laboratorija nije null ili prazan
        if (labName == null || labName.isBlank()) {
            throw new IllegalArgumentException("Naziv laba ne smije biti null/prazan."); // Baca iznimku ako je naziv neispravan
        }
        this.labName = labName; // Postavlja novi naziv laboratorija
    }

    /**
     * Getter za unutarnju strukturu podataka.
     */
    // Getter metoda koja vraća mapu s osobljem laboratorija
    public TreeMap<String, TreeSet<Specialist>> getStaffMap() {
        return staffMap; // Vraća referencu na unutarnju mapu podataka
    }

    // Setter metoda koja postavlja novu mapu s osobljem
    public void setStaffMap(TreeMap<String, TreeSet<Specialist>> staffMap) {
        // Provjerava da nova mapa nije null i postavlja je
        this.staffMap = Objects.requireNonNull(staffMap, "staffMap ne smije biti null.");
    }

    // Metoda za dodavanje specijalista u laboratorij
    public boolean addSpecialistToLab(Specialist specialist) {
        // Provjerava da specijalist nije null
        if (specialist == null) {
            throw new IllegalArgumentException("specialist ne smije biti null."); // Baca iznimku ako je specijalist null
        }
        String spec = specialist.getSpeciality(); // Dohvaća specijalnost specijalista
        // Provjerava da specijalnost nije null ili prazna
        if (spec == null || spec.isBlank()) {
            throw new IllegalArgumentException("Speciality ne smije biti null/prazan."); // Baca iznimku ako je specijalnost neispravna
        }
        // Dohvaća ili kreira novi skup za datu specijalnost
        TreeSet<Specialist> set = staffMap.computeIfAbsent(spec, k -> new TreeSet<>());
        return set.add(specialist); // Pokušava dodati specijalista u skup i vraća rezultat
    }

    // Metoda za dodavanje svih specijalista iz vanjske mape
    public int addAllFromTreeMap(Map<String, ? extends Set<Specialist>> external) {
        if (external == null) return 0; // Vraća 0 ako je vanjska mapa null
        int added = 0; // Brojač dodanih specijalista
        // Iterira kroz sve unose u vanjskoj mapi
        for (Map.Entry<String, ? extends Set<Specialist>> e : external.entrySet()) {
            String speciality = e.getKey();     // Dohvaća specijalnost
            Set<Specialist> incoming = e.getValue(); // Dohvaća skup specijalista
            // Preskače ako je skup null ili prazan
            if (incoming == null || incoming.isEmpty()) continue;

            // Dohvaća ili kreira cilni skup za specijalnost
            TreeSet<Specialist> target = staffMap.computeIfAbsent(speciality, k -> new TreeSet<>());
            // Iterira kroz sve specijalist u dolaznom skupu
            for (Specialist s : incoming) {
                if (s == null) continue; // Preskače null specijalist
                // Pokušava dodati specijalista i uvećava brojač ako je uspješno
                if (target.add(s)) {
                    added++; // Uvećava brojač dodanih specijalista
                }
            }
        }
        return added; // Vraća ukupan broj dodanih specijalista
    }

    // Prepisuje toString metodu za prilagođeni prikaz laboratorija
    @Override
    public String toString() {
        // Kreira StringBuilder za formatiranje izlaza
        StringBuilder sb = new StringBuilder("LAB '")
                .append(labName)     // Dodaje naziv laboratorija
                .append("'\n");      // Dodaje novi red
        // Provjerava je li mapa osoblja prazna
        if (staffMap.isEmpty()) {
            sb.append("(nema članova)\n"); // Dodaje poruku da nema članova
            return sb.toString();           // Vraća formatiran string
        }
        // Iterira kroz sve specijalnosti u mapi
        for (Map.Entry<String, TreeSet<Specialist>> e : staffMap.entrySet()) {
            sb.append(e.getKey()).append(":\n"); // Dodaje naziv specijalnosti
            TreeSet<Specialist> set = e.getValue(); // Dohvaća skup specijalista
            // Provjerava je li skup null ili prazan
            if (set == null || set.isEmpty()) {
                sb.append("  - (nema članova)\n"); // Dodaje poruku da nema članova u toj specijalnosti
                continue; // Prelazi na sljedeću specijalnost
            }
            // Iterira kroz sve specijalist u skupu
            for (Specialist s : set) {
                sb.append("  - ").append(s).append("\n"); // Dodaje specijalista u formatiran ispis
            }
        }
        return sb.toString(); // Vraća potpun formatiran string
    }
}