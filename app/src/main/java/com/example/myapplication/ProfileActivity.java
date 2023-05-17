package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView profileName = findViewById(R.id.profile_name);
        TextView profileBio = findViewById(R.id.profile_bio);
        String name = getUsername();
        String bio = "I want to be a Chef :)";
        profileName.setText(name);
        profileBio.setText(bio);
        Button logout = findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(v);
            }
        });

    }
    //log out
     public void logout(View view) {
         // shared preferences set to false
            SharedPreferences sharedPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("is_logged_in", false);
            editor.apply();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
     }

     private String getUsername() {
         SharedPreferences sharedPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
         return sharedPref.getString("username", "");
     }
}
