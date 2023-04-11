package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecipeAddActivity extends AppCompatActivity {

    private EditText mEditTextRecipeName;
    private LinearLayout mLayoutRecipeIngredients;
    private LinearLayout mLayoutRecipeSteps;
    private EditText mEditTextRecipeDescription;
    private Button mButtonAddRecipe;
    private Button buttonAddIngredient;
    private Button buttonAddStep;

    private Button buttonDelIngredient;

    private Button buttonDelStep;
    private int mIngredientCount = 1;
    private int mStepCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_add);

        // Initialize views
        mEditTextRecipeName = findViewById(R.id.edit_text_recipe_name);
        mEditTextRecipeDescription = findViewById(R.id.edit_text_recipe_description);
        mLayoutRecipeIngredients = findViewById(R.id.layout_recipe_ingredients);
        mLayoutRecipeSteps = findViewById(R.id.layout_recipe_steps);
        mButtonAddRecipe = findViewById(R.id.button_add_recipe);
        buttonAddIngredient = findViewById(R.id.button_add_ingredient);
        buttonAddStep = findViewById(R.id.button_add_step);
        buttonDelIngredient = findViewById(R.id.button_del_ingredient);
        buttonDelStep = findViewById(R.id.button_del_step);

        // Set click listener for add recipe button
        mButtonAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe();
            }
        });

        buttonAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredientField();
            }
        });

        buttonAddStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStepField();
            }
        });

        buttonDelIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteIngredientField();
            }
        });

        buttonDelStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStepField();
            }
        });

        // Add initial ingredient and step fields
        addIngredientField();
        addStepField();
//        deleteIngredientField();
//        deleteStepField();
    }

    private void addRecipe() {
        //TODO: Add recipe to database
        Toast.makeText(this, "Recipe added", Toast.LENGTH_SHORT).show();
        // Get recipe name
//        String recipeName = mEditTextRecipeName.getText().toString();

//        // Get recipe ingredients
//        List<String> ingredients = new ArrayList<>();
//        for (int i = 0; i < mIngredientCount; i++) {
//            EditText editTextIngredient = (EditText) mLayoutRecipeIngredients.getChildAt(i);
//            String ingredient = editTextIngredient.getText().toString();
//            if (!TextUtils.isEmpty(ingredient)) {
//                ingredients.add(ingredient);
//            }
//        }
//
//        // Get recipe steps
//        List<String> steps = new ArrayList<>();
//        for (int i = 0; i < mStepCount; i++) {
//            EditText editTextStep = (EditText) mLayoutRecipeSteps.getChildAt(i);
//            String step = editTextStep.getText().toString();
//            if (!TextUtils.isEmpty(step)) {
//                steps.add(step);
//            }
//        }

        // Create recipe object
//        Recipe recipe = new Recipe(recipeName, ingredients, steps);
        // TODO: Add recipe to database
        // Finish activity
        finish();
    }

    private void addIngredientField() {
        // Create new edit text for ingredient
        EditText editTextIngredient = new EditText(this);
        editTextIngredient.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editTextIngredient.setHint("Ingredient ");
        // Add edit text to layout
        mLayoutRecipeIngredients.addView(editTextIngredient);
        // Increment ingredient count
        mIngredientCount++;
    }

//    private void deleteIngredientField() {
//        // Create new edit text for ingredient
//        EditText editTextIngredient = new EditText(this);
//        editTextIngredient.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT));
//        editTextIngredient.setHint("Ingredient " + (mIngredientCount + 1));
//        // Can't delete the first ingredient
//        if (mIngredientCount == 1) {
//            return;
//        }
//        // Add edit text to layout
//        mLayoutRecipeIngredients.removeView(editTextIngredient);
//        // Increment ingredient count
//        mIngredientCount--;
//    }

    private void addStepField() {
        // Create new edit text for step
        EditText editTextStep = new EditText(this);
        editTextStep.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editTextStep.setHint("Step");

        // Add edit text to layout
        mLayoutRecipeSteps.addView(editTextStep);
        // Increment step count
        mStepCount++;
    }

    private void deleteIngredientField() {
        if (mIngredientCount <= 2) {
            return;
        }
        // Remove the last ingredient field
        View lastIngredientView = mLayoutRecipeIngredients.getChildAt(mIngredientCount - 1);
        mLayoutRecipeIngredients.removeView(lastIngredientView);
        // Decrement ingredient count
        mIngredientCount--;
    }

    private void deleteStepField() {
        if (mStepCount <= 2) {
            return;
        }
        // Remove the last ingredient field
        View lastStepView = mLayoutRecipeSteps.getChildAt(mStepCount - 1);
        mLayoutRecipeSteps.removeView(lastStepView);
        // Decrement ingredient count
        mStepCount--;
    }
}
