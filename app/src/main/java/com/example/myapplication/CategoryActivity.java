package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        recyclerView = findViewById(R.id.category_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Parent Category 1", Arrays.asList("Subcategory 1", "Subcategory 2", "Subcategory 3")));
        categories.add(new Category("Parent Category 2", Arrays.asList("Subcategory 4", "Subcategory 5")));
        categories.add(new Category("Parent Category 3", Arrays.asList("Subcategory 6", "Subcategory 7", "Subcategory 8", "Subcategory 9")));

        CategoryAdapter adapter = new CategoryAdapter(categories);
        recyclerView.setAdapter(adapter);
    }
}