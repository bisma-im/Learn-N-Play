package com.example.learnnplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CreateChildProfileActivity extends AppCompatActivity {

    List<ChildUser> childUsers = new ArrayList<>();
    EditText name, age;
    Button register;
    public String currentChildName;
    MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_child_profile);

        name = findViewById(R.id.editName);
        age = findViewById(R.id.editAge);
        register = findViewById(R.id.register);
        dbHelper = new MyDBHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentChildName = name.getText().toString();

                if (currentChildName.isEmpty() || age.getText().toString().isEmpty()) {
                    Toast.makeText(CreateChildProfileActivity.this,"Enter all fields", Toast.LENGTH_SHORT).show();
                }

                try {
                    int childAge = Integer.parseInt(age.getText().toString());
                    ChildUser childUser = new ChildUser(name.getText().toString(), childAge, 0);
                    childUsers.add(childUser);
                    dbHelper.addChild(name.getText().toString(), childAge);

                    Intent intent = new Intent(CreateChildProfileActivity.this, ChildProfiles.class);
                    startActivity(intent);
                } catch (NumberFormatException e) {
                    return;
                }
            }
        });

    }
}