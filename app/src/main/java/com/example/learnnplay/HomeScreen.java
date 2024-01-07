package com.example.learnnplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

public class HomeScreen extends AppCompatActivity {
    private  Button ShapeButton;
    private  Button ColorButton;
    private Button appsettingbtn;
    private  Button quizbutton;
    private  Button alpha;
    private Button numberbtn, imageRecog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ShapeButton=findViewById(R.id.buttonShape);
        ColorButton=findViewById(R.id.buttonColor);
        appsettingbtn=findViewById(R.id.appsettingbtn);
        quizbutton=findViewById(R.id.quizbutton);
        alpha=findViewById(R.id.buttonAlphabet);
        numberbtn=findViewById(R.id.buttonNumber);
        numberbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, AdditionActivity.class));
                finish();
            }
        });

        imageRecog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this, ImageRecognitionActivity.class));
                finish();
            }
        });
        alpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this,FlashCardActivity.class));
                finish();
            }
        });
        ShapeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this,ShapesRecognition.class));
                finish();
            }
        });
        ColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this,ColorsRecognition.class));
                finish();
            }
        });
        appsettingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this,AppSetting.class));
                finish();
            }
        });
        quizbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this,QuizActivity.class));
                finish();
            }
        });
    }

}