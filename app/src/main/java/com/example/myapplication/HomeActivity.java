package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements GalleryAdapter.OnItemClickListener {
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
        DbHandler dbHandler = new DbHandler(this);
        ArrayList<Recipe> recipes = dbHandler.getAllRecipe(" ID ");
        HomeAdapter adapter2 = new HomeAdapter(this, recipes);
        RecyclerView contentRecyclerView = findViewById(R.id.content_item_layout);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this);
        contentRecyclerView.setLayoutManager(layoutManager2);
        contentRecyclerView.setAdapter(adapter2);
        adapter2.setOnItemClickListener(new HomeAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Recipe item) {
                Log.d("ITEM", "item: " + item.toString());
                // Navigate to the detail activity
                Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
                intent.putExtra("item", item);
                Log.d("ITEM", "Title: " + item.getTitle());
                Log.d("ITEM", "Description: " + item.getDescription());
                startActivity(intent);
            }
        });
        adapter2.setOnEditClickListener(new HomeAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(Recipe recipe) {
                Intent editIntent = new Intent(HomeActivity.this,RecipeFormActivity.class);
                editIntent.putExtra("recipeId", recipe.getId());
                startActivity(editIntent);
            }
        });
        adapter2.setOnDeleteClickListener(new HomeAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(Recipe recipe) {
                // dialog to confirm delete
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setMessage("Are you sure you want to delete this recipe?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete recipe from database
                        dbHandler.deleteRecipe(recipe.getId());
                        // refresh activity
                        finish();
                        startActivity(getIntent());
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
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
                        Intent intent = new Intent(HomeActivity.this, RecipeFormActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_item_3:
                        Toast.makeText(HomeActivity.this, "Please wait to release :)", Toast.LENGTH_SHORT).show();
                        // TODO IF USER IS NOT LOGGED IN, NAVIGATE TO LOGIN ACTIVITY
                        return true;
                    case R.id.menu_item_4:
                        // TODO IF USER IS NOT LOGGED IN, NAVIGATE TO LOGIN ACTIVITY
                        // Navigate to the profile activity
                        Intent intent2 = new Intent(HomeActivity.this, ProfileActivity.class);
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
                        Toast.makeText(HomeActivity.this, "Tab 1", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        // Handle click on second tab item
                        Toast.makeText(HomeActivity.this, "Tab 2", Toast.LENGTH_SHORT).show();
                        // navigate to category activity
                        Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
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
                Toast.makeText(HomeActivity.this, "Search: submit " + query, Toast.LENGTH_SHORT).show();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO WILL BE IMPLEMENTED IN THE FUTURE
                Toast.makeText(HomeActivity.this, "Search: change" + newText, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
