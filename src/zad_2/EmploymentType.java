package SOLUTIONS.src.zad_2;

import java.io.Serializable;

public enum EmploymentType implements Serializable {
    FULL_TIME("Puno radno vrijeme"),
    PART_TIME("Pola radnog vremena");

    private final String displayName;

    EmploymentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}