package com.example.learnnplay;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class RegisterActivity extends AppCompatActivity {
    EditText inputemail, Password, cnfrmpassword;
    Button register,login;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (!isFirstRun) {
            Intent intent = new Intent(RegisterActivity.this, HomeScreen.class);
            startActivity(intent);
            finish();  // Finish the activity to prevent it from being opened again
        } else {
            setContentView(R.layout.activity_register);  // Set content view after checking isFirstRun
        }
            inputemail = findViewById(R.id.editEmail);
        Password = findViewById(R.id.editPassword);
        register = findViewById(R.id.register);
        cnfrmpassword = findViewById(R.id.confirmPass);
         login=findViewById(R.id.loginbtn);
         login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(RegisterActivity.this, Login.class));
                 finish();
             }
         });


        ProgressDialog progressDialog = new ProgressDialog(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerAuth();
            }
        });
        //   Update isFirstRun to false after registration
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).apply();
        finish();  // Finish the activity to prevent it from being opened again
    }
    public void PerAuth() {
        String email = inputemail.getText().toString();
        String password = Password.getText().toString();
        String cnfrmpass = cnfrmpassword.getText().toString();
        if (!email.matches(emailPattern)) {
            inputemail.setError("Enter Correct Email");
        } else if (password.isEmpty() || password.length() < 6) {
            Password.setError("Enter Proper Password");
        } else if (!password.equals(cnfrmpass)) {
            cnfrmpassword.setError("Password does not match");
        } else {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait, registering...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendUserToNextActivity() {
        Intent intent = new Intent(RegisterActivity.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
}
}