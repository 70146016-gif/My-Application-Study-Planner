package com.example.myapplicationstudyplanner;

/** One exam for a subject on a date. */
public class Exam {
    private final int id;
    private final String subjectName;
    private final long examDate;
    private final String notes;

    public Exam(int id, String subjectName, long examDate, String notes) {
        this.id = id;
        this.subjectName = subjectName;
        this.examDate = examDate;
        this.notes = notes;
    }

    public int getId() { return id; }
    public String getSubjectName() { return subjectName; }
    public long getExamDate() { return examDate; }
    public String getNotes() { return notes; }
}
