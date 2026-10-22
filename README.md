# Study Planner

An Android app that keeps a student's subjects, assignments and exams in one place.
It shows what is due today, which exams are coming up, and how many tasks are done.

Mobile Application Development (CS02310), Section W — The University of Lahore.

## Features (Task 1 — UI and layouts)

| Screen | What you can do |
| --- | --- |
| Home (Daily Planner) | See tasks due today and exams in the next 7 days |
| Subjects | Add and delete subjects |
| Tasks | Add a task (title, subject, due date), tick it done, delete it; overdue tasks shown in red |
| Exams | Add an exam (subject, date, notes), see days left, delete it |
| Progress | Total / done / pending counts, overall and per-subject progress bars |

## Required tools

- Android Studio (latest stable version)
- Android SDK with API 37 (installed by Android Studio)
- An Android emulator (e.g. Medium Phone, API 37) or an Android phone with USB debugging on
- Git

## Setup and run

1. Clone the repository:
   ```
   git clone https://github.com/70146016-gif/My-Application-Study-Planner.git
   ```
   or in Android Studio: **File → New → Project from Version Control**, then paste the URL.
2. Open the project in Android Studio and wait for **Gradle sync** to finish.
3. Select an emulator or a connected phone in the device dropdown.
4. Click **Run ▶**.

Minimum Android version: 7.0 (API 24).

## Database configuration

No database setup is needed yet. In Task 1 the app uses static sample data held in memory
(`DataStore.java`). A Room (SQLite) database will be added in Task 2.

## Project structure

- `app/src/main/res/layout/` — XML layouts for all screens, list rows and dialogs
- `app/src/main/res/menu/bottom_nav_menu.xml` — the 5 bottom tabs
- `app/src/main/java/com/example/myapplicationstudyplanner/` — Java code:
  `MainActivity` (tabs), 5 fragments (screens), 3 adapters (lists),
  `Subject` / `Task` / `Exam` (data), `DataStore` (sample data), `DateUtil` (dates)

## Known limitations

- Data is not saved: added items are lost when the app is closed (Room database in Task 2).
- Existing tasks and exams cannot be edited yet, only added and deleted.
- Deleting a subject does not delete its tasks or exams.
- Reminders and notifications are not implemented yet.

## Milestones

| Tag | Task |
| --- | --- |
| `task-1` | UI and layouts with sample data |
