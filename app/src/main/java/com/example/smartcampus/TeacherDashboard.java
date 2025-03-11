package com.example.smartcampus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class TeacherDashboard extends AppCompatActivity {

    private LinearLayout btnMarkAttendance, btnViewAttendance, btnAddStudent, btnCreateEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        // Linking XML elements to Java code
        btnMarkAttendance = findViewById(R.id.btnMarkAttendance);
        btnViewAttendance = findViewById(R.id.btnViewAttendance);
        btnAddStudent = findViewById(R.id.btnAddStudent);
        btnCreateEvent = findViewById(R.id.btnCreateEvent); // Initialize this!

        // Set click listeners for each button
        btnMarkAttendance.setOnClickListener(v ->
                startActivity(new Intent(TeacherDashboard.this, MarkAttendanceActivity.class))
        );

        btnViewAttendance.setOnClickListener(v ->
                startActivity(new Intent(TeacherDashboard.this, ViewAttendanceActivity.class))
        );

        btnAddStudent.setOnClickListener(v ->
                startActivity(new Intent(TeacherDashboard.this, AddStudentActivity.class))
        );

        btnCreateEvent.setOnClickListener(v ->
                startActivity(new Intent(TeacherDashboard.this, CreateEventActivity.class))
        );
    }
}
