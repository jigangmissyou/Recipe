package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView profileName = findViewById(R.id.profile_name);
        TextView profileBio = findViewById(R.id.profile_bio);
        String name = "Jigang Guo";
        String bio = "I'm a new android developer.";
        profileName.setText(name);
        profileBio.setText(bio);
    }
}
