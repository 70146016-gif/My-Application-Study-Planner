package com.example.myapplicationstudyplanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/** SUBJECTS screen: add, list and delete subjects. */
public class SubjectsFragment extends Fragment {

    private SubjectAdapter adapter;
    private TextView tvEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subjects, container, false);

        EditText etName = view.findViewById(R.id.etSubjectName);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        RecyclerView rvSubjects = view.findViewById(R.id.rvSubjects);
        tvEmpty = view.findViewById(R.id.tvEmpty);

        adapter = new SubjectAdapter(DataStore.getSubjects(), subject -> {
            DataStore.removeSubject(subject);
            refresh();
        });
        rvSubjects.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSubjects.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (name.isEmpty()) {
                etName.setError("Please enter a subject name");
                return;
            }
            DataStore.addSubject(name);
            etName.setText("");
            refresh();
        });

        refresh();
        return view;
    }

    private void refresh() {
        adapter.notifyDataSetChanged();
        tvEmpty.setVisibility(DataStore.getSubjects().isEmpty() ? View.VISIBLE : View.GONE);
    }
}
