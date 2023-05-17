package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        TextView nicknameTextView = findViewById(R.id.recipe_author);
        // to do: load RecyclerView in recipe ingredients view.
//        LinearLayout ingredientsLayout = findViewById(R.id.recipe_ingredients);
        // load RecyclerView in recipe steps view.
        RecyclerView stepList = findViewById(R.id.recipe_step_layout);
        stepList.setLayoutManager(new LinearLayoutManager(this));
        stepList.setAdapter(new RecipeStepAdapter(recipe.getRecipeSteps()));
//        LinearLayout stepsLayout = findViewById(R.id.recipe_steps);
        // TODO: load image from URL
//      Glide.with(this).load(recipe.getBannerImageUrl()).into(bannerImageView);
        titleTextView.setText(recipe.getTitle());
        nicknameTextView.setText(recipe.getNickName());
        bannerImageView.setImageResource(R.drawable.image2);
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
