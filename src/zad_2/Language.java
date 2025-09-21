package SOLUTIONS.src.zad_2;

import java.io.Serializable;

public enum Language implements Serializable {
    PYTHON("Python"),
    JAVA("JAVA"),
    JAVASCRIPT("JavaScript"),
    RUBY("Ruby"),
    PERL("Perl"),
    SWIFT("Swift"),
    RUST("RUST");

    private final String displayName;

    Language(String displayName) {
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