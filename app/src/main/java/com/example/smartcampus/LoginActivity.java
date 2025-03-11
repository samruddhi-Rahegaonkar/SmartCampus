package com.example.smartcampus;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private RadioButton facultyRadioButton, studentRadioButton;
    private Button loginButton;
    private TextView signUpTextView;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        facultyRadioButton = findViewById(R.id.facultyRadioButton);
        studentRadioButton = findViewById(R.id.studentRadioButton);
        loginButton = findViewById(R.id.loginButton);
        signUpTextView = findViewById(R.id.signUpTextView);
        db = FirebaseFirestore.getInstance();

        loginButton.setOnClickListener(v -> {
            animateLoginButton();
            loginUser();
        });

        signUpTextView.setOnClickListener(v -> navigateToSignUp());
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String enteredPassword = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || enteredPassword.isEmpty()) {
            showToast("Please enter email and password");
            return;
        }

        checkUserCredentials(email, enteredPassword);
    }

    private void checkUserCredentials(String email, String enteredPassword) {
        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String storedPassword = document.getString("password");
                            String role = document.getString("role");

                            if (storedPassword != null && storedPassword.equals(enteredPassword)) {
                                if ("faculty".equals(role) && facultyRadioButton.isChecked()) {
                                    showToast("Login successful as Faculty");
                                    startActivity(new Intent(LoginActivity.this, NoticeActivity.class));
                                } else if ("student".equals(role) && studentRadioButton.isChecked()) {
                                    showToast("Login successful as Student");
                                    startActivity(new Intent(LoginActivity.this, StudentDashboardActivity.class));
                                } else {
                                    showToast("Invalid role selection");
                                }
                            } else {
                                showToast("Incorrect password. Please try again.");
                            }
                        }
                    } else {
                        showToast("User not found. Please sign up.");
                    }
                });
    }

    private void navigateToSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.fade_out);
    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void animateLoginButton() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(loginButton, "scaleX", 0.9f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(loginButton, "scaleY", 0.9f, 1f);
        scaleX.setDuration(200);
        scaleY.setDuration(200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.start();
    }
}
