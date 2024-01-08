package com.example.learnnplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class CreateChildProfileActivity extends AppCompatActivity {

    EditText name, age;
    Button register;
    public String currentChildName;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_child_profile);

        name = findViewById(R.id.editName);
        age = findViewById(R.id.editAge);
        register = findViewById(R.id.register);

        // Initialize Firebase Firestore
        firestore = FirebaseFirestore.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentChildName = name.getText().toString();

                if (currentChildName.isEmpty() || age.getText().toString().isEmpty()) {
                    Toast.makeText(CreateChildProfileActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int childAge = Integer.parseInt(age.getText().toString());

                    // Create a ChildDTO object
                    ChildDTO childDTO = new ChildDTO(currentChildName, childAge, 0);

                    // Save the child profile to Firestore
                    saveChildProfileToFirestore(childDTO);

                    // Redirect to ChildProfiles activity
                    Intent intent = new Intent(CreateChildProfileActivity.this, ChildProfiles.class);
                    startActivity(intent);

                } catch (NumberFormatException e) {
                    Toast.makeText(CreateChildProfileActivity.this, "Invalid age format", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveChildProfileToFirestore(ChildDTO childDTO) {
        // Ensure the document ID is unique and does not contain spaces
        String sanitizedChildName = currentChildName.replaceAll("\\s", "_");

        // Add the child profile to Firestore
        firestore.collection("childProfiles")
                .document(sanitizedChildName)
                .set(childDTO)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Child profile added successfully"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error adding child profile", e));
    }
}
