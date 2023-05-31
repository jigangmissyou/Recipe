package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements RecipeStepAdapter.OnShareClickListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        // retrieve recipe from intent
        Recipe recipe = (Recipe) intent.getSerializableExtra("item");
        ArrayList<RecipeIngredient> ingredientList = recipe.getIngredients();
        RecyclerView stepList = findViewById(R.id.recipe_step_layout);
        stepList.setLayoutManager(new LinearLayoutManager(this));
        Log.d("RecipeStepAdapter", "Recipe Steps: " + recipe.getRecipeSteps().toString());
        RecipeStepAdapter adapter = new RecipeStepAdapter(recipe.getTitle(),recipe.getDescription(), recipe.getNickName(), ingredientList, recipe.getRecipeSteps());
        stepList.setAdapter(new RecipeStepAdapter(recipe.getTitle(),recipe.getDescription(), recipe.getNickName(), ingredientList, recipe.getRecipeSteps()));
        adapter.setOnShareClickListener(this);
    }

    // share recipe
    public void onShareClick(String title, String nickName) {
        // 处理分享逻辑
        String shareText = "Check out this recipe: " + title + " by " + nickName;
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        Intent chooser = Intent.createChooser(shareIntent, "Share this recipe");
        if (shareIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

}
