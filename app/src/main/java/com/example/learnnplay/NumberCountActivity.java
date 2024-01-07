package com.example.learnnplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NumberCountActivity extends AppCompatActivity {

    EditText answer1, answer2, answer3, answer4;
    Button next, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_count);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer1.getText().toString().equals("5")  && answer2.getText().toString().equals("10")
                && answer3.getText().toString().equals("3")  && answer4.getText().toString().equals("1")){
                    Intent intent = new Intent(NumberCountActivity.this, HomeScreen.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(NumberCountActivity.this, "Enter the correct answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NumberCountActivity.this, HomeScreen.class));
            }
        });
    }
}