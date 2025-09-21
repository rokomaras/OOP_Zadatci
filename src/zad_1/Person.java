package SOLUTIONS.src.zad_1;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private int id;
    private static int cntId = 40;

    public Person(String name) {
        this.name = name;
        this.id = ++cntId;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("Person ID: %d, Name: %s", id, name);
    }
}