package com.example.myapplicationstudyplanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps subjects, tasks and exams in memory while the app is open.
 * It starts with static sample data so every screen has something to show.
 * (Lab 2 replaces this with a Room database.)
 */
public class DataStore {

    private static final List<Subject> subjects = new ArrayList<>();
    private static final List<Task> tasks = new ArrayList<>();
    private static final List<Exam> exams = new ArrayList<>();
    private static int nextSubjectId = 1;
    private static int nextTaskId = 1;
    private static int nextExamId = 1;

    // ---------- Sample data (runs once when the app starts) ----------
    static {
        addSubject("MAD");
        addSubject("SPM");
        addSubject("SQE");
        addSubject("HRM");

        addTask("MAD Lab Task 1 – UI layouts", "MAD", DateUtil.daysFromToday(0));
        addTask("SPM quiz preparation", "SPM", DateUtil.daysFromToday(0));
        addTask("SQE test case document", "SQE", DateUtil.daysFromToday(2));
        addTask("HRM case study summary", "HRM", DateUtil.daysFromToday(5));
        addTask("SPM project charter", "SPM", DateUtil.daysFromToday(-1)); // overdue
        addTask("MAD proposal submission", "MAD", DateUtil.daysFromToday(-3));
        findTask("MAD proposal submission").setCompleted(true);

        addExam("SPM", DateUtil.daysFromToday(3), "Quiz 2 – chapters 3 and 4");
        addExam("MAD", DateUtil.daysFromToday(6), "Mid-term practical");
        addExam("SQE", DateUtil.daysFromToday(14), "");
    }

    // ---------- Subjects ----------
    public static List<Subject> getSubjects() { return subjects; }

    public static void addSubject(String name) {
        subjects.add(new Subject(nextSubjectId++, name));
    }

    public static void removeSubject(Subject subject) { subjects.remove(subject); }

    public static List<String> getSubjectNames() {
        List<String> names = new ArrayList<>();
        for (Subject s : subjects) names.add(s.getName());
        return names;
    }

    // ---------- Tasks ----------
    public static List<Task> getTasks() { return tasks; }

    public static void addTask(String title, String subjectName, long dueDate) {
        tasks.add(new Task(nextTaskId++, title, subjectName, dueDate));
        tasks.sort((a, b) -> Long.compare(a.getDueDate(), b.getDueDate()));
    }

    public static void removeTask(Task task) { tasks.remove(task); }

    private static Task findTask(String title) {
        for (Task t : tasks) if (t.getTitle().equals(title)) return t;
        return tasks.get(0);
    }

    public static List<Task> getTodayTasks() {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (DateUtil.daysLeft(t.getDueDate()) == 0) result.add(t);
        }
        return result;
    }

    public static int getCompletedCount() {
        int count = 0;
        for (Task t : tasks) if (t.isCompleted()) count++;
        return count;
    }

    // ---------- Exams ----------
    public static List<Exam> getExams() { return exams; }

    public static void addExam(String subjectName, long examDate, String notes) {
        exams.add(new Exam(nextExamId++, subjectName, examDate, notes));
        exams.sort((a, b) -> Long.compare(a.getExamDate(), b.getExamDate()));
    }

    public static void removeExam(Exam exam) { exams.remove(exam); }

    /** Exams from today up to 7 days ahead. */
    public static List<Exam> getUpcomingExams() {
        List<Exam> result = new ArrayList<>();
        for (Exam e : exams) {
            long days = DateUtil.daysLeft(e.getExamDate());
            if (days >= 0 && days <= 7) result.add(e);
        }
        return result;
    }
}
