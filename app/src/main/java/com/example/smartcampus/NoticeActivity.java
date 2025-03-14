package com.example.smartcampus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NoticeActivity extends AppCompatActivity {

    private EditText etNoticeTitle, etNoticeDescription;
    private TextView tvSelectedDate;
    private Button btnSubmitNotice, btnPickDate;
    private FirebaseFirestore db;
    private String selectedDate;

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
        btnPickDate = findViewById(R.id.btnPickDate);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);

        // Button Click Listeners
        btnPickDate.setOnClickListener(v -> showDatePicker());
        btnSubmitNotice.setOnClickListener(v -> submitNotice());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                    tvSelectedDate.setText("Selected Date: " + selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void submitNotice() {
        String title = etNoticeTitle.getText().toString().trim();
        String description = etNoticeDescription.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            etNoticeTitle.setError("Title is required!");
            return;
        }
        if (TextUtils.isEmpty(description)) {
            etNoticeDescription.setError("Description is required!");
            return;
        }
        if (TextUtils.isEmpty(selectedDate)) {
            Toast.makeText(this, "Please pick a date!", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> notice = new HashMap<>();
        notice.put("title", title);
        notice.put("description", description);
        notice.put("date", selectedDate);
        notice.put("timestamp", System.currentTimeMillis());

        db.collection("Notices")
                .add(notice)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(NoticeActivity.this, "Notice submitted!", Toast.LENGTH_SHORT).show();
                    etNoticeTitle.setText("");
                    etNoticeDescription.setText("");
                    tvSelectedDate.setText("Selected Date: None");
                    selectedDate = null;
                })
                .addOnFailureListener(e ->
                        Toast.makeText(NoticeActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
