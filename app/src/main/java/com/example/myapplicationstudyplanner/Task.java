package com.example.myapplicationstudyplanner;

/** One assignment or task with a due date. Dates are stored as milliseconds. */
public class Task {
    private final int id;
    private final String title;
    private final String subjectName;
    private final long dueDate;
    private boolean completed;

    public Task(int id, String title, String subjectName, long dueDate) {
        this.id = id;
        this.title = title;
        this.subjectName = subjectName;
        this.dueDate = dueDate;
        this.completed = false;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getSubjectName() { return subjectName; }
    public long getDueDate() { return dueDate; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
