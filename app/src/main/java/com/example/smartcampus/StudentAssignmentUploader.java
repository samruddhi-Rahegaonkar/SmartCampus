package com.example.smartcampus;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.HashMap;
import java.util.Map;

public class StudentAssignmentUploader {
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private Context context;

    public StudentAssignmentUploader(Context context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    // Submit the assignment by uploading the file to Firebase Storage and saving data to Firestore
    public void submitAssignment(String assignmentId, String studentId, Uri fileUri) {
        if (fileUri != null) {
            // Create a storage reference for the file
            StorageReference fileRef = storage.getReference().child("student_submissions/" + studentId + "_" + assignmentId + ".pdf");

            // Upload the file to Firebase Storage
            fileRef.putFile(fileUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // After successful upload, get the file URL
                        fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String fileUrl = uri.toString();

                            // Prepare data for Firestore
                            Map<String, Object> submissionData = new HashMap<>();
                            submissionData.put("studentId", studentId);
                            submissionData.put("fileUrl", fileUrl);
                            submissionData.put("submitted", true);

                            // Save the submission data to Firestore
                            db.collection("assignments")
                                    .document(assignmentId)
                                    .collection("submissions")
                                    .document(studentId)
                                    .set(submissionData)
                                    .addOnSuccessListener(documentReference -> {
                                        Toast.makeText(context, "Assignment submitted successfully!", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(context, "Submission Failed!", Toast.LENGTH_SHORT).show();
                                    });
                        });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "File Upload Failed!", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(context, "No file selected!", Toast.LENGTH_SHORT).show();
        }
    }
}
