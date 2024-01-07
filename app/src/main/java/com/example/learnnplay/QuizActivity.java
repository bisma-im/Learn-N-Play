package com.example.learnnplay;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
public class QuizActivity extends AppCompatActivity {
    private ImageView questionImageView;
    private TextView timerTextView;
    private RadioGroup radioGroup;
    private Button submitButton;
    private Button restartButton;
    private TextView scoreTextView;
    MyDBHelper dbHelper;
    private String[] questions = {"question1", "question2", "question3", "question4", "question5"};
    private int[] questionImages = {R.drawable.hexagon, R.drawable.yellow, R.drawable.number1, R.drawable.a, R.drawable.shape_triangle};
    private String[] correctAnswers = {"HexaGon", "yellow Color", "11", "Apple", "Triangle"};
    private String[] options={"cat","none"};
    private int currentQuestionIndex = 0;
    private int score = 0;
    private Button backButton;


    private CountDownTimer timer;
    private long timeLeftInMillis = 20000; // 20 seconds

    private MediaPlayer mediaPlayer;
    String childName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionImageView = findViewById(R.id.questionImageView);
        timerTextView = findViewById(R.id.timerTextView);
        radioGroup = findViewById(R.id.radioGroup);
        submitButton = findViewById(R.id.submitBtn);
        restartButton = findViewById(R.id.restartBtn);
        scoreTextView = findViewById(R.id.scoreTextView);
        backButton = findViewById(R.id.backbutton);

        dbHelper = new MyDBHelper(this);
        childName = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getString("childName",null);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizActivity.this, HomeScreen.class));
            }
        });
        // Initialize MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.quiz_start_audio);

        // Start playing audio
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }

        displayQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartQuiz();
            }
        });

        startTimer();
    }

    private void displayQuestion() {
        questionImageView.setImageResource(questionImages[currentQuestionIndex]);
        RadioButton option1 = findViewById(R.id.option1);
        RadioButton option2 = findViewById(R.id.option2);
        RadioButton option3 = findViewById(R.id.option3);

        option1.setText("A. " + options[0]);
        option2.setText("B. " + correctAnswers[currentQuestionIndex]);
        option3.setText("C. " + options[1]);
    }

    private void checkAnswer() {
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            String selectedAnswer = selectedRadioButton.getText().toString().substring(3); // Remove the prefix "A. " or "B. " or "C. "
            String correctAnswer = correctAnswers[currentQuestionIndex];

            if (selectedAnswer.equals(correctAnswer)) {
                score++;
                updateScore();
            }

            if (currentQuestionIndex < questions.length - 1) {
                currentQuestionIndex++;
                displayQuestion();
                radioGroup.clearCheck();
            } else {
                showResults();
            }
        } else {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateScore() {
        scoreTextView.setText("Score: " + score);
    }

    private void showResults() {
        stopTimer();
        mediaPlayer.release(); // Release MediaPlayer resources
        mediaPlayer = null;

        String resultMessage = "Your score is: " + score + " out of " + questions.length;
        Toast.makeText(this, resultMessage, Toast.LENGTH_LONG).show();
        if (childName != null) {
            dbHelper.updateScore(childName, score);
        }

        // Show restart button
        restartButton.setVisibility(View.VISIBLE);
    }

    private void restartQuiz() {
        // Reset quiz state
        currentQuestionIndex = 0;
        score = 0;
        timeLeftInMillis = 20000;

        // Hide restart button
        restartButton.setVisibility(View.GONE);

        // Start playing audio
        mediaPlayer = MediaPlayer.create(this, R.raw.quiz_start_audio);
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }

        // Restart timer, update score, and display the first question
        startTimer();
        updateScore();
        displayQuestion();
    }

    private void startTimer() {
        timer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                showResults();
            }
        }.start();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void updateTimer() {
        int seconds = (int) (timeLeftInMillis / 1000);
        timerTextView.setText("Timer: " + seconds + "s");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}