package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import com.squareup.picasso.Picasso;


public class RecipeAddActivity extends AppCompatActivity {

    private EditText mEditTextRecipeName;
    private LinearLayout mLayoutRecipeIngredients;
    private LinearLayout mLayoutRecipeSteps;
    private EditText mEditTextRecipeDescription;
    private Button mButtonAddRecipe;
    private Button buttonAddIngredient;
//    private Button buttonAddStep;

    private Button buttonDelIngredient;

    private Button buttonDelStep;
    private int mIngredientCount = 1;
    private int mStepCount = 1;

    private LinearLayout layoutPictureUpload;
    private int stepCount = 1;

    private int REQUEST_IMAGE_PICKER = 1;

    private List<View> pictureUploadViews = new ArrayList<>();

    private String selectedPictureUrlOrPath = null;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_add);

        // Initialize views
        mEditTextRecipeName = findViewById(R.id.edit_text_recipe_name);
        mEditTextRecipeDescription = findViewById(R.id.edit_text_recipe_description);
        mLayoutRecipeIngredients = findViewById(R.id.layout_recipe_ingredients);
        mLayoutRecipeSteps = findViewById(R.id.layout_picture_upload);
        mButtonAddRecipe = findViewById(R.id.button_add_recipe);
        buttonAddIngredient = findViewById(R.id.button_add_ingredient);
//        buttonAddStep = findViewById(R.id.button_add_step);
        buttonDelIngredient = findViewById(R.id.button_del_ingredient);
        buttonDelStep = findViewById(R.id.button_del_step);

        layoutPictureUpload = findViewById(R.id.layout_picture_upload);
        Button buttonAddStep = findViewById(R.id.button_add_step);
        buttonAddStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPictureUploadView();
            }
        });

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

//        buttonAddStep.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addStepField();
//            }
//        });

//        buttonDelIngredient.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                deleteIngredientField();
//            }
//        });

        buttonDelStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePictureUploadView();
            }
        });

        // Add initial ingredient and step fields
        setButtonAddIngredient2();
