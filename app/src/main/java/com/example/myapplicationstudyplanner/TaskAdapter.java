package com.example.myapplicationstudyplanner;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/** Shows tasks using item_task.xml. Used on the Tasks and Home screens. */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.Holder> {

    public interface Listener {
        void onToggle(Task task, boolean done);
        void onDelete(Task task);
    }

    private List<Task> tasks;
    private final Listener listener;

    public TaskAdapter(List<Task> tasks, Listener listener) {
        this.tasks = tasks;
        this.listener = listener;
    }

    /** Give the adapter a new list and redraw it. */
    public void setTasks(List<Task> newTasks) {
        this.tasks = newTasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Task task = tasks.get(position);

        holder.tvTitle.setText(task.getTitle());
        holder.tvDetails.setText(task.getSubjectName() + " • Due " + DateUtil.format(task.getDueDate()));

        // Cross out finished tasks
        if (task.isCompleted()) {
            holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

        // Overdue and not done = red date line
        boolean overdue = !task.isCompleted() && DateUtil.daysLeft(task.getDueDate()) < 0;
        int color = overdue ? R.color.overdue_red : R.color.text_secondary;
        holder.tvDetails.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), color));
        if (overdue) {
            holder.tvDetails.setText(holder.tvDetails.getText() + " (overdue)");
        }

        // Remove the old listener first so setChecked doesn't trigger it
        holder.cbDone.setOnCheckedChangeListener(null);
        holder.cbDone.setChecked(task.isCompleted());
        holder.cbDone.setOnCheckedChangeListener((button, isChecked) -> listener.onToggle(task, isChecked));

        holder.btnDelete.setOnClickListener(v -> listener.onDelete(task));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        final CheckBox cbDone;
        final TextView tvTitle;
        final TextView tvDetails;
        final ImageButton btnDelete;

        Holder(@NonNull View itemView) {
            super(itemView);
            cbDone = itemView.findViewById(R.id.cbDone);
            tvTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvDetails = itemView.findViewById(R.id.tvTaskDetails);
            btnDelete = itemView.findViewById(R.id.btnDeleteTask);
        }
    }
}
