package com.example.smartcampus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;

public class SubmitAssignmentActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1;
    private Uri fileUri;
    private TextView fileNameTextView;
    private Button selectFileButton;
    private Button submitButton;
    private StudentAssignmentUploader uploader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_assignment);

        // Initialize views
        fileNameTextView = findViewById(R.id.fileNameTextView);
        selectFileButton = findViewById(R.id.selectFileButton);
        submitButton = findViewById(R.id.submitButton);

        // Initialize uploader class for assignment upload
        uploader = new StudentAssignmentUploader(this);

        // Set up file picker button click listener
        selectFileButton.setOnClickListener(v -> {
            openFilePicker();
        });

        // Set up submit button click listener
        submitButton.setOnClickListener(v -> {
            if (fileUri != null) {
                String assignmentId = "assignmentId"; // Replace with dynamic assignment ID
                String studentId = "studentId"; // Replace with actual student ID
                uploader.submitAssignment(assignmentId, studentId, fileUri);
            } else {
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Open file picker to select a file
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf"); // Change this if you need other file types
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null) {
            fileUri = data.getData();
            String fileName = fileUri != null ? fileUri.getLastPathSegment() : "No file selected";
            fileNameTextView.setText(fileName);
        }
    }
}