//        addStepField();
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
        // log mLayoutRecipeSteps
        Log.d("RecipeAddActivityStep", "mLayoutRecipeSteps.getChildCount() = " + mLayoutRecipeSteps.getChildCount());
        for (int i = 0; i < mLayoutRecipeSteps.getChildCount(); i++) {
            View child = mLayoutRecipeSteps.getChildAt(i);
            Log.d("RecipeAddActivityStep", "child = " + child.toString());
            // picture upload url
            if (child instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) child;
                Log.d("RecipeAddActivityStep", "linearLayout.getChildCount() = " + linearLayout.getChildCount());
                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    View innerChild = linearLayout.getChildAt(j);
                    Log.d("RecipeAddActivityStep", "innerChild = " + innerChild.toString());
                    if (innerChild instanceof EditText) {
                        EditText editTextStep = (EditText) innerChild;
                        String stepName = editTextStep.getText().toString();
                        String pictureUploadUrl = (String) linearLayout.getTag(); // Get the picture upload URL from the tag
                        // log pictureUploadUrl
                        Log.d("RecipeAddActivityStep", "pictureUploadUrl = " + pictureUploadUrl);
                        if (!TextUtils.isEmpty(stepName)) {
                            recipeSteps.add(new RecipeStep(0, stepName, 0 ,pictureUploadUrl));
                        }
                    }

                }
            }
            if (child instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) child;
                Log.d("RecipeAddActivityCheck", "linearLayout.getChildCount() = " + linearLayout.getChildCount());
                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    View innerChild = linearLayout.getChildAt(j);
                    if (innerChild instanceof EditText) {
                        EditText editTextStep = (EditText) innerChild;
                        String stepName = editTextStep.getText().toString();
                        if (!TextUtils.isEmpty(stepName)) {
                            recipeSteps.add(new RecipeStep(0, stepName, 0 ,""));
                        }
                    }
                }
            }
        }
        // log recipeSteps
        for (RecipeStep recipeStep : recipeSteps) {
            Log.d("RecipeAddActivity", "recipeStep = " + recipeStep.getDescription());
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

    private void addPictureUploadView() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        LinearLayout pictureUploadLayout = new LinearLayout(this);
        pictureUploadLayout.setLayoutParams(layoutParams);
        pictureUploadLayout.setOrientation(LinearLayout.HORIZONTAL);

        imageView = new ImageView(this);
        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(
                120, // Set the desired width for the ImageView
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        imageView.setLayoutParams(imageViewParams);
        imageView.setImageResource(R.drawable.baseline_cloud_upload_24);

        Button buttonSelectPicture = new Button(this);
        buttonSelectPicture.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        buttonSelectPicture.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        buttonSelectPicture.setText("Select Picture");
        EditText editTextStep = new EditText(this);
        LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        editTextStep.setLayoutParams(editTextParams);
        editTextStep.setHint("Step");

        pictureUploadLayout.addView(imageView);
        pictureUploadLayout.addView(buttonSelectPicture);
        pictureUploadLayout.addView(editTextStep);

        layoutPictureUpload.addView(pictureUploadLayout);
        stepCount++;
        pictureUploadViews.add(pictureUploadLayout);
        buttonSelectPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
                imageView.setImageResource(R.drawable.baseline_cloud_upload_24);
                // Update the EditText with the selected picture's URL or file path
                editTextStep.setText(selectedPictureUrlOrPath);

                //log select picture url or path
                if(selectedPictureUrlOrPath != null){
                    Log.d("select picture url", selectedPictureUrlOrPath);
                }

            }
        });

    }


    private void deletePictureUploadView() {
        if (pictureUploadViews.size() > 0) {
            // Get the last added pictureUploadLayout
            LinearLayout lastPictureUploadLayout = (LinearLayout) pictureUploadViews.get(pictureUploadViews.size() - 1);

            // Remove it from the layoutPictureUpload
            layoutPictureUpload.removeView(lastPictureUploadLayout);

            // Remove it from the pictureUploadViews list
            pictureUploadViews.remove(lastPictureUploadLayout);

            stepCount--;
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICKER && resultCode == RESULT_OK && data != null) {
            // The user has selected a file
            Uri fileUri = data.getData();
            String filePath = FileUtility.getPath(this, fileUri);

            if (filePath != null) {
                // File path is available, save the file to your project
                String destinationPath = getFilesDir().getAbsolutePath() + "/uploaded_images/";
                File destinationDir = new File(destinationPath);
                if (!destinationDir.exists()) {
                    destinationDir.mkdirs();
                }
                Log.d("check folder", "Destination: " + destinationPath);
                String destinationFileName = "uploaded_image.jpg";
                //set new filename for each image
                destinationFileName = "uploaded_image" + System.currentTimeMillis() + ".jpg";
                File destinationFile = new File(destinationDir, destinationFileName);

                try {
                    copyFileFromUri(this, fileUri, destinationFile);
                    // File saved successfully, do something with it
                    // For example, display the image in an ImageView
                    imageView.setImageURI(Uri.fromFile(destinationFile));
                    // You can also pass the file path to other methods or classes within your project
                    processImageFile(destinationFile.getAbsolutePath());
                    // Store the file path as a string variable
                    selectedPictureUrlOrPath = destinationFile.getAbsolutePath();
                    // set tag for each image
                    imageView.setTag(destinationFile.getAbsolutePath());


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to save the image file", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Failed to get the file path", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void processImageFile(String filePath) {
        // TODO: Implement further processing logic for the image file
        // You can perform any desired operations on the saved image file within your project.
        // log filePath to Logcat
        Log.d("Image File Path", filePath);
    }

    private void copyFileFromUri(Context context, Uri uri, File destinationFile) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        OutputStream outputStream = new FileOutputStream(destinationFile);
        byte[] buffer = new byte[4 * 1024]; // Adjust the buffer size as per your requirement
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }






}
