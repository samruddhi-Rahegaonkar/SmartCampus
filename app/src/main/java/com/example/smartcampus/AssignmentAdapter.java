package com.example.smartcampus;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    private List<Assignment> assignmentList;

    public AssignmentAdapter(List<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assignment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Assignment assignment = assignmentList.get(position);
        holder.tvTitle.setText(assignment.getTitle());
        holder.tvDescription.setText(assignment.getDescription());
        holder.tvDeadline.setText("Deadline: " + assignment.getDeadline());

        // Set click listener for the Submit button
        holder.btnSubmitAssignment.setOnClickListener(v -> {
            // Create an Intent to navigate to SubmitAssignmentActivity
            Intent intent = new Intent(holder.itemView.getContext(), SubmitAssignmentActivity.class);
            intent.putExtra("assignmentId", assignment.getId()); // Pass the assignment ID
            holder.itemView.getContext().startActivity(intent); // Start the activity
        });
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvDeadline;
        Button btnSubmitAssignment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvAssignmentTitle);
            tvDescription = itemView.findViewById(R.id.tvAssignmentDescription);
            tvDeadline = itemView.findViewById(R.id.tvAssignmentDeadline);
            btnSubmitAssignment = itemView.findViewById(R.id.btnSubmitAssignment); // Get reference to the button
        }
    }
}
