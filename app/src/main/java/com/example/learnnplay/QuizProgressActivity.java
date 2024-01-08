package com.example.learnnplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class QuizProgressActivity extends AppCompatActivity {
    TextView scoreTextView, nameTextView;
    Button back;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_progress);

        scoreTextView = findViewById(R.id.score);
        nameTextView = findViewById(R.id.nameTextView);
        back = findViewById(R.id.back);

        firestore = FirebaseFirestore.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizProgressActivity.this, HomeScreen.class));
            }
        });

        String childName = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getString("childName", null);

        if (childName != null) {
            // Retrieve the latest score from Firestore
            getScoreFromFirestore(childName);
        } else {
            Log.e("QuizProgressActivity", "Child name is null");
        }
    }

    private void getScoreFromFirestore(String childName) {
        DocumentReference childProfileRef = firestore.collection("childProfiles").document(childName);

        childProfileRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Get the score from Firestore document
                        int score = documentSnapshot.getLong("score").intValue();

                        // Update UI with the latest score
                        nameTextView.setText(childName + "'s");
                        scoreTextView.setText(String.valueOf(score));
                    } else {
                        Log.e("QuizProgressActivity", "Document does not exist");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("QuizProgressActivity", "Error getting score from Firestore", e);
                });
    }
}
