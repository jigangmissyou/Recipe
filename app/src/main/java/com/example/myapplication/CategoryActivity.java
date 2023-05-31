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
        categories.add(new Category("Breakfast", Arrays.asList("Eggs", "Pancakes", "Toast")));
        categories.add(new Category("Lunch", Arrays.asList("Salad", "Sandwich", "Soup")));
        categories.add(new Category("Dinner", Arrays.asList("Chicken", "Beef", "Fish")));
        categories.add(new Category("Dessert", Arrays.asList("Cake", "Ice Cream", "Cookies")));

        CategoryAdapter adapter = new CategoryAdapter(categories);
        recyclerView.setAdapter(adapter);
    }
}