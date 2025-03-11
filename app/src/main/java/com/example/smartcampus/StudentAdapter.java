package com.example.smartcampus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> studentList;

    public StudentAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.tvStudentName.setText(student.getName());
        holder.checkBoxAttendance.setChecked(student.isPresent());

        holder.checkBoxAttendance.setOnCheckedChangeListener((buttonView, isChecked) -> {
            student.setPresent(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void setStudents(List<Student> students) {
        this.studentList = students;
        notifyDataSetChanged();
    }

    public List<Student> getStudents() {
        return studentList;
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName;
        CheckBox checkBoxAttendance;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            checkBoxAttendance = itemView.findViewById(R.id.checkBoxAttendance);
        }
    }
}
