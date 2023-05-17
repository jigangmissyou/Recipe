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
            // 获取数据
            recipeId = extras.getInt("recipeId");
            // 根据id获取数据
            DbHandler dbHandler = new DbHandler(this);
            Recipe recipe2 = dbHandler.getRecipe(recipeId);
            recipeSteps = recipe2.getRecipeSteps();

            recipeTitle = recipe2.getTitle();
            recipeDescription = recipe2.getDescription();
            recipeIngredients = extras.getStringArrayList("ingredients");
//            recipeSteps = extras.getParcelableArrayList("steps");

            // 进行数据回显
            populateFormWithData();
        }
    }

    private void addIngredientEditText() {
        Context context = getApplicationContext();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        EditText editText = new EditText(context);
        editText.setLayoutParams(layoutParams);
        editText.setHint("Enter ingredient");
        ingredientsLayout.addView(editText);
        ingredientEditTexts.add(editText);
    }

    private void addStepItem() {
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

    private void submitRecipe() {
        String title = ((EditText) findViewById(R.id.title_edit_text)).getText().toString();
        String description = ((EditText) findViewById(R.id.description_edit_text)).getText().toString();

        List<String> ingredients = new ArrayList<>();
        for (EditText editText : ingredientEditTexts) {
            String ingredient = editText.getText().toString();
            if (!TextUtils.isEmpty(ingredient)) {
                ingredients.add(ingredient);
            }
        }

        List<Step> steps = new ArrayList<>();
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

        Post post = new Post(title, description, "author", "", 1);

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
            }
        } else if (recipeId > 0) {
            post.setId(recipeId);
            dbHandler.updatePost(post);
            Step[] stepArray = steps.toArray(new Step[0]);
            for(int i=0; i<stepArray.length; i++) {
                Step step = stepArray[i];
                long id = dbHandler.addSteps(recipeId, step.getDescription(), i, stepImagePathMap.get("step_image_" + i));
                // log the last id
                Log.d("lastId3", String.valueOf(id));
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
        // 创建图片文件
        File imagesDir = new File(getFilesDir(), "images");
        if (!imagesDir.exists()) {
            imagesDir.mkdirs();
        }
        String fileName = "image_" + System.currentTimeMillis() + ".jpg";
        File imageFile = new File(imagesDir, fileName);

        try {
            // 将Bitmap保存到文件
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

        if (recipeIngredients != null && !recipeIngredients.isEmpty()) {
            for (String ingredient : recipeIngredients) {
                addIngredientEditTextWithData(ingredient);
            }
        }

        if (recipeSteps != null && !recipeSteps.isEmpty()) {
            for (Step step : recipeSteps) {
                addStepItemWithData(step);
            }
        }
    }

    private void addIngredientEditTextWithData(String ingredientText) {
        Context context = getApplicationContext();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        EditText editText = new EditText(context);
        editText.setLayoutParams(layoutParams);
        editText.setHint("Enter ingredient");
        editText.setText(ingredientText);
        ingredientsLayout.addView(editText);
        ingredientEditTexts.add(editText);
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
