package com.example.smartcampus;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class ViewStudentsAssignmentsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StudentAssignmentAdapter adapter;
    private List<StudentAssignment> studentAssignmentList;
    private Spinner yearSpinner;
    private ProgressBar progressBar;
    private FirebaseFirestore db;

    private final String[] years = {"1st Year", "2nd Year", "3rd Year", "Final Year"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students_assignments);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        recyclerView = findViewById(R.id.recyclerViewStudentAssignments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        studentAssignmentList = new ArrayList<>();
        adapter = new StudentAssignmentAdapter(studentAssignmentList);
        recyclerView.setAdapter(adapter);

        yearSpinner = findViewById(R.id.yearSpinner);
        progressBar = findViewById(R.id.progressBar);

        setupYearSpinner();
    }

    private void setupYearSpinner() {
        // Set up Spinner with year options
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(spinnerAdapter);

        // Listener for spinner selection
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedYear = parent.getItemAtPosition(position).toString();
                fetchStudentAssignments(selectedYear);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: show a message or leave blank
            }
        });

        // Default to 1st Year assignments
        yearSpinner.setSelection(0);
        fetchStudentAssignments(years[0]);
    }

    private void fetchStudentAssignments(String year) {
        progressBar.setVisibility(View.VISIBLE);

        db.collection("submittedAssignments")
                .document(year) // Year document
                .collection("assignments") // Assignments sub-collection
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    studentAssignmentList.clear(); // Clear old data

                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            StudentAssignment studentAssignment = document.toObject(StudentAssignment.class);
                            if (studentAssignment != null) {
                                studentAssignment.setId(document.getId());
                                studentAssignmentList.add(studentAssignment);
                            }
                        }
                    } else {
                        Toast.makeText(this, "No assignments found for " + year, Toast.LENGTH_SHORT).show();
                    }

                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to load assignments: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                });
    }
}
