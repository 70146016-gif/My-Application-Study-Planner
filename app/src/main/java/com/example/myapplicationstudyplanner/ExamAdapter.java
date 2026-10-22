package com.example.myapplicationstudyplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/** Shows exams using item_exam.xml. Used on the Exams and Home screens. */
public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.Holder> {

    public interface Listener {
        void onDelete(Exam exam);
    }

    private List<Exam> exams;
    private final Listener listener;

    public ExamAdapter(List<Exam> exams, Listener listener) {
        this.exams = exams;
        this.listener = listener;
    }

    public void setExams(List<Exam> newExams) {
        this.exams = newExams;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exam, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Exam exam = exams.get(position);

        holder.tvSubject.setText(exam.getSubjectName());
        holder.tvDate.setText(DateUtil.format(exam.getExamDate()));
        holder.tvDaysLeft.setText(DateUtil.daysLeftText(exam.getExamDate()));

        // Hide the notes line when there are no notes
        if (exam.getNotes().isEmpty()) {
            holder.tvNotes.setVisibility(View.GONE);
        } else {
            holder.tvNotes.setVisibility(View.VISIBLE);
            holder.tvNotes.setText(exam.getNotes());
        }

        holder.btnDelete.setOnClickListener(v -> listener.onDelete(exam));
    }

    @Override
    public int getItemCount() {
        return exams.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        final TextView tvSubject;
        final TextView tvDate;
        final TextView tvNotes;
        final TextView tvDaysLeft;
        final ImageButton btnDelete;

        Holder(@NonNull View itemView) {
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tvExamSubject);
            tvDate = itemView.findViewById(R.id.tvExamDate);
            tvNotes = itemView.findViewById(R.id.tvExamNotes);
            tvDaysLeft = itemView.findViewById(R.id.tvDaysLeft);
            btnDelete = itemView.findViewById(R.id.btnDeleteExam);
        }
    }
}
