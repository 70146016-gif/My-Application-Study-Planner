package com.example.myapplicationstudyplanner;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

/** TASKS screen: list of tasks, tick to complete, + button opens the Add Task dialog. */
public class TasksFragment extends Fragment {

    private TaskAdapter adapter;
    private TextView tvEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        RecyclerView rvTasks = view.findViewById(R.id.rvTasks);
        tvEmpty = view.findViewById(R.id.tvEmptyTasks);
        FloatingActionButton fab = view.findViewById(R.id.fabAddTask);

        adapter = new TaskAdapter(DataStore.getTasks(), new TaskAdapter.Listener() {
            @Override
            public void onToggle(Task task, boolean done) {
                task.setCompleted(done);
                refresh();
            }

            @Override
            public void onDelete(Task task) {
                DataStore.removeTask(task);
                refresh();
            }
        });
        rvTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTasks.setAdapter(adapter);

        fab.setOnClickListener(v -> showAddTaskDialog());

        refresh();
        return view;
    }

    private void refresh() {
        adapter.setTasks(DataStore.getTasks());
        tvEmpty.setVisibility(DataStore.getTasks().isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void showAddTaskDialog() {
        if (DataStore.getSubjects().isEmpty()) {
            Toast.makeText(getContext(), "Add a subject first", Toast.LENGTH_SHORT).show();
            return;
        }

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_task, null);
        EditText etTitle = dialogView.findViewById(R.id.etTaskTitle);
        Spinner spSubject = dialogView.findViewById(R.id.spTaskSubject);
        Button btnPickDate = dialogView.findViewById(R.id.btnPickDueDate);

        spSubject.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item, DataStore.getSubjectNames()));

        // Array of 1 so the lambda below can change it
        final long[] dueDate = {-1};

        btnPickDate.setOnClickListener(v -> {
            Calendar today = Calendar.getInstance();
            new DatePickerDialog(requireContext(), (picker, year, month, day) -> {
                dueDate[0] = DateUtil.dateOf(year, month, day);
                btnPickDate.setText("Due: " + DateUtil.format(dueDate[0]));
            }, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)).show();
        });

        AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Add Task")
                .setView(dialogView)
                .setPositiveButton("Save", null) // handled below so the dialog can stay open
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(d ->
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                    String title = etTitle.getText().toString().trim();
                    if (title.isEmpty()) {
                        etTitle.setError("Please enter a task title");
                        return;
                    }
                    if (dueDate[0] == -1) {
                        Toast.makeText(getContext(), "Please pick a due date", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    DataStore.addTask(title, spSubject.getSelectedItem().toString(), dueDate[0]);
                    refresh();
                    dialog.dismiss();
                }));

        dialog.show();
    }
}
