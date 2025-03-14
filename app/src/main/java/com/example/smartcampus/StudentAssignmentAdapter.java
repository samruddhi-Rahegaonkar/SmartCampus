package com.example.smartcampus;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentAssignmentAdapter extends RecyclerView.Adapter<StudentAssignmentAdapter.ViewHolder> {
    private final List<StudentAssignment> studentAssignmentList;
    private final FirebaseFirestore db;

    // Constructor
    public StudentAssignmentAdapter(List<StudentAssignment> studentAssignmentList) {
        this.studentAssignmentList = studentAssignmentList;
        this.db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_assignment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentAssignment studentAssignment = studentAssignmentList.get(position);

        // Populate the UI with student details
        holder.tvStudentName.setText(studentAssignment.getName() != null ? studentAssignment.getName() : "No Name");
        holder.tvYear.setText(studentAssignment.getYear() != null ? "Year: " + studentAssignment.getYear() : "No Year");
        holder.tvAssignmentLink.setText(studentAssignment.getLink() != null ? "Assignment Link: " + studentAssignment.getLink() : "No Link");
        holder.tvReviewed.setVisibility(View.GONE); // Hide "Reviewed" initially

        // "View Assignment" button click — opens the link in browser
        holder.btnViewAssignment.setOnClickListener(v -> {
            String link = studentAssignment.getLink();
            if (link != null && !link.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                holder.itemView.getContext().startActivity(intent);
            } else {
                Toast.makeText(holder.itemView.getContext(), "No link available", Toast.LENGTH_SHORT).show();
            }
        });

        // Submit grade button click
        holder.btnSubmitGrades.setOnClickListener(v -> {
            String grade = holder.etGrade.getText().toString().trim();
            if (grade.isEmpty()) {
                Toast.makeText(holder.itemView.getContext(), "Please enter a grade", Toast.LENGTH_SHORT).show();
            } else {
                submitGrade(studentAssignment.getId(), grade, holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentAssignmentList != null ? studentAssignmentList.size() : 0;
    }

    // Submit grade to Firestore
    private void submitGrade(String assignmentId, String grade, ViewHolder holder) {
        Map<String, Object> gradeData = new HashMap<>();
        gradeData.put("grade", grade);

        db.collection("submittedAssignments")
                .document(holder.tvYear.getText().toString().replace("Year: ", "").trim())
                .collection("assignments")
                .document(assignmentId)
                .update(gradeData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(holder.itemView.getContext(), "Grade submitted successfully!", Toast.LENGTH_SHORT).show();
                    holder.etGrade.setText(""); // Clear field
                    holder.tvReviewed.setVisibility(View.VISIBLE); // Show "Reviewed" label
                })
                .addOnFailureListener(e -> Toast.makeText(holder.itemView.getContext(), "Failed to submit grade: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName, tvYear, tvAssignmentLink, tvReviewed;
        EditText etGrade;
        Button btnViewAssignment, btnSubmitGrades;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvAssignmentLink = itemView.findViewById(R.id.tvAssignmentLink);
            etGrade = itemView.findViewById(R.id.etGrade);
            btnViewAssignment = itemView.findViewById(R.id.btnViewAssignment);
            btnSubmitGrades = itemView.findViewById(R.id.btnSubmitGrades);
            tvReviewed = itemView.findViewById(R.id.tvReviewed); // Green "Reviewed" text
        }
    }
}
