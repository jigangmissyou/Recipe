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

public class DetailActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        // retrieve recipe from intent
        Recipe recipe = (Recipe) intent.getSerializableExtra("item");
        // bind views
        ImageView bannerImageView = findViewById(R.id.banner_image);
        TextView titleTextView = findViewById(R.id.recipe_title);
        TextView descriptionTextView = findViewById(R.id.recipe_desc);
        TextView nicknameTextView = findViewById(R.id.recipe_author);
        RecyclerView ingredientRecyclerView = findViewById(R.id.recipe_ingredients_layout);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<RecipeIngredient> ingredientList = recipe.getIngredients();
        // set adapter for ingredientRecyclerView
        IngredientAdapter ingredientAdapter = new IngredientAdapter(ingredientList);
        ingredientRecyclerView.setAdapter(ingredientAdapter);


        RecyclerView stepList = findViewById(R.id.recipe_step_layout);
        stepList.setLayoutManager(new LinearLayoutManager(this));
        Log.d("RecipeStepAdapter", "Recipe Steps: " + recipe.getRecipeSteps().toString());
        stepList.setAdapter(new RecipeStepAdapter(recipe.getRecipeSteps()));
//        LinearLayout stepsLayout = findViewById(R.id.recipe_steps);
//      Glide.with(this).load(recipe.getBannerImageUrl()).into(bannerImageView);
        titleTextView.setText(recipe.getTitle());
        descriptionTextView.setText(recipe.getDescription());
        nicknameTextView.setText("By: "+recipe.getNickName());
        // select the first step image as banner image
        if (!recipe.getRecipeSteps().isEmpty()) {
            int lastStepIndex = recipe.getRecipeSteps().size() - 1;
            if (lastStepIndex >= 0) {
                Step lastStep = recipe.getRecipeSteps().get(lastStepIndex);
                String lastStepImagePath = lastStep.getImagePath();
                if (lastStepImagePath != null) {
                    // Load the last step image into the bannerImageView
                    Bitmap bitmap = BitmapFactory.decodeFile(lastStepImagePath);
                    bannerImageView.setImageBitmap(bitmap);
                }
            }
        }
        // get recipe.getRecipeSteps and set the first image to bannerImageView
        // show the first step image in bannerImageView
//        bannerImageView.setImageResource(R.drawable.image2);
        shareRecipe();
        // Abandoned code
//        for (RecipeStep step : recipe.getRecipeSteps()) {
//            View stepView = LayoutInflater.from(this).inflate(R.layout.recipe_step, stepsLayout, false);
//            ImageView stepImageView = stepView.findViewById(R.id.step1_image);
//            TextView stepTextView = stepView.findViewById(R.id.step1_text);
////            Glide.with(this).load(step.getImageUrl()).into(stepImageView);
//            stepImageView.setImageResource(step.getImageResourceId());
//            stepTextView.setText(step.getDescription());
//            stepsLayout.addView(stepView);
//        }

    }

    // share recipe
    public void shareRecipe() {
        ImageButton shareButton = findViewById(R.id.share_button);
        // add listener to share button
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Recipe recipe = (Recipe) intent.getSerializableExtra("item");
                String shareText = "Check out this recipe: " + recipe.getTitle() + " by " + recipe.getNickName();
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                Intent chooser = Intent.createChooser(shareIntent, "Share this recipe");
                if (shareIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });

    }

}
