package com.example.myapplicationstudyplanner;

/** One subject, e.g. "MAD". */
public class Subject {
    private final int id;
    private final String name;

    public Subject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
}
