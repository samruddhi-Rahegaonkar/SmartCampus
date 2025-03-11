package com.example.smartcampus;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateEventActivity extends AppCompatActivity {

    private EditText etEventTitle, etEventDescription;
    private TextView tvSelectedDate;
    private Button btnPickDate, btnAddEvent;
    private String selectedDate = "";
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        etEventTitle = findViewById(R.id.etEventTitle);
        etEventDescription = findViewById(R.id.etEventDescription);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        btnPickDate = findViewById(R.id.btnPickDate);
        btnAddEvent = findViewById(R.id.btnAddEvent);

        // Date picker event
        btnPickDate.setOnClickListener(v -> showDatePicker());

        // Add event to Firestore
        btnAddEvent.setOnClickListener(v -> addEventToFirestore());
    }

    // Show DatePickerDialog to select event date
    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, month1, dayOfMonth) -> {
                    selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    tvSelectedDate.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    // Add event to Firestore
    private void addEventToFirestore() {
        String eventTitle = etEventTitle.getText().toString().trim();
        String eventDescription = etEventDescription.getText().toString().trim();

        // Validation
        if (eventTitle.isEmpty()) {
            etEventTitle.setError("Please enter event title");
            return;
        }
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (eventDescription.isEmpty()) {
            etEventDescription.setError("Please enter event description");
            return;
        }

        // Create event object
        Map<String, Object> event = new HashMap<>();
        event.put("title", eventTitle);
        event.put("date", selectedDate);
        event.put("description", eventDescription);

        // Store event in Firestore under "events" collection
        db.collection("events")
                .add(event)
                .addOnSuccessListener(documentReference ->
                        Toast.makeText(CreateEventActivity.this, "Event added successfully!", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(CreateEventActivity.this, "Failed to add event!", Toast.LENGTH_SHORT).show()
                );
    }
}
