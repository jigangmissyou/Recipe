package com.example.myapplication;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameEditText, emailEditText, passwordEditText;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // findViewById() is a method of the Activity class
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = passwordEditText.getText().toString();

                // username is required
                if (username.isEmpty()) {
                    usernameEditText.setError("Username is required!");
                    usernameEditText.requestFocus();
                    return;
                }
                // validate the username length
                if (username.length() < 3 || username.length() > 20) {
                    usernameEditText.setError("Username must between 3 and 20 characters long!");
                    usernameEditText.requestFocus();
                    return;
                }
                // email is required
                if (email.isEmpty()) {
                    emailEditText.setError("Email is required!");
                    emailEditText.requestFocus();
                    return;
                }
                // validate the email address format
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.setError("Please enter a valid email address!");
                    emailEditText.requestFocus();
                    return;
                }
                // password is required
                if (password.isEmpty()) {
                    passwordEditText.setError("Password is required!");
                    passwordEditText.requestFocus();
                    return;
                }
                // validate the password length
                if (password.length() < 6) {
                    passwordEditText.setError("Password must be at least 6 characters long!");
                    passwordEditText.requestFocus();
                    return;
                }

                // validate the confirm password
                if (!password.equals(confirmPassword)) {
                    passwordEditText.setError("Passwords do not match!");
                    passwordEditText.requestFocus();
                    return;
                }

                // validate the confirm password
                if (!password.equals(passwordEditText.getText().toString())) {
                    passwordEditText.setError("Passwords do not match!");
                    passwordEditText.requestFocus();
                    return;
                }
                // TODO: Send the registration data to the server for processing
                Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
