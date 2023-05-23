package com.example.myapplication;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RecipeFormActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private LinearLayout ingredientsLayout;
    private LinearLayout stepsLayout;
    private Button addIngredientBtn;
    private Button addStepBtn;
    private Button submitBtn;

    private List<EditText> ingredientEditTexts;
    private List<StepItem> stepItems;

    private List<RecipeIngredient> ingredients;

    private ArrayList<RecipeIngredient> ingredientArrayList;

    private Map<String, Integer> stepIndexMap = new HashMap<>();
    private Map<String, String> stepImagePathMap = new HashMap<>();

    private String recipeTitle;
    private String recipeDescription;
    private List<String> recipeIngredients;
    private List<Step> recipeSteps;
    private int recipeId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_form);

        ingredientsLayout = findViewById(R.id.ingredients_layout);
        stepsLayout = findViewById(R.id.steps_layout);
        addIngredientBtn = findViewById(R.id.add_ingredient_button);
        addStepBtn = findViewById(R.id.add_step_button);
        submitBtn = findViewById(R.id.submit_button);

        ingredientEditTexts = new ArrayList<>();
        stepItems = new ArrayList<>();
        ingredients = new ArrayList<>();

        addIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredientEditText();
            }
        });

        addStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStepItem();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRecipe();
            }
        });

        // 获取传递的数据
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("recipeId")) {
            recipeId = extras.getInt("recipeId");
            DbHandler dbHandler = new DbHandler(this);
            Recipe recipe2 = dbHandler.getRecipe(recipeId);
            ingredientArrayList = recipe2.getIngredients();
            recipeSteps = recipe2.getRecipeSteps();
            recipeTitle = recipe2.getTitle();
            recipeDescription = recipe2.getDescription();
            recipeIngredients = extras.getStringArrayList("ingredients");
//            recipeSteps = extras.getParcelableArrayList("steps");
            populateFormWithData();
        }
    }

    public void addIngredientEditText() {
        Context context = getApplicationContext();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        EditText editText = new EditText(context);
        editText.setLayoutParams(layoutParams);
        editText.setHint("Enter ingredient name");
        ingredientsLayout.addView(editText);
        ingredientEditTexts.add(editText);
        // create ingredient quantity edit text
        EditText quantityEditText = new EditText(context);
        quantityEditText.setLayoutParams(layoutParams);
        quantityEditText.setHint("Enter ingredient quantity");
        ingredientsLayout.addView(quantityEditText);
        ingredientEditTexts.add(quantityEditText);
        // create ingredient unit edit text
        EditText unitEditText = new EditText(context);
        unitEditText.setLayoutParams(layoutParams);
        unitEditText.setHint("Enter ingredient unit");
        ingredientsLayout.addView(unitEditText);
        ingredientEditTexts.add(unitEditText);
        LinearLayout horizontalLayout = new LinearLayout(context);
        horizontalLayout.setLayoutParams(layoutParams);
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        ingredientsLayout.addView(horizontalLayout);
        ingredients.add(new RecipeIngredient(editText.getText().toString(), quantityEditText.getText().toString(), unitEditText.getText().toString()));
        // create remove ingredient button
        Button removeIngredientBtn = new Button(context);
        removeIngredientBtn.setLayoutParams(layoutParams);
        removeIngredientBtn.setText("Remove ingredient");
        removeIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeIngredientEditText(editText, quantityEditText, unitEditText, removeIngredientBtn);
            }
        });
        ingredientsLayout.addView(removeIngredientBtn);
        ingredients.add(new RecipeIngredient(editText.getText().toString(), quantityEditText.getText().toString(), unitEditText.getText().toString()));

    }

    private void removeIngredientEditText(EditText editText, EditText quantityEditText, EditText unitEditText, Button removeIngredientBtn) {
        ingredientsLayout.removeView(editText);
        ingredientsLayout.removeView(quantityEditText);
        ingredientsLayout.removeView(unitEditText);
        ingredientsLayout.removeView(removeIngredientBtn);
        ingredientEditTexts.remove(editText);
        ingredientEditTexts.remove(quantityEditText);
        ingredientEditTexts.remove(unitEditText);
    }

    public void addStepItem() {
        Context context = getApplicationContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout stepItemLayout = (LinearLayout) inflater.inflate(R.layout.step_item, stepsLayout, false);

        EditText stepEditText = stepItemLayout.findViewById(R.id.step_edit_text);
        ImageView imageView = stepItemLayout.findViewById(R.id.step_image_view);
        Button removeStepBtn = stepItemLayout.findViewById(R.id.remove_step_button);
        removeStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeStepItem(stepItemLayout);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(v);
            }
        });
        imageView.setTag("step_image_" + stepItems.size());
        stepIndexMap.put(imageView.getTag().toString(), stepItems.size());
        stepItems.add(new StepItem(stepEditText, imageView));
        stepsLayout.addView(stepItemLayout);
    }

    private void removeStepItem(View stepItemLayout) {
        stepsLayout.removeView(stepItemLayout);
        StepItem stepItem = findStepItem(stepItemLayout);
        if (stepItem != null) {
            stepItems.remove(stepItem);
        }
    }
    private StepItem findStepItem(View stepItemLayout) {
        for (StepItem stepItem : stepItems) {
            if (stepItem.getLayout() == stepItemLayout) {
                return stepItem;
            }
        }
        return null;
    }
    public void submitRecipe() {
        String title = ((EditText) findViewById(R.id.title_edit_text)).getText().toString();
        String description = ((EditText) findViewById(R.id.description_edit_text)).getText().toString();
        List<Step> steps = new ArrayList<>();
        // recipeSteps is initialized in onCreate method
//        Log.d("recipeSteps", recipeSteps.toString());
//        Log.d("recipeSteps size", String.valueOf(recipeSteps.size()));
        if (recipeSteps != null && recipeSteps.size() > 0) {
            for (int i = 0; i < recipeSteps.size(); i++) {
                Step step = recipeSteps.get(i);
                //log step
                Log.d("stepInfo", step.toString());
                StepItem stepItem = stepItems.get(i);
                // log stepItem
                Log.d("stepItem", stepItem.toString());
                String stepText = stepItem.getEditText().getText().toString();
                // log stepText
                Log.d("stepText", stepText);
                // get image path
                String imagePath = step.getImagePath();
                // log imagePath
                if(imagePath == null) {
                    Log.d("imagePath", "imagePath is null");
                } else {
                    Log.d("imagePath", imagePath);
                }
                step.setDescription(stepText);
                step.setImagePath(imagePath);
                steps.add(step);
            }
        } else {
            for (StepItem stepItem : stepItems) {
                String stepText = stepItem.getEditText().getText().toString();
                Bitmap stepImage = stepItem.getImage();
                // get image path
                String imagePath = stepImagePathMap.get(stepItem.getImageView().getTag().toString());
                // imagePath is null, means no image is selected
                if (!TextUtils.isEmpty(stepText)) {
                    Step step = new Step(stepText, imagePath, 0);
                    steps.add(step);
                }
            }
        }

//        for (StepItem stepItem : stepItems) {
//            String stepText = stepItem.getEditText().getText().toString();
//            Bitmap stepImage = stepItem.getImage();
//            // get image path
//            String imagePath = stepImagePathMap.get(stepItem.getImageView().getTag().toString());
//            //log image path
//            Log.d("check imagePath", imagePath);
//            // imagePath is null, means no image is selected
//            if (!TextUtils.isEmpty(stepText)) {
//                Step step = new Step(stepText, imagePath, 0);
//                steps.add(step);
//            }
//        }

        String username = getSharedPreferences("login_pref", MODE_PRIVATE).getString("username", "");
        Post post = new Post(title, description, username, "", 1);
        // add posts to sqlite
        DbHandler dbHandler = new DbHandler(this);
        if(recipeId == -1) {
            int lastId = dbHandler.addPost(post);
            Log.d("lastId", String.valueOf(lastId));
            if(lastId > 0){
                Step[] stepArray = steps.toArray(new Step[0]);
                for(int i=0; i<stepArray.length; i++) {
                    Step step = stepArray[i];
                    long id = dbHandler.addSteps(lastId, step.getDescription(), i, stepImagePathMap.get("step_image_" + i));
                    // log the last id
                    Log.d("lastId2", String.valueOf(id));
                }
                for (int i =0; i<ingredientEditTexts.size(); i+=3) {
                    String ingredientName = ingredientEditTexts.get(i).getText().toString();
                    //log the ingredient name
                    Log.d("ingredientName", ingredientName);
                    String ingredientQuantity = ingredientEditTexts.get(i+1).getText().toString();
                    //log the ingredient quantity
                    Log.d("ingredientQuantity", ingredientQuantity);
                    String ingredientUnit = ingredientEditTexts.get(i+2).getText().toString();
                    //log the ingredient unit
                    Log.d("ingredientUnit", ingredientUnit);
                    long id = dbHandler.addIngredients(lastId, ingredientName, ingredientQuantity, ingredientUnit);
                    // log id
                    Log.d("lastId4", String.valueOf(id));
                }

            }
        } else if (recipeId > 0) {
            post.setId(recipeId);
            dbHandler.updatePost(post);
            Step[] stepArray = steps.toArray(new Step[0]);
            for(int i=0; i<stepArray.length; i++) {
                Step step = stepArray[i];
                long id = dbHandler.addSteps(recipeId, step.getDescription(), i, recipeSteps.get(i).getImagePath());
                // log the last id
                Log.d("lastId3", String.valueOf(id));
            }
            // log ingredientEditTexts size
            Log.d("f", String.valueOf(ingredientEditTexts.size()));
            // insert ingredients from edit text
            for (int i =0; i<ingredientEditTexts.size(); i+=3) {
                String ingredientName = ingredientEditTexts.get(i).getText().toString();
                //log the ingredient name
                Log.d("ingredientName", ingredientName);
                String ingredientQuantity = ingredientEditTexts.get(i+1).getText().toString();
                //log the ingredient quantity
                Log.d("ingredientQuantity", ingredientQuantity);
                String ingredientUnit = ingredientEditTexts.get(i+2).getText().toString();
                //log the ingredient unit
                Log.d("ingredientUnit", ingredientUnit);
                long id = dbHandler.addIngredients(recipeId, ingredientName, ingredientQuantity, ingredientUnit);
                // log id
                Log.d("lastId4", String.valueOf(id));
            }

        }
        // redirect to home page
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void uploadImage(View view) {
        String viewTag = (String) view.getTag();
        int stepIndex = getStepIndexFromTag(viewTag);
        startActivityForResult(getImageSelectionIntent(), PICK_IMAGE_REQUEST + stepIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode >= PICK_IMAGE_REQUEST && requestCode < PICK_IMAGE_REQUEST + stepItems.size()) {
                int stepIndex = requestCode - PICK_IMAGE_REQUEST;
                Uri imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    String imagePath = saveBitmapToLocal(bitmap);

                    StepItem stepItem = stepItems.get(stepIndex);
                    stepItem.setImagePath(imagePath);
                    //if recipeSteps is not null, means it is edit recipe page
                    if(recipeSteps != null) {
                        // get step id
                        if(stepIndex < recipeSteps.size()) {
                            Step stepData = recipeSteps.get(stepIndex);
                            stepData.setImagePath(imagePath);
                        }else {
                            // add new step
                            Step stepData = new Step("", imagePath, 0);
                            recipeSteps.add(stepData);
                        }
//                        Step stepData = recipeSteps.get(stepIndex);
//                        stepData.setImagePath(imagePath);
                    }
                    // set image path to tag
                    stepImagePathMap.put(stepItem.getImageView().getTag().toString(), imagePath);
                    stepItem.getImageView().setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String saveBitmapToLocal(Bitmap bitmap) {
        File imagesDir = new File(getFilesDir(), "images");
        if (!imagesDir.exists()) {
            imagesDir.mkdirs();
        }
        String fileName = "image_" + System.currentTimeMillis() + ".jpg";
        File imageFile = new File(imagesDir, fileName);

        try {
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private int getStepIndexFromTag(String tag) {
        Log.d("tag_to_see", tag);
        Integer stepIndex = stepIndexMap.get(tag);
        // log stepIndex
        if (stepIndex != null) {
            return stepIndex;
        }
        return 0;
    }

    private Intent getImageSelectionIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        return Intent.createChooser(intent, "choose pic");
    }

    private void populateFormWithData() {
        EditText titleEditText = findViewById(R.id.title_edit_text);
        EditText descriptionEditText = findViewById(R.id.description_edit_text);
        titleEditText.setText(recipeTitle);
        descriptionEditText.setText(recipeDescription);
        // add ingredients
        if (ingredientArrayList != null && !ingredientArrayList.isEmpty()) {
            for (RecipeIngredient recipeIngredient : ingredientArrayList) {
                addIngredientEditTextWithData(recipeIngredient);
            }
        }

        if (recipeSteps != null && !recipeSteps.isEmpty()) {
            for (Step step : recipeSteps) {
                addStepItemWithData(step);
            }
        }
    }

    private void addIngredientEditTextWithData(RecipeIngredient recipeIngredient) {
        Context context = getApplicationContext();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        // Create and configure ingredient name EditText
        EditText nameEditText = new EditText(context);
        nameEditText.setLayoutParams(layoutParams);
        nameEditText.setHint("Enter ingredient name");
        nameEditText.setText(recipeIngredient.getIngredientName());
        ingredientsLayout.addView(nameEditText);
        ingredientEditTexts.add(nameEditText);

        // Create and configure ingredient quantity EditText
        EditText quantityEditText = new EditText(context);
        quantityEditText.setLayoutParams(layoutParams);
        quantityEditText.setHint("Enter ingredient quantity");
        quantityEditText.setText(recipeIngredient.getIngredientQuantity());
        ingredientsLayout.addView(quantityEditText);
        ingredientEditTexts.add(quantityEditText);

        // Create and configure ingredient unit EditText
        EditText unitEditText = new EditText(context);
        unitEditText.setLayoutParams(layoutParams);
        unitEditText.setHint("Enter ingredient unit");
        unitEditText.setText(recipeIngredient.getIngredientUnit());
        ingredientsLayout.addView(unitEditText);
        ingredientEditTexts.add(unitEditText);

        LinearLayout horizontalLayout = new LinearLayout(context);
        horizontalLayout.setLayoutParams(layoutParams);
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        ingredientsLayout.addView(horizontalLayout);

        // Create and configure remove ingredient button
        Button removeIngredientBtn = new Button(context);
        removeIngredientBtn.setLayoutParams(layoutParams);
        removeIngredientBtn.setText("Remove ingredient");
        removeIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeIngredientEditText(nameEditText, quantityEditText, unitEditText, removeIngredientBtn);
            }
        });
        ingredientsLayout.addView(removeIngredientBtn);

        // Create and add RecipeIngredient object to the list
        ingredients.add(new RecipeIngredient(recipeIngredient.getIngredientName(), recipeIngredient.getIngredientQuantity(), recipeIngredient.getIngredientUnit()));
    }

    private void removeIngredientItem(LinearLayout ingredientItemLayout) {
    }

    private void addStepItemWithData(Step step) {
        Context context = getApplicationContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout stepItemLayout = (LinearLayout) inflater.inflate(R.layout.step_item, stepsLayout, false);

        EditText stepEditText = stepItemLayout.findViewById(R.id.step_edit_text);
        ImageView imageView = stepItemLayout.findViewById(R.id.step_image_view);
        Button removeStepBtn = stepItemLayout.findViewById(R.id.remove_step_button);

        removeStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeStepItem(stepItemLayout);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(v);
            }
        });
        stepEditText.setText(step.getDescription());
        Bitmap stepImage = BitmapFactory.decodeFile(step.getImagePath());
        imageView.setImageBitmap(stepImage);
        imageView.setTag("step_image_" + stepItems.size());
        stepIndexMap.put(imageView.getTag().toString(), stepItems.size());
        stepItems.add(new StepItem(stepEditText, imageView));
        stepsLayout.addView(stepItemLayout);
    }

}
