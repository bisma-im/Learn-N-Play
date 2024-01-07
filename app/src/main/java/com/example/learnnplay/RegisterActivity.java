package com.example.learnnplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    EditText inputemail, Password, cnfrmpassword;
    Button register;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
   // MyDBHelper dbHelper = new MyDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if it's the first run
//        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
//                .getBoolean("isFirstRun", true);
//
//        if (!isFirstRun) {
//            Intent intent = new Intent(RegisterActivity.this, ChildProfiles.class);
//            startActivity(intent);
//            finish();  // Finish the activity to prevent it from being opened again
//        } else {
//            setContentView(R.layout.activity_register);  // Set content view after checking isFirstRun
//
              inputemail = findViewById(R.id.editEmail);
            Password = findViewById(R.id.editPassword);
              register = findViewById(R.id.register);
              cnfrmpassword=findViewById(R.id.confirmPass);

              register.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                  }
              });
              public void PerAuth(){
                  String email=inputemail.getText().toString();
                  String password=Password.getText().
        }
//            register.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dbHelper.addParent(email.getText().toString(), password.getText().toString());
//                    Intent intent = new Intent(RegisterActivity.this, CreateChildProfileActivity.class);
//                    startActivity(intent);
//
//                  //   Update isFirstRun to false after registration
//                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
//                            .putBoolean("isFirstRun", false).apply();
//                    finish();  // Finish the activity to prevent it from being opened again
//                }
//            });
//        }
    }
}
