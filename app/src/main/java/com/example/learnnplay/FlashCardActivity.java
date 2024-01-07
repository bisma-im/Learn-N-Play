package com.example.learnnplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FlashCardActivity extends AppCompatActivity {
    private List<FlashCard> flashCards;
    private int currentFlashCardIndex = 0;
    private TextView textViewLetter;
    private ImageButton imageButtonFirst, imageButtonSecond, imageButtonThird;
    private EditText editTextFirst, editTextSecond, editTextThird;
    private TextView textViewFirst, textViewSecond, textViewThird;
    private Button nextButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);

        initializeViews();
        initializeFlashCards();
        loadFlashCard(currentFlashCardIndex);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check for incorrect letter
                FlashCard flashCard = flashCards.get(currentFlashCardIndex);
                if((editTextFirst.getText().toString().trim().equals(flashCard.letter) ||
                        editTextFirst.getText().toString().trim().equals(flashCard.letter.toLowerCase())) &&
                        (editTextSecond.getText().toString().trim().equals(flashCard.letter) ||
                        editTextSecond.getText().toString().trim().equals(flashCard.letter.toLowerCase())) &&
                        (editTextThird.getText().toString().trim().equals(flashCard.letter) ||
                        editTextThird.getText().toString().trim().equals(flashCard.letter.toLowerCase()))){

                    // All fields are filled, proceed to the next flash card
                    currentFlashCardIndex++;
                    if (currentFlashCardIndex >= flashCards.size()) {
                        currentFlashCardIndex = 0; // Loop back to the first card
                    }
                    loadFlashCard(currentFlashCardIndex);
                }
                // Check if any of the EditText fields are empty
                else if (editTextFirst.getText().toString().trim().isEmpty() ||
                        editTextSecond.getText().toString().trim().isEmpty() ||
                        editTextThird.getText().toString().trim().isEmpty()) {

                    // Show a toast message if any field is empty
                    Toast.makeText(FlashCardActivity.this, "Please enter the first letter(s)", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FlashCardActivity.this, "Please enter the correct letter", Toast.LENGTH_SHORT).show();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashCardActivity.this, HomeScreen.class);
                startActivity(intent);
            }
        });

    }

    private void loadFlashCard(int index) {
        FlashCard flashCard = flashCards.get(index);
        textViewLetter.setText(flashCard.letter);

        imageButtonFirst.setImageResource(flashCard.images[0]);
        imageButtonSecond.setImageResource(flashCard.images[1]);
        imageButtonThird.setImageResource(flashCard.images[2]);

        textViewFirst.setText(flashCard.objectNames[0].substring(1));
        textViewSecond.setText(flashCard.objectNames[1].substring(1));
        textViewThird.setText(flashCard.objectNames[2].substring(1));

        editTextFirst.setText("");
        editTextSecond.setText("");
        editTextThird.setText("");

        imageButtonFirst.setOnClickListener(null);
        imageButtonSecond.setOnClickListener(null);
        imageButtonThird.setOnClickListener(null);

        // Set up onClickListeners for each ImageButton to play the corresponding audio
        setupAudioPlayer(imageButtonFirst, flashCard.audios[0]);
        setupAudioPlayer(imageButtonSecond, flashCard.audios[1]);
        setupAudioPlayer(imageButtonThird, flashCard.audios[2]);
    }

    private void setupAudioPlayer(ImageButton imageButton, int audioResId) {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = MediaPlayer.create(FlashCardActivity.this, audioResId);
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mediaPlayer.release();
                        }
                    });
                } else {
                    // Handle the error, the media player was not created properly
                    Log.e("FlashCardActivity", "Error creating media player for audioResId: " + audioResId);
                }
            }
        });
    }

    private void initializeFlashCards() {
        flashCards = new ArrayList<>();

        flashCards.add(new FlashCard(
                "A",
                new String[] {"Ant", "Apple", "Astronaut"},
                new int[] {R.drawable.ant, R.drawable.apple, R.drawable.astronaut},
                new int[] {R.raw.a_for_ant, R.raw.a_for_apple, R.raw.a_for_astronaut}
        ));

        flashCards.add(new FlashCard(
                "B",
                new String[] {"Ball", "Banana", "Butterfly"},
                new int[] {R.drawable.ball, R.drawable.banana, R.drawable.butterfly},
                new int[] {R.raw.b_for_ball, R.raw.b_for_banana, R.raw.b_for_butterfly}
        ));

        flashCards.add(new FlashCard(
                "C",
                new String[] {"Cake", "Cat", "Clock"},
                new int[] {R.drawable.cake, R.drawable.cat, R.drawable.clock},
                new int[] {R.raw.c_for_cake, R.raw.c_for_cat, R.raw.c_for_clock}
        ));

        flashCards.add(new FlashCard(
                "D",
                new String[] {"Doll", "Dog", "Doughnut"},
                new int[] {R.drawable.doll, R.drawable.dog, R.drawable.doughnut},
                new int[] {R.raw.d_for_doll, R.raw.d_for_dog, R.raw.d_for_doughnut}
        ));
    }

    private void initializeViews() {
        textViewLetter = findViewById(R.id.alphabet);
        imageButtonFirst = findViewById(R.id.firstObjButton);
        imageButtonSecond = findViewById(R.id.secondObjButton);
        imageButtonThird = findViewById(R.id.thirdObjButton);
        editTextFirst = findViewById(R.id.firstObjEditText);
        editTextSecond = findViewById(R.id.secondObjEditText);
        editTextThird = findViewById(R.id.thirdObjEditText);
        textViewFirst = findViewById(R.id.firstObjTextView);
        textViewSecond = findViewById(R.id.secondObjTextView);
        textViewThird = findViewById(R.id.thirdObjTextView);
        nextButton = findViewById(R.id.next);
        backButton = findViewById(R.id.back);
    }
}