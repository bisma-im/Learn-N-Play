package com.example.learnnplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizProgressActivity extends AppCompatActivity {
    TextView scoreTextView, nameTextView;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_progress);
        scoreTextView = findViewById(R.id.score);
        nameTextView = findViewById(R.id.nameTextView);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizProgressActivity.this, HomeScreen.class));
            }
        });
        MyDBHelper dbHelper = new MyDBHelper(this);
        String childName = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getString("childName",null);

        if (childName != null) {
            int score = dbHelper.getScore(childName);
            nameTextView.setText(childName+"'s");
            scoreTextView.setText(String.valueOf(score));
        } else {
            Log.e("QuizProgressActivity", "Child name is null");
        }
    }
}


