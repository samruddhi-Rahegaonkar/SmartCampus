package com.example.smartcampus;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class ViewAssignmentsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AssignmentAdapter adapter;
    private List<Assignment> assignmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assignments);

        recyclerView = findViewById(R.id.recyclerViewAssignments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assignmentList = new ArrayList<>();
        adapter = new AssignmentAdapter(assignmentList);
        recyclerView.setAdapter(adapter);

        fetchAssignments();
    }

    private void fetchAssignments() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("assignments").get().addOnSuccessListener(queryDocumentSnapshots -> {
            assignmentList.clear();
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Assignment assignment = document.toObject(Assignment.class);
                assignmentList.add(assignment);
            }
            adapter.notifyDataSetChanged();
        }).addOnFailureListener(e ->
                Toast.makeText(this, "Failed to load assignments", Toast.LENGTH_SHORT).show()
        );
    }
}
