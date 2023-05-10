package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

//        buttonDelIngredient.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                deleteIngredientField();
//            }
//        });

        buttonDelStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStepField();
            }
        });

        // Add initial ingredient and step fields
        setButtonAddIngredient2();
        addStepField();
        deleteIngredientField();
//        deleteIngredientField();
//        deleteStepField();
    }

    private void addRecipe() {
        // Get recipe name
        String recipeName = mEditTextRecipeName.getText().toString();
        // Get recipe description
        String recipeDescription = mEditTextRecipeDescription.getText().toString();
        // Get recipe ingredients
        List<String> recipeIngredients = new ArrayList<>();
        for (int i = 0; i < mLayoutRecipeIngredients.getChildCount(); i++) {
            View child = mLayoutRecipeIngredients.getChildAt(i);
            if (child instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) child;
                Log.d("RecipeAddActivity", "linearLayout.getChildCount() = " + linearLayout.getChildCount());
                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    View innerChild = linearLayout.getChildAt(j);
                    if (innerChild instanceof EditText) {
                        EditText editTextIngredient = (EditText) innerChild;
                        Log.d("RecipeAddActivity", "editTextIngredient = " + editTextIngredient.getText().toString());
                        String ingredient = editTextIngredient.getText().toString();
                        if (!TextUtils.isEmpty(ingredient)) {
                            recipeIngredients.add(ingredient);
                        }
                    }
                }
            }
        }

        // Get recipe steps
        ArrayList<RecipeStep> recipeSteps = new ArrayList<>();
        for (int i = 0; i < mLayoutRecipeSteps.getChildCount(); i++) {
            View child = mLayoutRecipeSteps.getChildAt(i);
            if (child instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) child;
                Log.d("RecipeAddActivity", "linearLayout.getChildCount() = " + linearLayout.getChildCount());
                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    View innerChild = linearLayout.getChildAt(j);
                    if (innerChild instanceof EditText) {
                        EditText editTextStep = (EditText) innerChild;
                        Log.d("RecipeAddActivity", "editTextStep = " + editTextStep.getText().toString());
                        String stepName = editTextStep.getText().toString();
                        if (!TextUtils.isEmpty(stepName)) {
                            recipeSteps.add(new RecipeStep(0, stepName, 0 ,""));
                        }
                    }
                }
            }
        }
        // Validate input
        if (TextUtils.isEmpty(recipeName)) {
            Toast.makeText(this, "Enter recipe name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(recipeDescription)) {
            Toast.makeText(this, "Enter recipe description!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (recipeIngredients.isEmpty()) {
            Toast.makeText(this, "Enter recipe ingredients!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (recipeSteps.isEmpty()) {
            Toast.makeText(this, "Enter recipe steps!", Toast.LENGTH_SHORT).show();
            return;
        }

//        Recipe recipe = new Recipe(recipeName, recipeDescription, recipeIngredients, recipeSteps);
        // Add recipe to database
        //    public boolean addRecipe(String title,String description,String steps,String author,int category,int thumbUpCounts,int collectedCounts, String imagePath){
        DbHandler dbHandler = new DbHandler(this);
        dbHandler.addRecipe(recipeName,recipeDescription, recipeSteps,"admin",0,0,0,"");
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

    private void setButtonAddIngredient2(){
        // Find the "button_add_ingredient" button
        Button addIngredientButton = findViewById(R.id.button_add_ingredient);

// Add click listener to the button
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the parent layout of the ingredient rows
                LinearLayout ingredientLayout = findViewById(R.id.layout_recipe_ingredients);

                // Create a new LinearLayout for the new ingredient row
                LinearLayout newIngredientLayout = new LinearLayout(getApplicationContext());
                newIngredientLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                newIngredientLayout.setOrientation(LinearLayout.HORIZONTAL);

                // Create the "ingredient" EditText
                EditText ingredientEditText = new EditText(getApplicationContext());
                ingredientEditText.setLayoutParams(new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1));
                ingredientEditText.setHint("Ingredient");

                // Create the "unit" EditText
                EditText unitEditText = new EditText(getApplicationContext());
                unitEditText.setLayoutParams(new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1));
                unitEditText.setHint("Unit");

                // Add the "ingredient" and "unit" EditTexts to the new ingredient row
                newIngredientLayout.addView(ingredientEditText);
                newIngredientLayout.addView(unitEditText);

                // Add the new ingredient row to the parent layout
                ingredientLayout.addView(newIngredientLayout);
            }
        });

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
        // Create new edit text and button for step
        EditText editTextStep = new EditText(this);
        editTextStep.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editTextStep.setHint("Add Step");
        // create a button dynamically
        Button btn = new Button(this);
        btn.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        btn.setText("Add Next Step");
        btn.setId(mStepCount);
        // Add edit text to layout
        mLayoutRecipeSteps.addView(btn);
        mLayoutRecipeSteps.addView(editTextStep);
        //create a button dynamically
        Button  btnDelete = new Button(this);
        btnDelete.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        btnDelete.setText("Delete");
        btnDelete.setId(mStepCount);
        mLayoutRecipeSteps.addView(btnDelete);
        // Set on click listener for delete button
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // log mStepCount
                if (mStepCount >2) {
                    mLayoutRecipeSteps.removeView(editTextStep);
                    mLayoutRecipeSteps.removeView(btn);
                    mLayoutRecipeSteps.removeView(btnDelete);
                    // Decrement ingredient count
                    mStepCount--;
                }else {
                    mLayoutRecipeSteps.removeView(btnDelete);
                }
            }
        });
        // Increment step count
        mStepCount++;
    }

    private void deleteIngredientField() {
        // Find the "button_del_ingredient" button
        Button delIngredientButton = findViewById(R.id.button_del_ingredient);

// Add click listener to the button
        delIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the parent layout of the ingredient rows
                LinearLayout ingredientLayout = findViewById(R.id.layout_recipe_ingredients);

                // Get the index of the last ingredient row
                int lastIndex = ingredientLayout.getChildCount() - 1;

                // Make sure there is at least one ingredient row
                if (lastIndex >= 0) {
                    // Remove the last ingredient row from the parent layout
                    ingredientLayout.removeViewAt(lastIndex);
                }
            }
        });

//        if (mIngredientCount <= 2) {
//            return;
//        }
//        // Remove the last ingredient field
//        View lastIngredientView = mLayoutRecipeIngredients.getChildAt(mIngredientCount - 1);
//        mLayoutRecipeIngredients.removeView(lastIngredientView);
//        // Decrement ingredient count
//        mIngredientCount--;
    }



    private void deleteStepField() {
        if (mStepCount <= 2) {
            return;
        }
        Log.d("xxxxxx", "deleteStepField: mStepCount = " + mStepCount);
        // Remove the last step field
        View lastStepView = mLayoutRecipeSteps.getChildAt(mStepCount - 1);
        Log.d("RecipeAddActivity", "lastStepView = " + lastStepView);
        mLayoutRecipeSteps.removeView(lastStepView);
        // Decrement ingredient count
        mStepCount--;
    }
}
