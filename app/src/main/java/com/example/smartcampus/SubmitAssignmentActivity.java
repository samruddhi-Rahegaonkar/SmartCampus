package com.example.smartcampus;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SubmitAssignmentActivity extends AppCompatActivity {

    private EditText linkEditText, nameEditText;
    private Spinner yearSpinner;
    private Button submitButton;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_assignment);

        // Initialize views
        linkEditText = findViewById(R.id.linkEditText);
        nameEditText = findViewById(R.id.nameEditText);
        yearSpinner = findViewById(R.id.yearSpinner);
        submitButton = findViewById(R.id.submitButton);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Submitting assignment...");
        progressDialog.setCancelable(false);

        // Year options for spinner
        String[] years = {"Select Year", "1st Year", "2nd Year", "3rd Year", "Final Year"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years);
        yearSpinner.setAdapter(adapter);

        // Submit button logic
        submitButton.setOnClickListener(v -> {
            String link = linkEditText.getText().toString().trim();
            String name = nameEditText.getText().toString().trim();
            String year = yearSpinner.getSelectedItem().toString();

            if (isValidLink(link) && !TextUtils.isEmpty(name) && !year.equals("Select Year")) {
                submitAssignmentToFirestore(link, name, year);
            } else {
                Toast.makeText(this, "Please fill all fields correctly!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Validate HTTP/HTTPS links
    private boolean isValidLink(String link) {
        return !TextUtils.isEmpty(link) && (link.startsWith("http://") || link.startsWith("https://"));
    }

    // Extract title from the link (using the last part after '/')
    private String extractTitleFromLink(String link) {
        if (TextUtils.isEmpty(link)) return "Untitled";
        String title = link.substring(link.lastIndexOf('/') + 1).trim();
        return title.replace("-", " ").replace("_", " ");
    }

    // Submit assignment to Firestore
    private void submitAssignmentToFirestore(String link, String name, String year) {
        progressDialog.show();

        // Extract title from link
        String title = extractTitleFromLink(link);

        Map<String, Object> submissionData = new HashMap<>();
        submissionData.put("title", title); // Automatically extracted title
        submissionData.put("link", link);
        submissionData.put("name", name);
        submissionData.put("year", year);
        submissionData.put("timestamp", System.currentTimeMillis());

        db.collection("submittedAssignments").document(year)
                .collection("assignments")
                .add(submissionData)
                .addOnSuccessListener(documentReference -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Assignment submitted successfully!", Toast.LENGTH_SHORT).show();
                    clearFields();
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Failed to submit: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Clear input fields after submission
    private void clearFields() {
        linkEditText.setText("");
        nameEditText.setText("");
        yearSpinner.setSelection(0);
    }
}
