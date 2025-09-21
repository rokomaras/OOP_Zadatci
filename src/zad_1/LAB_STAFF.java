package SOLUTIONS.src.zad_1;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class LAB_STAFF {

    private String labName;
    private TreeMap<String, TreeSet<Specialist>> staffMap;

    public LAB_STAFF(String labName) {
        if (labName == null || labName.isBlank()) {
            throw new IllegalArgumentException("Naziv laba ne smije biti null/prazan.");
        }
        this.labName = labName;
        this.staffMap = new TreeMap<>();
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        if (labName == null || labName.isBlank()) {
            throw new IllegalArgumentException("Naziv laba ne smije biti null/prazan.");
        }
        this.labName = labName;
    }

    /**
     * Getter za unutarnju strukturu podataka.
     */
    public TreeMap<String, TreeSet<Specialist>> getStaffMap() {
        return staffMap;
    }


    public void setStaffMap(TreeMap<String, TreeSet<Specialist>> staffMap) {
        this.staffMap = Objects.requireNonNull(staffMap, "staffMap ne smije biti null.");
    }


    public boolean addSpecialistToLab(Specialist specialist) {
        if (specialist == null) {
            throw new IllegalArgumentException("specialist ne smije biti null.");
        }
        String spec = specialist.getSpeciality();
        if (spec == null || spec.isBlank()) {
            throw new IllegalArgumentException("Speciality ne smije biti null/prazan.");
        }
        TreeSet<Specialist> set = staffMap.computeIfAbsent(spec, k -> new TreeSet<>());
        return set.add(specialist);
    }


    public int addAllFromTreeMap(Map<String, ? extends Set<Specialist>> external) {
        if (external == null) return 0;
        int added = 0;
        for (Map.Entry<String, ? extends Set<Specialist>> e : external.entrySet()) {
            String speciality = e.getKey();
            Set<Specialist> incoming = e.getValue();
            if (incoming == null || incoming.isEmpty()) continue;

            TreeSet<Specialist> target = staffMap.computeIfAbsent(speciality, k -> new TreeSet<>());
            for (Specialist s : incoming) {
                if (s == null) continue;
                if (target.add(s)) {
                    added++;
                }
            }
        }
        return added;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("LAB '")
                .append(labName)
                .append("'\n");
        if (staffMap.isEmpty()) {
            sb.append("(nema članova)\n");
            return sb.toString();
        }
        for (Map.Entry<String, TreeSet<Specialist>> e : staffMap.entrySet()) {
            sb.append(e.getKey()).append(":\n");
            TreeSet<Specialist> set = e.getValue();
            if (set == null || set.isEmpty()) {
                sb.append("  - (nema članova)\n");
                continue;
            }
            for (Specialist s : set) {
                sb.append("  - ").append(s).append("\n");
            }
        }
        return sb.toString();
    }
}