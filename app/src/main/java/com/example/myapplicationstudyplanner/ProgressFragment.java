package com.example.myapplicationstudyplanner;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

/** PROGRESS screen: completed vs pending tasks, overall and per subject. */
public class ProgressFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        TextView tvTotal = view.findViewById(R.id.tvTotal);
        TextView tvDone = view.findViewById(R.id.tvDone);
        TextView tvPending = view.findViewById(R.id.tvPending);
        TextView tvPercent = view.findViewById(R.id.tvPercent);
        ProgressBar progressOverall = view.findViewById(R.id.progressOverall);
        LinearLayout llSubjects = view.findViewById(R.id.llSubjectProgress);

        // ---------- Overall numbers ----------
        int total = DataStore.getTasks().size();
        int done = DataStore.getCompletedCount();
        int percent = (total == 0) ? 0 : (done * 100) / total;

        tvTotal.setText(String.valueOf(total));
        tvDone.setText(String.valueOf(done));
        tvPending.setText(String.valueOf(total - done));
        tvPercent.setText("Overall progress: " + percent + "%");
        progressOverall.setProgress(percent);

        // ---------- One label + bar per subject ----------
        if (DataStore.getSubjects().isEmpty()) {
            TextView empty = new TextView(getContext());
            empty.setText("Add subjects to see progress per subject.");
            empty.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary));
            empty.setPadding(0, dp(12), 0, 0);
            llSubjects.addView(empty);
        }

        for (Subject subject : DataStore.getSubjects()) {
            int subjectTotal = 0;
            int subjectDone = 0;
            for (Task t : DataStore.getTasks()) {
                if (t.getSubjectName().equals(subject.getName())) {
                    subjectTotal++;
                    if (t.isCompleted()) subjectDone++;
                }
            }

            TextView label = new TextView(getContext());
            label.setText(subject.getName() + ": " + subjectDone + "/" + subjectTotal + " done");
            label.setTextSize(16);
            label.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_main));
            label.setPadding(0, dp(16), 0, dp(4));

            ProgressBar bar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
            bar.setMax(100);
            bar.setProgress(subjectTotal == 0 ? 0 : (subjectDone * 100) / subjectTotal);
            bar.setProgressTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.done_green)));

            llSubjects.addView(label);
            llSubjects.addView(bar);
        }

        return view;
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
