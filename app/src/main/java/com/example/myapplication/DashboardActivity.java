package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        List<Integer> images = Arrays.asList(R.drawable.pic1, R.drawable.pic1, R.drawable.pic1);
        for (int i = 0; i < images.size(); i++) {
            int imageResourceId = images.get(i);
            Log.d("ImageDemo", "Image " + (i+1) + " resource ID: " + imageResourceId);
        }
        GalleryAdapter adapter = new GalleryAdapter(images);
        RecyclerView galleryRecyclerView = findViewById(R.id.galleryRecyclerView);
        galleryRecyclerView.setAdapter(adapter);
    }
}
