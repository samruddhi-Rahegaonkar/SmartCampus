package com.example.smartcampus;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FetchGradesAdapter extends RecyclerView.Adapter<FetchGradesAdapter.ViewHolder> {
    private final List<Grade> gradeList;

    public FetchGradesAdapter(List<Grade> gradeList) {
        this.gradeList = gradeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grade, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Grade grade = gradeList.get(position);

        // Display student name, assignment title, and year
        holder.tvStudentName.setText("Student: " + grade.getStudentName());
        holder.tvAssignmentTitle.setText("Assignment: " + grade.getAssignmentTitle());
        holder.tvAssignmentLink.setText("Link: " + grade.getAssignmentLink());
        holder.tvYear.setText("Year: " + grade.getYear());
        holder.tvGrade.setText("Grade: " + grade.getGrade());

        // Make assignment link clickable
        holder.tvAssignmentLink.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(grade.getAssignmentLink()));
            v.getContext().startActivity(browserIntent);
        });
    }


    @Override
    public int getItemCount() {
        return gradeList != null ? gradeList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName, tvAssignmentTitle, tvAssignmentLink, tvYear, tvGrade;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            tvAssignmentTitle = itemView.findViewById(R.id.tvAssignmentTitle);
            tvAssignmentLink = itemView.findViewById(R.id.tvAssignmentLink);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvGrade = itemView.findViewById(R.id.tvGrade);
        }
    }

}
