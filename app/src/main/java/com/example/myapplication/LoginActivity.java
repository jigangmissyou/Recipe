package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (username.isEmpty()) {
                    usernameEditText.setError("Username is required");
                    usernameEditText.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    passwordEditText.setError("Password is required");
                    passwordEditText.requestFocus();
                    return;
                }

                // Check user credentials against a database or web service
                // and start the dashboard activity if login is successful
                if (authenticateUser(username, password)) {
                    // session management
                    SharedPreferences sharedPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("is_logged_in", true);
                    editor.putString("username", username);
                    editor.apply();
                    Log.d("LoginActivity", "onClick: " + sharedPref.getBoolean("is_logged_in", false));
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean authenticateUser(String username, String password) {
       DbHandler dbHandler = new DbHandler(this);
       boolean ret = dbHandler.findUser(username, password);
       Log.d("LoginActivity", "authenticateUser: " + ret);
       return ret;
//        return email.equals("demo@demo.com") && password.equals("123456");
    }
}

