package com.example.smartcampus;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AddStudentActivity extends AppCompatActivity {

    private EditText etStudentName, etStudentId, etStudentEmail;
    private Spinner spinnerYear;
    private Button btnAddStudent;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        // Initialize UI components
        etStudentName = findViewById(R.id.etStudentName);
        etStudentId = findViewById(R.id.etStudentId);
        etStudentEmail = findViewById(R.id.etStudentEmail);
        spinnerYear = findViewById(R.id.spinnerYear);
        btnAddStudent = findViewById(R.id.btnAddStudent);

        db = FirebaseFirestore.getInstance();

        // Populate Year Spinner
        String[] years = {"Select Year", "1st Year", "2nd Year", "Final Year"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years);
        spinnerYear.setAdapter(adapter);

        btnAddStudent.setOnClickListener(v -> addStudent());
    }

    private void addStudent() {
        String name = etStudentName.getText().toString().trim();
        String studentId = etStudentId.getText().toString().trim();
        String email = etStudentEmail.getText().toString().trim();
        String year = spinnerYear.getSelectedItem().toString();

        // Input Validation
        if (TextUtils.isEmpty(name)) {
            etStudentName.setError("Enter Student Name");
            etStudentName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(studentId)) {
            etStudentId.setError("Enter Student ID");
            etStudentId.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            etStudentEmail.setError("Enter Student Email");
            etStudentEmail.requestFocus();
            return;
        }

        if (year.equals("Select Year")) {
            Toast.makeText(this, "Please select a valid Year", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare student data
        Map<String, Object> student = new HashMap<>();
        student.put("name", name);
        student.put("studentId", studentId);
        student.put("email", email);

        // Store data in Firestore
        db.collection("students").document(year)
                .collection("studentList").document(studentId)
                .set(student)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(AddStudentActivity.this, "Student added successfully to " + year, Toast.LENGTH_SHORT).show();
                    clearFields();
                })
                .addOnFailureListener(e -> Toast.makeText(AddStudentActivity.this, "Error adding student: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void clearFields() {
        etStudentName.setText("");
        etStudentId.setText("");
        etStudentEmail.setText("");
        spinnerYear.setSelection(0);
    }
}
