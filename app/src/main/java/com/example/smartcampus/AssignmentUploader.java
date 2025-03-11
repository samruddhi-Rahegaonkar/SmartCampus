package com.example.smartcampus;

import android.content.Context;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AssignmentUploader {
    private FirebaseFirestore db;
    private Context context;  // Context for Toast messages

    public AssignmentUploader(Context context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();
    }

    public void uploadAssignment(String title, String description, String deadline) {
        Map<String, Object> assignment = new HashMap<>();
        assignment.put("title", title);
        assignment.put("description", description);
        assignment.put("deadline", deadline);

        db.collection("assignments")
                .add(assignment)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(context, "Assignment submitted successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

}
