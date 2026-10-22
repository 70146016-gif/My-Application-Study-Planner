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

/** EXAMS screen: list of exams with days left, + button opens the Add Exam dialog. */
public class ExamsFragment extends Fragment {

    private ExamAdapter adapter;
    private TextView tvEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exams, container, false);

        RecyclerView rvExams = view.findViewById(R.id.rvExams);
        tvEmpty = view.findViewById(R.id.tvEmptyExams);
        FloatingActionButton fab = view.findViewById(R.id.fabAddExam);

        adapter = new ExamAdapter(DataStore.getExams(), exam -> {
            DataStore.removeExam(exam);
            refresh();
        });
        rvExams.setLayoutManager(new LinearLayoutManager(getContext()));
        rvExams.setAdapter(adapter);

        fab.setOnClickListener(v -> showAddExamDialog());

        refresh();
        return view;
    }

    private void refresh() {
        adapter.setExams(DataStore.getExams());
        tvEmpty.setVisibility(DataStore.getExams().isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void showAddExamDialog() {
        if (DataStore.getSubjects().isEmpty()) {
            Toast.makeText(getContext(), "Add a subject first", Toast.LENGTH_SHORT).show();
            return;
        }

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_exam, null);
        Spinner spSubject = dialogView.findViewById(R.id.spExamSubject);
        Button btnPickDate = dialogView.findViewById(R.id.btnPickExamDate);
        EditText etNotes = dialogView.findViewById(R.id.etExamNotes);

        spSubject.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item, DataStore.getSubjectNames()));

        final long[] examDate = {-1};

        btnPickDate.setOnClickListener(v -> {
            Calendar today = Calendar.getInstance();
            DatePickerDialog picker = new DatePickerDialog(requireContext(), (p, year, month, day) -> {
                examDate[0] = DateUtil.dateOf(year, month, day);
                btnPickDate.setText("Exam on " + DateUtil.format(examDate[0]));
            }, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
            // Exams are in the future: grey out past days
            picker.getDatePicker().setMinDate(DateUtil.startOfToday().getTimeInMillis());
            picker.show();
        });

        AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Add Exam")
                .setView(dialogView)
                .setPositiveButton("Save", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(d ->
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                    if (examDate[0] == -1) {
                        Toast.makeText(getContext(), "Please pick an exam date", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    DataStore.addExam(spSubject.getSelectedItem().toString(), examDate[0],
                            etNotes.getText().toString().trim());
                    refresh();
                    dialog.dismiss();
                }));

        dialog.show();
    }
}
