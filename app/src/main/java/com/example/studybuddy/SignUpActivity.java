package com.example.studybuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText signUpEmail, signUpPassword;
    private Button signup_button ;
    private TextView loginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        signUpEmail = findViewById(R.id.signup_email);
        signUpPassword = findViewById(R.id.signup_password);
        loginRedirectText = findViewById(R.id.logInRedirectText);
        signup_button = findViewById(R.id.signup_button);


        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = signUpEmail.getText().toString().trim();
                String pass = signUpPassword.getText().toString().trim();

                if(user.isEmpty())
                {
                    signUpEmail.setError("Email cannot be empty");

                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(user).matches())
                {
                    signUpEmail.setError("Please enter a valid email");
                }
                if(pass.isEmpty())
                {
                    signUpPassword.setError("Password cannot be empty");
                }
                else
                {
                    auth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(SignUpActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this,LogInActivity.class));
                            }
                            else
                            {
                                Toast.makeText(SignUpActivity.this, "Signup Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LogInActivity.class));
            }
        });

    }
}