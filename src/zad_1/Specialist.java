package SOLUTIONS.src.zad_1;

import java.io.Serializable;
import java.util.Objects;

public class Specialist extends Person implements Comparable<Specialist>, Serializable {

    private static final long serialVersionUID = 1L;

    private final String speciality;

    public Specialist(String name, String speciality) {
        super(name);
        if (speciality == null || speciality.isBlank()) {
            throw new IllegalArgumentException("Speciality ne smije biti null/prazan.");
        }
        this.speciality = speciality;
    }

    public String getSpeciality() {
        return speciality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Specialist)) return false;
        Specialist that = (Specialist) o;
        return this.getID() == that.getID()
                && Objects.equals(this.getName(), that.getName())
                && Objects.equals(this.speciality, that.speciality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID(), getName(), speciality);
    }

    @Override
    public int compareTo(Specialist o) {
        // Redoslijed: id -> name -> speciality
        int c = Integer.compare(this.getID(), o.getID());
        if (c != 0) return c;
        c = this.getName().compareTo(o.getName());
        if (c != 0) return c;
        return this.speciality.compareTo(o.speciality);
    }

    @Override
    public String toString() {
        return String.format("%s, Speciality: %s", super.toString(), speciality);
    }
}