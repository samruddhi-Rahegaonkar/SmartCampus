package com.example.smartcampus;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class GradeFetchActivity extends AppCompatActivity {

    private Spinner yearSpinner;
    private RecyclerView recyclerView;
    private FetchGradesAdapter adapter;
    private List<Grade> gradeList;
    private FirebaseFirestore db;
    private static final String TAG = "GradeFetchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_fetch);

        // Initialize UI components
        yearSpinner = findViewById(R.id.yearSpinner);
        recyclerView = findViewById(R.id.recyclerViewGrades);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firestore and data list
        db = FirebaseFirestore.getInstance();
        gradeList = new ArrayList<>();
        adapter = new FetchGradesAdapter(gradeList);
        recyclerView.setAdapter(adapter);

        // Setup year spinner dropdown
        setupYearSpinner();
    }

    private void setupYearSpinner() {
        String[] years = {"Select Year", "1st Year", "2nd Year", "3rd Year", "Final Year"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(spinnerAdapter);

        // Handle year selection
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedYear = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "Selected year: " + selectedYear);

                if (!selectedYear.equals("Select Year")) {
                    fetchGradesByYear(selectedYear);
                } else {
                    clearGrades();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "No year selected");
            }
        });
    }

    private void fetchGradesByYear(String year) {
        Log.d(TAG, "Fetching grades for year: " + year);

        CollectionReference assignmentsRef = db.collection("submittedAssignments")
                .document(year)
                .collection("assignments");

        assignmentsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                gradeList.clear(); // Clear existing data

                for (QueryDocumentSnapshot document : task.getResult()) {
                    String studentName = document.getString("name");
                    String assignmentTitle = document.getString("title");
                    String assignmentLink = document.getString("link");
                    String grade = document.getString("grade"); // Optional — can be null

                    if (studentName != null && assignmentTitle != null && assignmentLink != null) {
                        gradeList.add(new Grade(studentName, assignmentTitle, assignmentLink, year, grade != null ? grade : "Not Graded"));
                        Log.d(TAG, "Added: " + studentName + " | " + assignmentTitle + " | " + grade);
                    } else {
                        Log.e(TAG, "Missing data in document: " + document.getId());
                    }
                }

                adapter.notifyDataSetChanged();
            } else {
                Log.e(TAG, "Error fetching grades", task.getException());
                Toast.makeText(this, "Failed to fetch grades", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearGrades() {
        gradeList.clear();
        adapter.notifyDataSetChanged();
        Log.d(TAG, "Cleared grade list");
    }
}
