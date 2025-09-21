package SOLUTIONS.src.zad_2;

import java.io.Serializable;
import java.util.Objects;

public class Programmer implements Serializable, Comparable<Programmer> {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final String email;
    private final Language language;
    private final EmploymentType employmentType;

    public Programmer(String name, String email, Language language, EmploymentType employmentType) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Ime je obavezno.");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email je obavezan.");
        if (language == null) throw new IllegalArgumentException("Programski jezik je obavezan.");
        if (employmentType == null) throw new IllegalArgumentException("Tip zaposlenja je obavezan.");
        this.name = name.trim();
        this.email = email.trim();
        this.language = language;
        this.employmentType = employmentType;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Language getLanguage() {
        return language;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Programmer)) return false;
        Programmer that = (Programmer) o;
        return Objects.equals(name, that.name)
                && Objects.equals(email, that.email)
                && language == that.language
                && employmentType == that.employmentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, language, employmentType);
    }

    @Override
    public int compareTo(Programmer o) {
        int c = this.name.compareToIgnoreCase(o.name);
        if (c != 0) return c;
        c = this.email.compareToIgnoreCase(o.email);
        if (c != 0) return c;
        c = this.language.compareTo(o.language);
        if (c != 0) return c;
        return this.employmentType.compareTo(o.employmentType);
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s", name, email, language.getDisplayName(), employmentType.getDisplayName());
    }
}