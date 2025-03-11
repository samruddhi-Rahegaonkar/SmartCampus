package com.example.smartcampus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class NoticeActivity extends AppCompatActivity
{

    private EditText etNoticeTitle, etNoticeDescription;
    private Button btnSubmitNotice;
    private FirebaseFirestore db; // Firestore instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        etNoticeTitle = findViewById(R.id.etNoticeTitle);
        etNoticeDescription = findViewById(R.id.etNoticeDescription);
        btnSubmitNotice = findViewById(R.id.btnSubmitNotice);

        // Button Click Listener
        btnSubmitNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitNotice();
            }
        });
    }

    private void submitNotice() {
        String title = etNoticeTitle.getText().toString().trim();
        String description = etNoticeDescription.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(title)) {
            etNoticeTitle.setError("Title is required!");
            return;
        }
        if (TextUtils.isEmpty(description)) {
            etNoticeDescription.setError("Description is required!");
            return;
        }

        // Create a notice object
        Map<String, Object> notice = new HashMap<>();
        notice.put("title", title);
        notice.put("description", description);
        notice.put("timestamp", System.currentTimeMillis()); // Store timestamp

        // Store in Firestore under "Notices" collection
        db.collection("Notices")
                .add(notice)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(NoticeActivity.this, "Notice submitted!", Toast.LENGTH_SHORT).show();
                        etNoticeTitle.setText("");
                        etNoticeDescription.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NoticeActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
