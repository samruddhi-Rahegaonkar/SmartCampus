package com.example.smartcampus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentDashboardActivity extends AppCompatActivity {

    private TextView welcomeTextView;

    private Button btnLogout;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private LinearLayout btnEvent, btnAssignment, btnAttendance, btnNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        btnLogout.setOnClickListener(v -> {
            auth.signOut();
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            finish();
        });

        btnEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                /*Intent i = new Intent(StudentDashboardActivity.this,EventActivity.class);
                startActivity(i);*/
            }
        });
    }


}
