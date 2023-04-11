package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

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
        showBottomNav();
        layoutTab();
        search();
    }

    @Override
    public void onItemClick(int position) {
        Log.d("ImageDemo", "Clicked on image " + (position + 1));
    }

    /**
     * Show a horizontal gallery of images
     */
    public void showGallery() {
        List<Integer> images = Arrays.asList(R.drawable.pic2, R.drawable.pic2, R.drawable.pic2);
        for (int i = 0; i < images.size(); i++) {
            int imageResourceId = images.get(i);
            Log.d("ImageDemo", "Image " + (i + 1) + " resource ID: " + imageResourceId);
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
    public void showContentList() {
        // last parameter is an array of strings
        ArrayList<RecipeStep> recipeSteps = new ArrayList<>();
        recipeSteps.add(new RecipeStep("Step 1", R.drawable.pic1));
        recipeSteps.add(new RecipeStep("Step 2", R.drawable.pic2));
        List<Recipe> recipes = Arrays.asList(
                new Recipe(R.drawable.pic2, "Title 1", "Description 1", true, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 2", "Description 2", true, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 3", "Description 3", false, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 4", "Description 4", false, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 5", "Description 5", true, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 6", "Description 6", true, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 7", "Description 7", false, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 8", "Description 8", false, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 9", "Description 9", true, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 10", "Description 10", true, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 11", "Description 11", false, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 12", "Description 12", false, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 13", "Description 13", true, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 14", "Description 14", true, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 15", "Description 15", false, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 16", "Description 16", false, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 17", "Description 17", true, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 18", "Description 18", true, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 19", "Description 19", false, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 20", "Description 20", false, false, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps),
                new Recipe(R.drawable.pic2, "Title 21", "Description 21", true, true, "Jigang", new String[]{"Tag 1", "Tag 2", "Tag 3"}, recipeSteps)
        );
        DashboardAdapter adapter2 = new DashboardAdapter(this, recipes);
        RecyclerView contentRecyclerView = findViewById(R.id.content_item_layout);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this);
        contentRecyclerView.setLayoutManager(layoutManager2);
        contentRecyclerView.setAdapter(adapter2);
        adapter2.setOnItemClickListener(new DashboardAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Recipe item) {
                // Navigate to the detail activity
                Intent intent = new Intent(DashboardActivity.this, DetailActivity.class);
                intent.putExtra("item", item);
                Log.d("ITEM", "Title: " + item.getTitle());
                Log.d("ITEM", "Description: " + item.getDescription());
                startActivity(intent);
            }
        });
    }

    /**
     * Show bottom navigation bar
     */
    public void showBottomNav() {
        // show bottom navigation, but don't do anything when a menu item is clicked

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_1:
                        // Handle menu item 1 click
                        return true;
                    case R.id.menu_item_2:
                        //Navigate to recipe add activity
                        Intent intent = new Intent(DashboardActivity.this, RecipeAddActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_item_3:
                        Toast.makeText(DashboardActivity.this, "Please wait to release :)", Toast.LENGTH_SHORT).show();
                        // TODO IF USER IS NOT LOGGED IN, NAVIGATE TO LOGIN ACTIVITY
                        return true;
                    case R.id.menu_item_4:
                        // TODO IF USER IS NOT LOGGED IN, NAVIGATE TO LOGIN ACTIVITY
                        // Navigate to the profile activity
                        Intent intent2 = new Intent(DashboardActivity.this, ProfileActivity.class);
                        startActivity(intent2);
                        return true;
                    default:
                        return false;
                }
            }

        });
    }

    /**
     * Show top navigation bar
     */
    public void layoutTab(){
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        // Handle click on first tab item
                        Toast.makeText(DashboardActivity.this, "Tab 1", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        // Handle click on second tab item
                        Toast.makeText(DashboardActivity.this, "Tab 2", Toast.LENGTH_SHORT).show();
                        // navigate to category activity
                        Intent intent = new Intent(DashboardActivity.this, CategoryActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }

    public void search(){
        // Get a reference to the SearchView
        SearchView searchView = findViewById(R.id.search_view);
        // Set up a listener for when the user submits a search query
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO WILL BE IMPLEMENTED IN THE FUTURE
                Toast.makeText(DashboardActivity.this, "Search: submit " + query, Toast.LENGTH_SHORT).show();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO WILL BE IMPLEMENTED IN THE FUTURE
                Toast.makeText(DashboardActivity.this, "Search: change" + newText, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
