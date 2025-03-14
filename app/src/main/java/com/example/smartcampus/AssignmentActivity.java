package com.example.smartcampus;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;

public class AssignmentActivity extends AppCompatActivity {
    private TextInputEditText etTitle, etDescription;
    private TextView tvDeadline;
    private Button btnPickDeadline, btnSubmitAssignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        // Initialize UI components
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        tvDeadline = findViewById(R.id.tvDeadline);
        btnPickDeadline = findViewById(R.id.btnPickDeadline);
        btnSubmitAssignment = findViewById(R.id.btnSubmitAssignment);
        Button btnViewAssignments = findViewById(R.id.btnViewAssignment);
        btnViewAssignments.setOnClickListener(v -> {
            Intent intent = new Intent(AssignmentActivity.this, ViewStudentsAssignmentsActivity.class);
            startActivity(intent);
        });
        btnPickDeadline.setOnClickListener(v -> showDatePicker());
        btnSubmitAssignment.setOnClickListener(v -> submitAssignment());
    }

    // Date Picker Dialog for selecting a deadline
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            tvDeadline.setText("Deadline: " + selectedDate);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    // Submit Assignment
    private void submitAssignment() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String deadline = tvDeadline.getText().toString().replace("Deadline: ", "");

        // Validation
        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Upload Assignment Data
        AssignmentUploader uploader = new AssignmentUploader(this);
        uploader.uploadAssignment(title, description, deadline);
    }

}
