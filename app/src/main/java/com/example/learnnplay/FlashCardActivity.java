package com.example.learnnplay;
import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class FlashCardActivity extends AppCompatActivity {
    private List<FlashCardDTO> flashCards;
    private int currentFlashCardIndex = 0;
    private TextView textViewLetter;
    private ImageButton imageButtonFirst, imageButtonSecond, imageButtonThird;
    private EditText editTextFirst, editTextSecond, editTextThird;
    private TextView textViewFirst, textViewSecond, textViewThird;
    private Button nextButton, backButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);
        db = FirebaseFirestore.getInstance();

        initializeViews();
        fetchFlashCardsFromFirestore();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check for incorrect letter
                FlashCardDTO flashCard = flashCards.get(currentFlashCardIndex);
                if((editTextFirst.getText().toString().trim().equals(flashCard.getLetter()) ||
                        editTextFirst.getText().toString().trim().equals(flashCard.getLetter().toLowerCase())) &&
                        (editTextSecond.getText().toString().trim().equals(flashCard.getLetter()) ||
                        editTextSecond.getText().toString().trim().equals(flashCard.getLetter().toLowerCase())) &&
                        (editTextThird.getText().toString().trim().equals(flashCard.getLetter()) ||
                        editTextThird.getText().toString().trim().equals(flashCard.getLetter().toLowerCase()))){

                    // All fields are filled, proceed to the next flash card
                    currentFlashCardIndex++;

                    if(currentFlashCardIndex > flashCards.size()-1){
                        currentFlashCardIndex =0;
                    }
                    else{
                        loadFlashCard(currentFlashCardIndex);
                    }
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
        FlashCardDTO flashCard = flashCards.get(index);
        textViewLetter.setText(flashCard.getLetter());

        imageButtonFirst.setImageResource(getResourceIdByName(flashCard.getImageIdentifiers().get(0), "drawable"));
        imageButtonSecond.setImageResource(getResourceIdByName(flashCard.getImageIdentifiers().get(1), "drawable"));
        imageButtonThird.setImageResource(getResourceIdByName(flashCard.getImageIdentifiers().get(2), "drawable"));

        textViewFirst.setText(flashCard.getObjectNames().get(0).substring(1));
        textViewSecond.setText(flashCard.getObjectNames().get(1).substring(1));
        textViewThird.setText(flashCard.getObjectNames().get(2).substring(1));

        editTextFirst.setText("");
        editTextSecond.setText("");
        editTextThird.setText("");

        imageButtonFirst.setOnClickListener(null);
        imageButtonSecond.setOnClickListener(null);
        imageButtonThird.setOnClickListener(null);


        setupAudioPlayer(imageButtonFirst, getResourceIdByName(flashCard.getAudioIdentifiers().get(0), "raw"));
        setupAudioPlayer(imageButtonSecond, getResourceIdByName(flashCard.getAudioIdentifiers().get(1), "raw"));
        setupAudioPlayer(imageButtonThird, getResourceIdByName(flashCard.getAudioIdentifiers().get(2), "raw"));
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
    private void fetchFlashCardsFromFirestore() {
        db.collection("flashcard")
                .orderBy("letter")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            flashCards = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                FlashCardDTO flashCardDTO = document.toObject(FlashCardDTO.class);
                                flashCards.add(flashCardDTO);
                            }
                            if (!flashCards.isEmpty()) {
                                loadFlashCard(currentFlashCardIndex); // Load the first flashcard
                            }
                            if (!flashCards.isEmpty()) {
                                loadFlashCard(currentFlashCardIndex);
                            }
                        } else {
                            Log.w("FlashCardActivity", "Error getting documents.", task.getException());
                        }
                    }
                });
    }



    private int getResourceIdByName(String resourceName, String resourceType) {
        return getResources().getIdentifier(resourceName, resourceType, getPackageName());
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