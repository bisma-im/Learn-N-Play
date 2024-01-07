package com.example.learnnplay;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class ShapesRecognition extends AppCompatActivity {

    private static final String TAG = "ShapesRecognition";

    private ImageView shapeImage;
    private ImageButton btnPrevious, btnNext;
    private TextView shapeNameTextView;
    private int currentShapeIndex = 0;
    private int[] shapeImages = {R.drawable.shape_circle, R.drawable.shape_square, R.drawable.shape_triangle, R.drawable.hexagon, R.drawable.rectangle};
    private String[] shapeNames = {"Circle", "Square", "Triangle", "Hexagon", "Rectangle"};
    private MediaPlayer mediaPlayer;
  private  Button backButton;
  private  Button buttonsetting;
    private static final int RESTART_DELAY = 30 * 1000; // 30 seconds
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shapes_recognition);

        shapeImage = findViewById(R.id.shapeImage);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        shapeNameTextView = findViewById(R.id.shapeNameTextView);
        backButton=findViewById(R.id.backbutton);
        buttonsetting=findViewById(R.id.buttonsetting);
        buttonsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShapesRecognition.this, AppSetting.class));
                finish();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShapesRecognition.this,HomeScreen.class));
                finish();
            }

        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousShape();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextShape();
            }
        });

        shapeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playShapeNameAudio();
            }
        });

        updateShapeImage();
    }

    private void showPreviousShape() {
        currentShapeIndex = (currentShapeIndex - 1 + shapeImages.length) % shapeImages.length;
        updateShapeImage();
    }

    private void showNextShape() {
        currentShapeIndex = (currentShapeIndex + 1) % shapeImages.length;
        updateShapeImage();
    }

    private void updateShapeImage() {
        shapeImage.setImageResource(shapeImages[currentShapeIndex]);
        shapeNameTextView.setText(shapeNames[currentShapeIndex]);
    }

    private void playShapeNameAudio() {
        int audioResourceId = getResources().getIdentifier(
                shapeNames[currentShapeIndex].toLowerCase(), "raw", getPackageName());

        Log.d(TAG, "Audio Resource ID: " + audioResourceId);

        if (audioResourceId != 0) {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }

            mediaPlayer = MediaPlayer.create(this, audioResourceId);

            if (mediaPlayer != null) {
                mediaPlayer.start();
            } else {
                Log.e(TAG, "Error creating MediaPlayer");
            }
        } else {
            Log.e(TAG, "Audio resource not found");
        }
    }
    private void scheduleRestart() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Restart the activity
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }, RESTART_DELAY);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
