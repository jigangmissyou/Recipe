package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements GalleryAdapter.OnItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        showGallery();
        showContentList();
    }

    @Override
    public void onItemClick(int position) {
        Log.d("ImageDemo", "Clicked on image " + (position+1));
    }

    /**
     * Show a horizontal gallery of images
     */
    public void showGallery(){
        List<Integer> images = Arrays.asList(R.drawable.pic2, R.drawable.pic2, R.drawable.pic2);
        for (int i = 0; i < images.size(); i++) {
            int imageResourceId = images.get(i);
            Log.d("ImageDemo", "Image " + (i+1) + " resource ID: " + imageResourceId);
        }
        GalleryAdapter adapter = new GalleryAdapter(images, this);
        RecyclerView galleryRecyclerView = findViewById(R.id.galleryRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        galleryRecyclerView.setLayoutManager(layoutManager);
        galleryRecyclerView.setAdapter(adapter);
    }

    /**
     * Show a vertical list of content items
     */
    public void showContentList(){
        List<ContentItem> contentItems = Arrays.asList(
                new ContentItem(R.drawable.pic2, "Title 1", "Description 1", true, true),
                new ContentItem(R.drawable.pic2, "Title 2", "Description 2", true, false),
                new ContentItem(R.drawable.pic2, "Title 3", "Description 3", false, true),
                new ContentItem(R.drawable.pic2, "Title 4", "Description 4", false, false),
                new ContentItem(R.drawable.pic2, "Title 5", "Description 5", true, true),
                new ContentItem(R.drawable.pic2, "Title 6", "Description 6", true, false),
                new ContentItem(R.drawable.pic2, "Title 7", "Description 7",    false, true),
                new ContentItem(R.drawable.pic2, "Title 8", "Description 8", false, false),
                new ContentItem(R.drawable.pic2, "Title 9", "Description 9", true, true),
                new ContentItem(R.drawable.pic2, "Title 10", "Description 10", true, false),
                new ContentItem(R.drawable.pic2, "Title 11", "Description 11", false, true),
                new ContentItem(R.drawable.pic2, "Title 12", "Description 12", false, false),
                new ContentItem(R.drawable.pic2, "Title 13", "Description 13", true, true),
                new ContentItem(R.drawable.pic2, "Title 14", "Description 14", true, false),
                new ContentItem(R.drawable.pic2, "Title 15", "Description 15", false, true),
                new ContentItem(R.drawable.pic2, "Title 16", "Description 16", false, false),
                new ContentItem(R.drawable.pic2, "Title 17", "Description 17", true, true),
                new ContentItem(R.drawable.pic2, "Title 18", "Description 18", true, false),
                new ContentItem(R.drawable.pic2, "Title 19", "Description 19", false, true),
                new ContentItem(R.drawable.pic2, "Title 20", "Description 20", false, false),
                new ContentItem(R.drawable.pic2, "Title 21", "Description 21", true, true)
                );
//
        Adapter adapter2 = new Adapter(this, contentItems);
        RecyclerView contentRecyclerView = findViewById(R.id.content_item_layout);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this);
        contentRecyclerView.setLayoutManager(layoutManager2);
        contentRecyclerView.setAdapter(adapter2);
    }
}
