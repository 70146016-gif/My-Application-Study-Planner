package com.example.myapplicationstudyplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/** Connects the subject list to the RecyclerView using item_subject.xml. */
public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.Holder> {

    public interface Listener {
        void onDelete(Subject subject);
    }

    private final List<Subject> subjects;
    private final Listener listener;

    public SubjectAdapter(List<Subject> subjects, Listener listener) {
        this.subjects = subjects;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subject, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Subject subject = subjects.get(position);
        holder.tvName.setText(subject.getName());
        holder.tvLetter.setText(subject.getName().substring(0, 1).toUpperCase());
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(subject));
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        final TextView tvLetter;
        final TextView tvName;
        final ImageButton btnDelete;

        Holder(@NonNull View itemView) {
            super(itemView);
            tvLetter = itemView.findViewById(R.id.tvSubjectLetter);
            tvName = itemView.findViewById(R.id.tvSubjectName);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
