package com.example.smartcampus;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.*;
import java.util.ArrayList;
import java.util.List;

public class NoticeFetchActivity extends AppCompatActivity {

    private RecyclerView noticesRecyclerView;
    private EventAdapter eventAdapter;
    private List<String> eventsList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_fetch);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        noticesRecyclerView = findViewById(R.id.noticesRecyclerView);
        noticesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize data and adapter
        eventsList = new ArrayList<>();
        eventAdapter = new EventAdapter(eventsList);
        noticesRecyclerView.setAdapter(eventAdapter);

        // Fetch events from Firestore
        fetchEvents();
    }

    private void fetchEvents() {
        db.collection("Notices")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    eventsList.clear();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        String title = document.getString("title");
                        String date = document.getString("date");
                        String description = document.getString("description");
                        String eventDetails = "Title: " + title + "\nDate: " + date + "\nDescription: " + description;
                        eventsList.add(eventDetails);
                    }
                    eventAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(NoticeFetchActivity.this, "Failed to load events!", Toast.LENGTH_SHORT).show()
                );
    }
}
