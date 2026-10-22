package com.example.myapplicationstudyplanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/** HOME / DAILY PLANNER: what to study today and exams in the next 7 days. */
public class HomeFragment extends Fragment {

    private TaskAdapter taskAdapter;
    private ExamAdapter examAdapter;
    private TextView tvNoTasks;
    private TextView tvNoExams;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView tvToday = view.findViewById(R.id.tvToday);
        tvToday.setText("Today is " + DateUtil.format(System.currentTimeMillis()));

        tvNoTasks = view.findViewById(R.id.tvNoTasksToday);
        tvNoExams = view.findViewById(R.id.tvNoExamsSoon);
        RecyclerView rvTasks = view.findViewById(R.id.rvTodayTasks);
        RecyclerView rvExams = view.findViewById(R.id.rvUpcomingExams);

        taskAdapter = new TaskAdapter(new ArrayList<>(), new TaskAdapter.Listener() {
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
        rvTasks.setAdapter(taskAdapter);

        examAdapter = new ExamAdapter(new ArrayList<>(), exam -> {
            DataStore.removeExam(exam);
            refresh();
        });
        rvExams.setLayoutManager(new LinearLayoutManager(getContext()));
        rvExams.setAdapter(examAdapter);

        refresh();
        return view;
    }

    private void refresh() {
        List<Task> today = DataStore.getTodayTasks();
        taskAdapter.setTasks(today);
        tvNoTasks.setVisibility(today.isEmpty() ? View.VISIBLE : View.GONE);

        List<Exam> upcoming = DataStore.getUpcomingExams();
        examAdapter.setExams(upcoming);
        tvNoExams.setVisibility(upcoming.isEmpty() ? View.VISIBLE : View.GONE);
    }
}
